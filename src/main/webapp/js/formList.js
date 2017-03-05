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
		            { "title": "描述", 	"data": "formDescription", "orderable": false },
		            { "title": "状态", 	"data": "statusDesc" },
		            { "title": "操作", 	"data": "formId", "orderable": false, "searchable": false, "render": function (data, type, full) {
		            	var prefixForUpdate = $(':hidden[name=formMetaUpdateUrl]').val();
		            	var prefixForEnroll = $(':hidden[name=enrollUrl]').val();
		            	var updateUrl = '<a href="' + prefixForUpdate + '?formId=' + data + '&op=update">修改</a>';
		            	var publishUrl = '<a href="' + prefixForUpdate + '?formId=' + data + '&op=publish">发布</a>';
		            	var inactiveUrl = '<a href="' + prefixForUpdate + '?formId=' + data + '&op=inactive">废弃</a>';
		            	var result = '';
		            	if (full.canUpdate) {
		            		result = updateUrl;
		            	}
		            	if (full.canPublish) {
		            		result = result + '</br>' + publishUrl;
		            	}
		            	if (full.canInactive) {
		            		result = result + '</br>' + inactiveUrl;
		            	}
		    			return result; 
			    	 } }
		        ],
		 order: [[ 2, 'asc' ]]
	    });
});