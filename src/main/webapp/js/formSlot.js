$(document).ready(function() {
	
	
	$('.update').click(function() {
		var href = $( '.update').data( "href" );
		window.location.href=href; 
	});	
	
	$('.backToList').click(function() {
		var href = $( '.backToList').data( "href" );
		window.location.href = href;
	});
	
});