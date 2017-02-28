jQuery(document).ready(function($) {
  var buildWrap = document.querySelector('.build-wrap'),
    renderWrap = document.querySelector('.render-wrap'),
    editBtn = document.getElementById('edit-form'),
    formData = window.sessionStorage.getItem('formData'),
    editing = true,
    fbOptions = {
      dataType: 'json'
    };

  if (formData) {
    fbOptions.formData = JSON.parse(formData);
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
  
  $('#save-form').click(function() {
	  var para = {};
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
	  
	  $.post( "/enrollment/saveForm.html", para, function( data ) {
		  if (data.status == '200') {
			  alert('保存成功');
		  }
	});
  });
});
