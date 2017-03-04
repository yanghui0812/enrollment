$(document).ready(function() {
	var table = $('#formTable').DataTable( {
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
				  "url": $(':hidden[name=formMetaPageUrl]').val(),
			      "type": "POST"
			   },
		"columns": [{ "title": "编号", 	"data": "formId" },
		            { "title": "名字", 	"data": "formName" },
		            { "title": "创建时间", "data": "formatCreatedDate" },
		            { "title": "描述", 	"data": "formDescription" },
		            { "title": "状态", 	"data": "statusDesc" },
		            { "title": "操作", 	"data": "formId", "orderable": false, "render": function (data, type, full) {
		            	var prefixForUpdate = $(':hidden[name=formMetaUpdateUrl]').val();
		            	var prefixForEnroll = $(':hidden[name=enrollUrl]').val();
		    			return '<a href="' + prefixForUpdate + '?formId=' + data + '" style="margin-top:-3px">修改</a></br><a href="' + prefixForEnroll + '?formId=' + data + '" style="margin-top:-3px">报名</a>'; 
			    	 } }
		        ],
		 order: [[ 2, 'asc' ]]
	    });
});