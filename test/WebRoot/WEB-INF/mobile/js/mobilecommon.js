//PC端、手机端有同样的代码
 function zxingClick(){
 	var beforepath = $("#beforeloginpath").val();
 	var beforepar = $("#beforeloginpar").val();
 	$.ajax({
 		url:"qrcode",
 		type:"post",
 		data:{"beforepath":beforepath,"beforepar":beforepar},
 		dataType:"json",
 		success:function(result){
 			var str = result.message;
 			var con = confirm("生成当前页面的二维码，用来分享最好不过了！" + "\n确定要继续吗？");
 			if (con) {
 				$("#zxingpath").val(str);
 				$("#zxing_form").submit();
 			} else {
 				return false;
 			}	
 		},
 		error: function () {
 			alert("抱歉出错了！")
 		}
 	});
 }