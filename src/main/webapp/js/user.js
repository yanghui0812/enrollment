$(document).ready(function() {
	
	$('.backToList').click(function() {
		var href = $( '.backToList').data( "href" );
		window.location.href = href;
	});
	
	$('.updateUser').click(function() {
		var href = $( '.updateUser').data( "href" );
		window.location.href = href;
	});
});