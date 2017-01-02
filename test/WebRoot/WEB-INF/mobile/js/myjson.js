 $(function(){
    $("#login-submit").click(function(){
    	var username = $("#login-username").val();
    	var password = $("#login-password").val();
    	if(username==""){
    		$("#loginFailedInfo").text("用户名不能为空!");
    		$("#loginFailedInfo").fadeIn( 200 );
    		setTimeout("shubingDelTime()", 3000);
    		return false;
    	}
    	if(password==""){
    		$("#loginFailedInfo").text("密码不能为空!");
    		$("#loginFailedInfo").fadeIn( 200 );
    		setTimeout("shubingDelTime()", 3000);
    		return false;
    	}
    	$("#loginFailedInfo").text("");
    	shubingDelTime();
    	$("#login-formWrapper").submit();
   });
});
 function shubingDelTime(){
 	$("#loginFailedInfo").fadeOut( 200 );
 }