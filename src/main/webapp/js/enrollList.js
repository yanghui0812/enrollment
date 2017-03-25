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
		"columns": [{ "title": "注册号", 	"data": "registerId" },
					{ "title": "表单号", 	"data": "formId", "visible":false},
		            { "title": "电话", 	"data": "phoneNumber" },
		            { "title": "身份证号", "data": "id" },
		            { "title": "注册日期", "data": "registerDateStr" },
		            { "title": "注册状态", "data": "statusDesc" },
		            { "title": "操作", 	"data": "registerId", "orderable": false, "searchable": false, "render": function (data, type, full) {
		            	var prefixForUpdate = $(':hidden[name=enrollUpdateUrl]').val();
		            	var prefixForDetail = $(':hidden[name=enrollDetail]').val();
		    			return '<a href="' + prefixForUpdate + '?registerId=' + data + '" style="margin-top:-3px">修改</a></br><a href="' + prefixForDetail + '?registerId=' + data + '" style="margin-top:-3px">详细信息</a>'; 
			    	 } }
		        ],
		 order: [[ 2, 'asc' ]]
	    });
	
		$('#enrollTable_filter').hide();
		$('input.global_filter').on( 'keyup click', function () {
			var begin = $('input[name=registerDateBegin]').val();
			var end =   $('input[name=registerDateEnd]').val();
			table.columns(1).search($('.formId').val());
			table.columns(4).search(begin + '~' + end);
			table.search($('input[name=search]').val()).draw();
	    });
		
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