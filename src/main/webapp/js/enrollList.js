$(document).ready(function() {
	var table = $('#enrollTable').DataTable( {
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
				  "url": $(':hidden[name=enrollPageJson]').val(), 
				  "contentType": "application/json",
                  "type": "POST",
                  "data": function ( d ) {
                     return JSON.stringify( d );
                  }
			   },
		"columns": [
				  //{ "title": "注册号", 	 "data": "registerId", "name" : "registerId"},
					{ "title": "表单号",   "data": "formId", "visible":false, "name" : "formId"},
		            { "title": "预约时间",  "data": "fieldValueList", "orderable": false, "name" : "apptime", "render": function (data, type, full) {
		            	var array = data;
		            	for(var i = 0; i < array.length; i++) {
		            		if (array[i]['fieldName'] == 'apptime') {
		            			return array[i]['fieldDisplay'];
		            		}
		            	}
		            	return "";
		              }
					},
		            { "title": "考生姓名",  "data": "fieldValueList", "orderable": false, "render": function (data, type, full) {
		            	var array = data;
		            	for(var i = 0; i < array.length; i++) {
		            		if (array[i]['fieldName'] == 'applicantname') {
		            			return array[i]['fieldValue'];
		            		}
		            	}
		            	return "";
		            }},
		            { "title": "中考报名号", "data": "fieldValueList", "orderable": false, "render": function (data, type, full) {
		            	var array = data;
		            	for(var i = 0; i < array.length; i++) {
		            		if (array[i]['fieldName'] == 'examnumber') {
		            			return array[i]['fieldValue'];
		            		}
		            	}
		            	return "";
		            }},
		            { "title": "报考区域",  "data": "fieldValueList", "orderable": false, "render": function (data, type, full) {
		            	var array = data;
		            	for(var i = 0; i < array.length; i++) {
		            		if (array[i]['fieldName'] == 'appzone') {
		            			return array[i]['fieldDisplay'];
		            		}
		            	}
		            	return "";
		            }},
		            { "title": "联系电话",  "data": "fieldValueList", "orderable": false, "render": function (data, type, full) {
		            	var array = data;
		            	for(var i = 0; i < array.length; i++) {
		            		if (array[i]['fieldName'] == 'phonenumber') {
		            			return array[i]['fieldValue'];
		            		}
		            	}
		            	return "";
		            }},
		            { "title": "注册日期",  "data": "registerDateStr", "name" : "registerDate"},
		            { "title": "注册状态",  "data": "statusDesc", "name" : "status"},
		            { "title": "操作", 	 "data": "registerId", "orderable": false, "searchable": false, "render": function (data, type, full) {
		            	var prefixForDetail = $(':hidden[name=enrollDetail]').val();
		    			return '<a href="' + prefixForDetail + '?registerId=' + data + '" style="margin-top:-3px">详细信息</a>'; 
			    	 } }
		        ]
	    });
		
		$('.dataTable').addClass('table');
		$('#enrollTable_filter').hide();
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
			table.columns(6).search(begin + '~' + end);
			var apptime = $('.apptime').val();
			table.search($('input[name=search]').val() + ' ' + apptime.replace(/\s/g,"")).draw();
		}
		
		$('#downloadEnroll').click(function() {
			$('#downloadForm').submit();
		});			
		
		//Select date range
		var dateFormat = "mm/dd/yy",
	      from = $( "#from" )
	        .datepicker({
	          defaultDate: "+1w",
	          changeMonth: true,
	          numberOfMonths: 2
	        }).on( "change", function() {
	          to.datepicker( "option", "minDate", getDate( this ) );
	        }),
	      to = $( "#to" ).datepicker({
	        defaultDate: "+1w",
	        changeMonth: true,
	        numberOfMonths: 2
	      })
	      .on( "change", function() {
	        from.datepicker( "option", "maxDate", getDate( this ) );
	      });
	 
	    function getDate( element ) {
	      var date;
	      try {
	        date = $.datepicker.parseDate( dateFormat, element.value );
	      } catch( error ) {
	        date = null;
	      }
	 
	      return date;
	    }
});