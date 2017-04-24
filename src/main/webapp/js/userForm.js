$(document).ready(function() {
	
	$('.backToList').click(function() {
		var href = $( '.backToList').data( "href" );
		window.location.href = href;
	});
		
	$('.saveUser').click(function() {
		$('#userForm').submit();
	});
	
	$.validator.addMethod("tel", function(value, element) { 
	    var tel = /^(130|131|132|133|134|135|136|137|138|139|150|153|157|158|159|180|181|182|183|184|185|186|187|188|189|170|171|172|173|174|177|178|175|176|179|)\d{8}$/; 
	    return tel.test(value) || this.optional(element); 
	}, "请输入正确的手机号码");
	
	$.validator.addMethod("pattern", function(value, element, params) { 
	    var pattern = new RegExp($(element).attr('pattern'));
	    return pattern.test(value) || this.optional(element); 
	}, "输入不合法");
	$("#userForm").validate({
		rules: {
			name: {
				required: true,
				pattern: []
			},
			password: {
				required: true,
				rangelength:[8,12]
			},
			confirmPassword: {
				required: true,
				equalTo:'#password',
				rangelength:[8,12]
			}
		},
		messages: {
			name: {
				required: '请输入必填项',
				pattern: '请输入合法的用户名，包含3到15个(数字，字母或下划线)'
			},
			password: {
				required: '请为新用户输入密码'
			},
			confirmPassword: {
				required: '请再次为新用户输入密码',
				equalTo:"请输入相同的密码"
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