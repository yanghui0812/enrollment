$(document).ready(function() {
	$('.save').click(function() {		
		$('#enrollForm').submit();
	});
	 
	$('.register').click(function() {
		$(':input[name=status]').val('enroll');
		$('#enrollForm').submit();
	});
	
	$('.confirm').click(function() {
		$('#detailForm').submit();
	});
	
	$('.cancel').click(function() {
		$(':input[name=status]').val('cancel');
		$('#detailForm').submit();
	});
	
	$('.update').click(function() {
		var href = $( '.update').data( "href" );
		window.location.href=href; 
	});	
	
	$('.gotoanother').click(function() {
		window.location.href = $( ':input[name=existingEnroll]' ).val() + "?registerId=" +  $(this).data( "id");
	});
	
	$('.uniqueKey').next("input").blur(function() {
		var formId = $(':input[name=formId]').val();
		var fieldId = $('.uniqueKey').val();
		var value = $.trim($(this).val());
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
	
	$("#enrollForm").validate();
	$.validator.addMethod("telphone", function(value, element) { 
	    var tel = /^(130|131|132|133|134|135|136|137|138|139|150|153|157|158|159|180|187|188|189)\d{8}$/; 
	    return tel.test(value) || this.optional(element); 
	}, "请输入正确的手机号码");
});