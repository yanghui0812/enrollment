$(document).ready(function() {
	$( "#saveform" ).click(function() {
		buildFromData();
		$.post( "/enrollment/enroll/saveDesignForm.html", buildFromData(), function( data ) {
			  alert(data);
		});
	});
	
	function buildFromData() {
		var fields = [];
		var param = {};
		var token = 'formFieldMetaList[#]';
		$( "#designForm" ).find( ".row > .form-group" ).each(function( i, top) {
			var field = {};
			field['required'] = $.trim($(top).find( "label>span:eq(0)" ).text()) != '';
			field['fieldName'] =  $(top).find( "label>span:eq(1)" ).text();
			if ($(top).children( "span:eq(0)").find( "input" ).length > 0) {
				field['fieldType'] = 'text';
			}
			
			field.fieldOptionList = [];
     		$(top).children( "span:eq(0)").find( "select > option" ).each(function( optionIndex, option) {
     			field['fieldType'] = 'select';
     			var optionObj = {};
     			optionObj['value'] = $(option).text();
     			field.fieldOptionList.push(optionObj);
     		});
     		
			$(top).children( "span:eq(0)").find( "ul > label" ).each(function( optionIndex, option) {
				field['fieldType'] = $(option).children( "input").prop("type");
				var optionObj = {};
				optionObj['display'] = $(option).children( "span").text();
				field.fieldOptionList.push(optionObj);
			});
			fields.push(field);
		});
		
		for (var i = 0; i < fields.length; i++) {
			var field = fields[i];
			var prefix = 'formFieldMetaList[#]'.replace('#', i);
			for (property in field) {
				var name = prefix + '.' + property
				if (!$.isArray(field[property])) {
					param[name] = field[property];
					continue;
				}
				
				for (var j = 0; j < field[property].length; j++) {
					var subPrefix = '.fieldOptionList[#]'.replace('#', j);
					for (subProperty in field[property][j]) {
						var subName = prefix + subPrefix + '.' + subProperty;
						param[subName] = field[property][j][subProperty];
					}
				}
			}
		}
		param['formName'] = $( "#formName" ).val();
		param['formDesc'] = $( "#formDesc" ).val();
		alert($.param( param));
		return param;
	}
});