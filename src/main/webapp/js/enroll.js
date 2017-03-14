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
	
	
	$("#enrollForm").validate();
	$.validator.addMethod("telphone", function(value, element) { 
	    var tel = /^(130|131|132|133|134|135|136|137|138|139|150|153|157|158|159|180|187|188|189)\d{8}$/; 
	    return tel.test(value) || this.optional(element); 
	}, "请输入正确的手机号码");
});