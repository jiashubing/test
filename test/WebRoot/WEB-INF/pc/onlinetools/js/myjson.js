 $(function(){
       $("#insertStringBtn").click(function(){
           var st = $("#startText").val();
           if(st==""){
        	   alert("原文本不能为空！");
        	   return false;
           }
           var sc = $("#startChar").val();
           var ec = $("#endChar").val();
           if(sc=="" && ec==""){
        	   alert("行首、行尾增加的文本不能都为空！");
        	   return false;
           }
           $.ajax({
               url:"insertString",
               type:"post",
               dataType:"json",
               data:{"startText":st,"startChar":sc,"endChar":ec},
               success:function(result){
                if(result.status==1){
					var endTextAns = result.body; //角度 
					$("#endText").val(endTextAns);
				}
           	 },
            error: function () {
                alert("请求失败！");
            }
        }); 
      });
       
       
      $("#loadFile").live("change",function(){
   	    var filepath = $(this).val();
   	    var extStart = filepath.lastIndexOf(".");
   	    var ext = filepath.substring(extStart, filepath.length).toUpperCase();
   	    if (ext != ".TXT") {
   	        $("#loadFileErr").text("原始文本只能是txt格式");
   	        return false;
   	    }else{
   	    	$("#loadFileErr").text("");
   	    }
   	 $("#startPassText").text("123");
	   //ajax文件上传
   	    $.ajaxFileUpload({
   	        url: 'loadFile',
   	        secureuri: false,
   	        fileElementId: 'loadFile',
   	        dataType: 'json',
   	        data: "",
   	        success:function(result){
   	            if(result.status==1){
   	                $("#startPassText").text(result.body);
   	            }else {
   	            	alert("系统异常","请求失败");
   	            }
   	        },
   	        error:function(e){
   	        	alert("系统异常","请求失败");
   	        }
   	    });
   	});
      
      $("#customKey").blur(checkNum);
      function checkNum() {
      	var customKey = $("#customKey").val();
      	var reg = /^[0-9]*$/; 
      	if(customKey=="") {
      		$("#customKeyErr").text("密匙不能为空");
      		return false;
      	}
      	if(!reg.test(customKey)){
      		$("#customKeyErr").text("密匙是不能大于8位的数字");
            return false;
        }
      	$("#customKeyErr").text("");
      }
      
      
      
  });
