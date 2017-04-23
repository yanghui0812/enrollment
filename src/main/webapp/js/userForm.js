$(document).ready(function() {
	
	$('.backToList').click(function() {
		var href = $( '.backToList').data( "href" );
		window.location.href = href;
	});
		
	$('.saveUser').click(function() {
		$('#userForm').submit();
	});
	
	$.validator.addMethod("pattern", function(value, element, params) { 
	    var pattern = new RegExp($(element).attr('pattern'));
	    return pattern.test(value) || this.optional(element); 
	}, "输入不合法");
	
	$("#userForm").validate({
		rules: {
			name: {
				required: true,
				pattern: []
			}
		},
		messages: {
			name: {
				required: '请输入必填项',
				pattern: '请输入合法的用户名，包含3到15个(数字，字母或下划线)'
			}
		},
		submitHandler: function(form) {
			$.ajax({method: "POST", url: $('#checkUsernameUrl').val(), data: {'username': $( ':input[name=name]' ).val()}}).done(function( data ) {
				if (data.status == 'success') {
					form.submit();
				} else {
					$("#name-error").show();
					$("#name-error").text("用户名已存在，请重新输入");
				}
			});
			
        } 
	});
});