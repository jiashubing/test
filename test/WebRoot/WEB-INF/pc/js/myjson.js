 $(function(){
 	//登陆框动态效果
    $("#showLoginForm").click(function(){
       $("#loginFailedInfo").text("");
       $("#asaimsg").show();
       $("#asaimsgbg").show();
   });
    
    //登陆框动态效果
    $("#showLoginForm2").click(function(){
    	$("#loginFailedInfo").text("");
    	$("#asaimsg").show();
    	$("#asaimsgbg").show();
    });
    
    //登陆框动态效果
    $("#btn_login").click(function(){
    	var username = $("#username").val();
    	var password = $("#password").val();
    	if(username==""){
    		$("#loginFailedInfo").text("用户名不能为空");
    		return false;
    	}
    	if(password==""){
    		$("#loginFailedInfo").text("密码不能为空");
    		return false;
    	}
    	$("#loginFailedInfo").text("");
    	
    	var beforepath = $("#beforeloginpath").val();
    	var beforepar = $("#beforeloginpar").val();
    	$.ajax({
    		url:"mylogin",
    		type:"post",
    		data:{"beforepath":beforepath,"beforepar":beforepar},
    		dataType:"json",
    		success:function(result){
    			$("#login_form").submit();
    		},
    		error: function () {
    			$("#login_form").submit();
    		}
    	});
    	
    });
    
    //鼠标悬停在登陆头像上
    $("#memberLi").mouseenter(function(){
		$(".lbpo").fadeIn("fast");
		$(".zhizheni").fadeIn("fast");
	});
    
    //鼠标悬停在控制台文字上
    $("#loginLi").on("mouseover",function(){
 	   $(".lbpo").fadeIn("fast");
 	   $(".zhizheni").fadeIn("fast");
 	});
    
    $(".lbpo").on("mouseleave",function(){
    	$(this).fadeOut("fast");
    	$(".zhizheni").fadeOut("fast");
    });
    
    
});