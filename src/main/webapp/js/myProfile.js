$(document).ready(function() {
	$('.savePassword').click(function() {
		$('#passwordForm').submit();
	});
	
	$("#passwordForm").validate({
		rules: {
			currentPassword: {
				required: true
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
			currentPassword: {
				required: '请输入必填项'
			},
			password: {
				required: '请输入新密码'
			},
			confirmPassword: {
				required: '请输入必填项',
				equalTo:"请输入相同的新密码"
			}
		},
		submitHandler: function(form) {
			var action = $("#passwordForm").attr("action");
			$.ajax({method: "POST", url: action, data: $(form).serialize()}).done(function( data ) {
				if (data.status == 'fail') {
					$('#message').removeClass('btn-success');
					$('#message').addClass('has-error');
					$('#message').text(data.message);
				} if (data.status == 'success') {
					$('#message').removeClass('has-error');
					$('#message').addClass('btn-success');
					$('#message').text(data.message);
					$('.savePassword').hide();
					$('#passwordForm').hide();
					$('.changePassword').show();
					$('.goToLogin').show();
				} else {
					$('#message').text(data.message);
				}
    		});
        } 
	});
	
	$('.changePassword').click(function() {
		$("#passwordForm").find(':input').val('');
		$('#passwordForm').show();
		$('.savePassword').show();
		$('#message').text('');
		$('.goToLogin').hide();
		$(this).hide();
	});
});