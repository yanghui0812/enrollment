$(document).ready(function($) {
	window.sessionStorage.removeItem('formData');
	var buildWrap = document.querySelector('.build-wrap'),
    renderWrap = document.querySelector('.render-wrap'),
    editBtn = document.getElementById('edit-form'),
    formData = window.sessionStorage.getItem('formData'),
    editing = true,
    fbOptions = {
      dataType: 'json',
      disableFields: ['file', 'paragraph', 'header', 'hidden'],
      messages: {
    	    clearAllMessage: '确认要清除所有的字段吗?',
    	    clearAll: '清除',
    	    save: '预览',
    	    no: '否',
    	    yes: '是'
      }
    };

  if (formData) {
    fbOptions.formData = JSON.parse(formData);
  }
  
  if (json) {
	  fbOptions.formData = json;
  }
  
  $('#save-form').hide();
  var toggleEdit = function() {
    document.body.classList.toggle('form-rendered', editing);
    editing = !editing;
  };

  var formBuilder = $(buildWrap).formBuilder(fbOptions).data('formBuilder');
 
  $('.form-builder-save').click(function() {
    toggleEdit();
    //alert(formBuilder.formData);
    $(renderWrap).formRender({
      dataType: 'json',
      formData: formBuilder.formData
    });
    $('#save-form').show();
    window.sessionStorage.setItem('formData', JSON.stringify(formBuilder.formData));
  });

  editBtn.onclick = function() {
	$('#save-form').toggle();
    toggleEdit();
  };
  
  function groupParameter() {
	  var para = {};
	  para.formId = $('#formId').val();
	  para.formName = $('#formName').val();
	  para.rawJson = formBuilder.formData;
	  para.formDescription = $('#formDesc').val();
	  var i = 0;
	  var json =  $.parseJSON(formBuilder.formData);
	  for(var i = 0; i < json.length; i++) {
		 var field = json[i];
		 var prefix = "fields[" + i + "].";
		 for(var property in field) {
			 if (property != 'values') {
				 para[prefix + property] = field[property];
				 continue;
			 }
			 for(var j = 0; j < field['values'].length; j++) {
				 var option = field['values'][j];
				 var subPrefix = prefix + "options[" + j + "].";
				 for(var optProperty in option) {
					 para[subPrefix + optProperty] = option[optProperty];
				 }
				 para[subPrefix + "position"] = j;
			 }
		 }
	  }
	  return para;
  }
  
  $("#designForm").validate({
		rules: {
			formName: {
				required: true,
				minlength: 5
			}
		},
		messages: {
			formName: {
				required: "请输入表单的名字",
				minlength: "表单名字的长度要大于5"
			}
		},
		submitHandler: function(form){
			var para = groupParameter();
			var requestMethod = "POST";
			var requestUrl = $("#saveFormMeta").val();
			if ($("#formId").val()) {
				requestMethod = "PUT";
				requestUrl = requestUrl + "/" + $("#formId").val();
			}
			$.ajax({method: requestMethod, url: requestUrl, data: JSON.stringify(para), dataType: "json", contentType : "application/json"}).done(function( data ) {
				if (data.status == 'success') {
					$('#formId').val(data.data);
					$('#message').text('保存成功');
				}
    		});
        } 
  });
});