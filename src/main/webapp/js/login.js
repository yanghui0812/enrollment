$().ready(function() {
	refreshPic();
	$("#loginForm").validate({
		onfocusout : function(element) {
			$(element).valid();
		},
		onkeyup : function(element) {
			$(element).valid();
		},
		rules : {
			username : {
				required : true,
				minlength : 2
			},
			password : {
				required : true,
				minlength : 6
			},
			agree : "required"
		},
		messages : {
			username : {
				required : "请输入用户名",
				minlength : "用户名必需由两个字母组成"
			},
			password : {
				required : "请输入密码",
				minlength : "密码最少输入六位"
			}
		}
	});
});