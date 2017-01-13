$(function() {
    $("#submitNewPwdButton").click(function () {
        var oldPwd = $("#oldPwd").val();
        var newPwd = $("#newPwd").val();
        var confirmPwd = $("#confirmPwd").val();
        if(oldPwd==""){
            $("#changePwdFailed").text("密码不能为空！");
        }else if(newPwd==""){
            $("#changePwdFailed").text("密码不能为空！");
        }else if(newPwd!==confirmPwd){
            $("#changePwdFailed").text("两次密码不同！");
        }else {
            $.ajax({
                url:"changePassword",
                type:"post",
                dataType:"json",
                data:{"oldPwd":oldPwd,"newPwd":newPwd},
                success:function(result){
                    if(result.status==1){
                        $("#changePwdSuccess").text(result.message);
                        $("#changePwdFailed").text("");
                    }else if(result.status==0){
                        $("#changePwdFailed").text(result.message);
                        $("#changePwdSuccess").text("");
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