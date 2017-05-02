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
		"columns": [{ "title": "用户名",  "data": "name", "name" : "name", "render": function (data, type, full) {
			        	return '<a href="' + userDetailUrl + '?userId=' + full.id + '" style="margin-top:-3px;">' + data + '</a>';
			    	 }},
		            { "title": "用户角色", "data": "rolesDesc", "orderable": false},
		            { "title": "用户状态", "data": "activeDesc", "name" : "active"},
		            { "title": "操作", 	 "data": "id", "orderable": false, "searchable": false, "render": function (data, type, full) {
		            	//var detail =  '<a href="' + userDetailUrl + '?userId=' + data + '" style="margin-top:-3px">详细信息</a>'; 
		            	var disable = '<a class="disableUser" href="javascript:void(0);" data-userId="' + data + '" style="margin-top:-3px">暂停</a>'; 
		            	var enable =  '<a class="enableUser"  href="javascript:void(0);" data-userId="' + data + '" style="margin-top:-3px">启用</a>'; 
		            	if (full.active) {
		            		return disable; 
		            	} 
		    			return enable; 
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
		$('.searchbtn').on( 'click', function () {
			submitToSearch();
	    });
		
		function submitToSearch() {
			table.search($('input[name=search]').val()).draw();
		}
		
		$('#downloadEnroll').click(function() {
			$('#downloadForm').submit();
		});	
});