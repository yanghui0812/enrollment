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
});