$(document).ready(function() {
	$("#enrollForm").validate();
	$.validator.addMethod("phone", function(value, element) { 
	    var tel = /^(130|131|132|133|134|135|136|137|138|139|150|153|157|158|159|180|181|182|183|184|185|186|187|188|189|170|171|172|173|174|177|178|175|176|179|)\d{8}$/; 
	    return tel.test(value) || this.optional(element); 
	}, "请输入正确的手机号码");
	$('.save').click(function() {		
		$('#enrollForm').submit();
	});
	 
	$('.register').click(function() {
		$(':input[name=status]').val('enroll');
		$('#enrollForm').submit();
	});
	
	$('.backToList').click(function() {
		var href = $( '.backToList').data( "href" );
		window.location.href = href; 
	});	
	
	$('.gotoanother').click(function() {
		window.location.href = $( ':input[name=existingEnroll]' ).val() + "?registerId=" +  $(this).data( "id");
	});
	
	$('.uniqueKey').next("input").blur(function() {
		var value = $.trim($(this).val());
		if (value == '') {
			return;
		}
		var formId = $(':input[name=formId]').val();
		var fieldId = $('.uniqueKey').val();
		var registerId = $(':input[name=registerId]').val();
		var url = $(':input[name=checkUniqueKey]').val() + '?formId=' + formId + '&fieldId=' + fieldId + '&value=' + value;
		var keyLabel = $('.uniqueKey').parent().find("label").text();
		$.getJSON(url, function( data ) {
			 if (data.status == 'success') {
				 if (data.data != '' && data.data != $.trim(registerId)) {
					 $('#message').text(keyLabel + "存在另一个报名信息");
					 $('.gotoanother').show();
					 $('.gotoanother').data("id", data.data);
					 $('.gotoanother').siblings('button').hide();
				 } else {
					 $('#message').text('');
					 $('.gotoanother').hide();
					 $('.gotoanother').data( "id",'');
					 $('.gotoanother').siblings('button').show();
				 }
			 }
		});
	});
});