$("#registButton").click(function() {
    var reg=/^(13|14|15|17|18)\d{9}$/;
    var nickName = $("#nickName").val();
    var trueName = $("#trueName").val();
    var email = $("#email").val();
    var newPwd = $("#newPwd").val();
    var sex = $("input[name='sex']:checked").val();
    var confirmPwd = $("#confirmPwd").val();
    var reg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/; 
	
    if(nickName=="") {
        $("#loginFailedInfo").text("昵称（用户名）不能为空!");
		$("#loginFailedInfo").fadeIn( 200 );
		setTimeout("shubingDelTime()", 1000);
		return false;
    }
    if(email=="") {
    	$("#loginFailedInfo").text("邮箱不能为空!");
    	$("#loginFailedInfo").fadeIn( 200 );
    	setTimeout("shubingDelTime()", 1000);
    	return false;
    }
    if(!reg.test(email)){
		$("#loginFailedInfo").text("请填入正确的邮箱!");
		$("#loginFailedInfo").fadeIn( 200 );
		setTimeout("shubingDelTime()", 1000);
		return false;
    }
    if(newPwd=="") {
    	$("#loginFailedInfo").text("登陆密码不能为空!");
    	$("#loginFailedInfo").fadeIn( 200 );
    	setTimeout("shubingDelTime()", 1000);
    	return false;
    }
    if(confirmPwd=="") {
    	$("#loginFailedInfo").text("确认密码不能为空!");
    	$("#loginFailedInfo").fadeIn( 200 );
    	setTimeout("shubingDelTime()", 1000);
    	return false;
    }
    if(confirmPwd != newPwd) {
    	$("#loginFailedInfo").text("两次密码不一致!");
    	$("#loginFailedInfo").fadeIn( 200 );
    	setTimeout("shubingDelTime()", 1000);
    	return false;
    }
    
    $("#loginFailedInfo").text("");
	shubingDelTime();
    $("#reg-formWrapper").submit();
    
});

function shubingDelTime(){
 	$("#loginFailedInfo").fadeOut( 200 );
}


$("#nickName").blur(checkNickName);
function checkNickName() {
    var nickName = $("#nickName").val();
    if(nickName=="") {
        $("#loginFailedInfo").text("昵称不能为空!");
		$("#loginFailedInfo").fadeIn( 200 );
		setTimeout("shubingDelTime()", 1000);
		return false;
    }
    $.ajax({
        url:"checkUserName",
        type:"post",
        dataType:"json",
        data:{"name":nickName},
        success:function(result){
            if(result.status==0) {
                $("#loginFailedInfo").text(result.message);
        		$("#loginFailedInfo").fadeIn( 200 );
        		setTimeout("shubingDelTime()", 1000);
            }else if(result.status==1){
            	$("#loginFailedInfo").text("该昵称可以使用!");
            	$("#loginFailedInfo").fadeIn( 200 );
            	setTimeout("shubingDelTime()", 1000);
            }
        },
        error:function(){
            alert("系统异常","请求失败");
        }
    });
}


$("#email").blur(checkEmail);
function checkEmail() {
	var email = $("#email").val();
	var reg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/; 
	if(email=="") {
		$("#loginFailedInfo").text("邮箱不能为空!");
		$("#loginFailedInfo").fadeIn( 200 );
		setTimeout("shubingDelTime()", 1000);
		return false;
	}
	if(!reg.test(email)){
		$("#loginFailedInfo").text("请填入正确的邮箱!");
		$("#loginFailedInfo").fadeIn( 200 );
		setTimeout("shubingDelTime()", 1000);
		return false;
    }
	$.ajax({
		url:"checkEmail",
		type:"post",
		dataType:"json",
		data:{"email":email},
		success:function(result){
			if(result.status==0) {
				$("#loginFailedInfo").text(result.message);
	        	$("#loginFailedInfo").fadeIn( 200 );
	        	setTimeout("shubingDelTime()", 1000);
			}else if(result.status==1){
            	$("#loginFailedInfo").text("该邮箱可以使用!");
            	$("#loginFailedInfo").fadeIn( 200 );
            	setTimeout("shubingDelTime()", 1000);
            }
		},
		error:function(){
			alert("系统异常","请求失败");
		}
	});
}