$("#feedbackContent").blur(checkFeedbackContent);
 function checkFeedbackContent() {
    var len = $("#feedbackContent").val().length;
    if(len < 10){
    	$("#feedbackMsgInfo").text("反馈信息最少10个字");
        $("#feedbackMsgInfo").fadeIn( 200 );
        setTimeout("shubingDelTime()", 2000);
		return false;
    }
    if(len >1000){
    	$("#feedbackMsgInfo").text("反馈信息最多1000字");
        $("#feedbackMsgInfo").fadeIn( 200 );
        setTimeout("shubingDelTime()", 2000);
		return false;
    }
}
 $("#feedbackEmail").blur(checkFeedbackEmail);
 function checkFeedbackEmail() {
 	var email = $("#feedbackEmail").val();
 	if(email.trim()==""){
 		return;
 	}
 	var reg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/; 
 	if(!reg.test(email)){
        $("#feedbackMsgInfo").text("邮箱格式不正确");
        $("#feedbackMsgInfo").fadeIn( 200 );
        setTimeout("shubingDelTime()", 2000);
		return false;
    }
 }
 
 $("#feedbackBtn").click(function() {
	 var len = $("#feedbackContent").val().length;
	 if(len < 10){
		 $("#feedbackMsgInfo").text("反馈信息最少10个字");
	     $("#feedbackMsgInfo").fadeIn( 200 );
	     setTimeout("shubingDelTime()", 2000);
	     return false;
	 }
	 if(len >1000){
		 $("#feedbackMsgInfo").text("反馈信息最多1000字");
         $("#feedbackMsgInfo").fadeIn( 200 );
         setTimeout("shubingDelTime()", 2000);
	     return false;
	 }
	 var email = $("#feedbackEmail").val();
 	if(email.trim()!=""){
 		var reg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/; 
 		if(!reg.test(email)){
 			$("#feedbackMsgInfo").text("邮箱格式不正确");
 	        $("#feedbackMsgInfo").fadeIn( 200 );
 	        setTimeout("shubingDelTime()", 2000);
 			return false;
 		}
 	}
	 $("#feedbackFm").submit();
 });
 
 function shubingDelTime(){
 	$("#feedbackMsgInfo").fadeOut( 200 );
 }