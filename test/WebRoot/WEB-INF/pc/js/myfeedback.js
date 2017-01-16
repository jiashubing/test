$("#feedbackContent").blur(checkFeedbackContent);
 function checkFeedbackContent() {
    var len = $("#feedbackContent").text().length;
    if(len < 10){
    	alert("请详细描述您的反馈信息，至少10个字。");
    }
    if(len >1000){
    	alert("请将您的描述控制在1000 字以内，如有更多内容请分多次提交。");
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
        alert("邮箱格式不正确。");
    }
 }
 
 $("#feedbackBtn").click(function() {
	 var len = $("#feedbackContent").text().length;
	 if(len < 10){
		 alert("请详细描述您的反馈信息，至少10个字。");
		 return false;
	 }
	 if(len >1000){
		 alert("请将您的描述控制在1000 字以内，如有更多内容请分多次提交。");
		 return false;
	 }
	 var email = $("#feedbackEmail").val();
 	if(email.trim()!=""){
 		var reg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/; 
 		if(!reg.test(email)){
 			alert("邮箱格式不正确。");
 			return false;
 		}
 	}
	 $("#feedbackFm").submit();
 });