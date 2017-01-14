$(function() {
    $("#submitNewPwdButton").click(function () {
        var oldPwd = $("#oldPwd").val();
        var newPwd = $("#newPwd").val();
        var confirmPwd = $("#confirmPwd").val();
        if(oldPwd==""){
			$("#changedFailedInfo").text("密码不能为空！");
			$("#changedFailedInfo").fadeIn( 200 );
			setTimeout("shubingDelTime()", 3000);
			return false;
            
            
        }else if(newPwd==""){
            $("#changedFailedInfo").text("密码不能为空！");
			$("#changedFailedInfo").fadeIn( 200 );
			setTimeout("shubingDelTime()", 3000);
			return false;
        }else if(newPwd!==confirmPwd){
        	$("#changedFailedInfo").text("两次密码不同！");
        	$("#changedFailedInfo").fadeIn( 200 );
        	setTimeout("shubingDelTime()", 3000);
        }else {
            $.ajax({
                url:"changePassword",
                type:"post",
                dataType:"json",
                data:{"oldPwd":oldPwd,"newPwd":newPwd},
                success:function(result){
                    if(result.status==1){
                        $("#changedFailedInfo").text(result.message);
                        $("#changedFailedInfo").fadeIn( 200 );
                        setTimeout("location.href='login'", 2000);
                    }else if(result.status==0){
                        $("#changedFailedInfo").text(result.message);
                        $("#changedFailedInfo").fadeIn( 200 );
                    	setTimeout("shubingDelTime()", 3000);
                    }
                },
                error:function(){
                    alert("系统错误,修改密码失败！");
                }
            });
            $("#oldPwd").val("");
            $("#newPwd").val("");
            $("#confirmPwd").val("");
        }
    })
});
function shubingDelTime(){
 	$("#changedFailedInfo").fadeOut( 200 );
 }