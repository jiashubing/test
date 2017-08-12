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
    
    //鼠标悬停在首页文字上
    $("#homeLi").on("mouseover",function(){
 	   $("#lbpo2").fadeIn("fast");
 	   $("#zhizheni2").fadeIn("fast");
 	});
    
    $("#lbpo2").on("mouseleave",function(){
    	$(this).fadeOut("fast");
    	$("#zhizheni2").fadeOut("fast");
    });
    
    $("input[id=password]").keypress(function(e){
        var eCode = e.keyCode ? e.keyCode : e.which ? e.which : e.charCode;
        if (eCode == 13){
        	$("#btn_login").click();
        }
    });
    
});