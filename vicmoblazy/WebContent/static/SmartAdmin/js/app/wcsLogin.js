$(document).ready(function () {

});


function login() {

	var name = $("#name").val();
	var pass = $("#pass").val();

	if (name == "" || pass == "") {
		ezi.msg.warning("用户名和密码不能为空!");
	} else
		ezi.ajax({
			type : "POST",
			url : API_ACCOUNT_LOGIN,
			data : {
				name : name,
				pass : pass
			},
			homeiferror : false,
			success : function(result) {
				if (result.status)
					window.location = "index.html#ajax/home.html";
				else
					ezi.msg.warning(result.message);
			}
		});
}