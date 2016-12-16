 $(function(){
 	//登陆框动态效果
    $("#showLoginForm").click(function(){
       $("#loginFailedInfo").text("");
       $("#asaimsg").show();
       $("#asaimsgbg").show();
   });
    
    //鼠标悬停在登陆头像上
    $("#memberLi").mouseenter(function(){
		$(".lbpo").fadeIn("fast");
		$(".zhizheni").fadeIn("fast");
	});
});