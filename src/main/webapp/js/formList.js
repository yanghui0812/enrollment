$(document).ready(function() {
	var formTable = $('#formTable').DataTable( {
		"processing":true,
		"serverSide":true,
		"srollY": "200px",
		"sScrollX": true,
		"searchDelay": 200,
		"language": {
			 "sLengthMenu": "显示 _MENU_ 项结果",
		     "emptyTable": "没有数据",
		     "search": "搜索:",
		     "info": "显示第 _PAGE_ 页，共 _PAGES_ 页, _TOTAL_ 条记录",
		     "paginate": {
		           "next": "下一页",
		           "previous": "前一页",
		           "last": "最后一页",
		           "first": "第一页"}
		  },
		"ajax": {
				  "url": $(':hidden[name=formMetaPageUrl]').val(),
				  "contentType": "application/json",
                  "type": "POST",
                  "data": function ( d ) {
                     return JSON.stringify( d );
                  }
			   },
		"order": [[ 2, "desc" ]],
		"columns": [{ "title": "编号", 	 "data": "formId", "name" : "formId"},
		            { "title": "表单名称", "data": "formName", "name" : "formName"},
		            { "title": "创建时间", "data": "formatCreatedDate", "name" : "createdDate"},
		            { "title": "描述", 	 "data": "formDescription", "orderable": false},
		            { "title": "状态", 	 "data": "statusDesc", "name" : "status"},
		            { "title": "操作", 	 "data": "formId", "orderable": false, "searchable": false, "render": function (data, type, full) {
		            	var prefixForUpdate = ($(':hidden[name=formMetaUpdateUrl]').val()).replace('{formId}', data);
		            	var prefixForEnroll = $(':hidden[name=enrollUrl]').val();
		            	var updateUrl = '<a href="' + prefixForUpdate + '">修改</a>';
		            	var publishUrl = '<a class="publishOrInactive" href="javascript:void(0);" data-formId="' + data + '" data-status="publish">发布</a>';
		            	var inactiveUrl = '<a class="publishOrInactive" href="javascript:void(0);" data-formId="' + data + '" data-status="inactive">废弃</a>';
		            	var copyUrl = '<a class="copyForm" href="javascript:void(0);" data-formId="' + data + '">复制</a>';
		            	var publicUrl = '<a class="publicForm" href="javascript:void(0);" data-formId="' + data + '">报名</a>';
		            	var result = '';
		            	if (full.canUpdate) {
		            		result = updateUrl;
		            	}
		            	if (full.canPublish) {
		            		if (result == '') {
		            			result = publishUrl;
		            		} else {
		            			result = result + '</br>' + publishUrl;
		            		}
		            	}
		            	if (full.canInactive) {
		            		if (result == '') {
		            			result = inactiveUrl;
		            		} else {
		            			result = result + '</br>' + inactiveUrl;
		            		}
		            	}
		            	if (full.canEnroll) {
		            		if (result == '') {
		            			result = publicUrl;
		            		} else {
		            			result = result + '</br>' + publicUrl;
		            		}
		            	}
		            	result = result + '</br>' + copyUrl;
		            	result = result + '</br><a href="' + prefixForEnroll + '?formId=' + data + '">预览</a>';
		    			return result; 
			    	 } }
		        ]
	    }).on( 'draw.dt', function () {
	    	$( '.publishOrInactive' ).bind( "click", function(event) {
        		var op = $(event.target).data('status');
        		var formId = $(event.target).data('formid');
        		var requestUrl = ($(':hidden[name=formMetaUpdateUrl]').val()).replace('{formId}', formId);
        		$.ajax({method: "POST", url: requestUrl, data: {"status": op}, dataType: "json"}).done(function( data ) {
        			formTable.draw( 'full-hold' );
        			if (data.status == '200') {
        				alert(data.message);
        			}
        		});
        	});
	    	
	    	$( '.copyForm' ).bind( "click", function(event) {
        		var formId = $(event.target).data('formid');
        		var requestUrl = $(':hidden[name=copyUrl]').val();
        		$.ajax({method: "POST", url: requestUrl, data: {"formId": formId}}).done(function( data ) {
        			formTable.draw( 'full-hold' );
        			if (data.status == '200') {
        				alert(data.message);
        			}
        		});
        	});
	    	
	    	$( '.publicForm' ).bind( "click", function(event) {
	    		var formId = $(event.target).data('formid');
        		var requestUrl = $(':hidden[name=publicUrl]').val() + '?formId=' + formId;
	    		var open_link = window.open('', formId);
				open_link.location = requestUrl;
        	});
	   });
	
	   $('#formTable_filter').hide();
	   $('input.global_filter').on( 'keyup click', function () {
		   formTable.search($('input[name=search]').val()).draw();
	    });
});