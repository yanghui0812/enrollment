$(document).ready(function() {
	var disableUserUrl = $(':hidden[name=disableUser]').val();
	var enableUserUrl = $(':hidden[name=enableUser]').val();
	var userDetailUrl = $(':hidden[name=userDetail]').val();
	var table = $('#userTable').DataTable( {
		"processing":true,
		"serverSide":true,
		"srollY": "200px",
		"sScrollX": true,
		"language": {
			 "sLengthMenu": "显示 _MENU_ 项结果",
		     "emptyTable": "没有数据",
		     "info": "显示第 _PAGE_ 页，共 _PAGES_ 页, _TOTAL_ 条记录",
		     "paginate": {
		           "next": "下一页",
		           "previous": "前一页",
		           "last": "最后一页",
		           "first": "第一页"}
		  },
		"ajax": {
				  "url": $(':hidden[name=userPageJson]').val(), 
				  "contentType": "application/json",
                  "type": "POST",
                  "data": function ( d ) {
                     return JSON.stringify( d );
                  }
			   },
		"columns": [{ "title": "用户编号", "data": "id", "visible":false},
		            { "title": "用户名",   "data": "name", "orderable": false},
		            { "title": "用户角色", "data": "name", "name" : "registerDate"},
		            { "title": "用户状态", "data": "activeDesc", "name" : "active"},
		            { "title": "操作", 	 "data": "id", "orderable": false, "searchable": false, "render": function (data, type, full) {
		            	var detail =  '<a href="' + userDetailUrl + '?userId=' + data + '" style="margin-top:-3px">详细信息</a>'; 
		            	var disable = '<a class="disableUser" href="javascript:void(0);" data-userId="' + data + '" style="margin-top:-3px">暂停</a>'; 
		            	var enable =  '<a class="enableUser"  href="javascript:void(0);" data-userId="' + data + '" style="margin-top:-3px">启用</a>'; 
		            	if (full.active) {
		            		return disable + '<br>' + detail; 
		            	} 
		    			return enable + '<br>' + detail; 
			    	 } }
		        ]
	    }).on( 'draw.dt', function () {
	    	$('.dataTable').addClass('table');
	    	$( '.disableUser' ).bind( "click", function(event) {
        		var userId = $(event.target).data('userid');
        		$.ajax({method: "POST", url: disableUserUrl, data: {"userId": userId}, dataType: "json"}).done(function( data ) {
        			table.draw( 'full-hold' );
        			if (data.status == '200') {
        				alert(data.message);
        			}
        		});
        	});
	    	
	    	$( '.enableUser' ).bind( "click", function(event) {
	    		var userId = $(event.target).data('userid');
        		$.ajax({method: "POST", url: enableUserUrl, data: {"userId": userId}, dataType: "json"}).done(function( data ) {
        			table.draw( 'full-hold' );
        			if (data.status == '200') {
        				alert(data.message);
        			}
        		});
        	});
	   });
		
		$('.dataTable').addClass('table');
		$('#userTable_filter').hide();
		$('input.global_filter').on( 'keyup click', function () {
			submitToSearch();
	    });
		
		$('.searchbtn').on( 'click', function () {
			submitToSearch();
	    });
		
		function submitToSearch() {
			var begin = $('input[name=registerDateBegin]').val();
			var end =   $('input[name=registerDateEnd]').val();
			table.columns(1).search($('.formId').val());
			table.columns(3).search(begin + '~' + end);
			var apptime = $('.apptime').val();
			table.search($('input[name=search]').val() + ' ' + apptime.replace(/\s/g,"")).draw();
		}
		
		$('#downloadEnroll').click(function() {
			$('#downloadForm').submit();
		});	
});