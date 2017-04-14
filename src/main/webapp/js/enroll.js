$(document).ready(function() {
	
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
	
	$('.backToList').click(function() {
		var href = $( '.backToList').data( "href" );
		window.location.href = href;
	});
	
	$('.back').click(function() {
		var href = $( '.back').data( "href" );
		window.location.href = href;
	});	
});