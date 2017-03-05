$(document).ready(function() {
	$('.save').click(function() {
		$(':form[name=enrollForm]').submit();
	});
	 
	$('.register').click(function() {
		$(':input[name=status]').val('enroll');
		$(':form[name=enrollForm]').submit();
	});
});