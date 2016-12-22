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
    	$("#loadFileFlag").val("0");
   	    var filepath = $(this).val();
   	    var extStart = filepath.lastIndexOf(".");
   	    var ext = filepath.substring(extStart, filepath.length).toUpperCase();
   	    if (ext != ".TXT") {
   	        $("#loadFileErr").text("原始文本只能是txt格式");
   	        $("#startPassText").text("");
   	        return false;
   	    }else{
   	    	$("#loadFileErr").text("");
   	    	$("#startPassText").text("");
   	    }
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
   	                $("#inName").val(result.message);
   	                
   	                $("#loadFileFlag").val("1");
   	            }else {
   	            	$("#startPassText").text("");
   	            	alert("系统异常","请求失败");
   	            }
   	        },
   	        error:function(e){
   	        	$("#startPassText").text("");
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
      		$("#customKeyErr").text("密匙必须是数字数字");
            return false;
        }
      	if(customKey.length>8){
      		$("#customKeyErr").text("密匙不能超过8位");
            return false;
        }
      	$("#customKeyErr").text("");
      }
      
      $("#encryptionBtn").click(function(){
    	  var loadFileFlag = $("#loadFileFlag").val();
    	  //不为空并且格式正确
    	  if(loadFileFlag != "1" ){
    		  alert("请导入正确格式的原始文本");
    		  return false;
    	  }
    	  var customKey = $("#customKey").val();
    	  var customKeyErr = $("#customKeyErr").val();
    	  if(customKey == "" || customKeyErr != ""){
    		  alert("请输入正确的密匙");
    		  return false;
    	  }
    	  //$("#cusKeyFm").submit();
    	  var inName = $("#inName").val();
    	  var customKey = $("#customKey").val();
    	  $.ajax({
              url:"customEncryption",
              type:"post",
              dataType:"json",
              data:{"inName":inName,"customKey":customKey},
              success:function(result){
               if(result.status==1){
					var endPassText = result.body.endPassText;  
					var outName = result.body.outName;  
					var outPath = result.body.outPath;  
					$("#endPassText").val(endPassText);
					$("#outName").val(outName);
					$("#outPath").val(outPath);
				}
          	 },
           error: function () {
               alert("请求失败！");
           }
    	  }); 
       }); 
      
      $("#deleteBtn").click(function(){
    	  if(confirm("确定要删除服务器上的对应文件吗?")){
    		  var outName= $("#outName").val();
    		  $.ajax({
    			  url : "deleteFile",
    			  type : "post",
    			  data:{"outName":outName},
    			  dataType : "json",
    			  success : function(result) {
    				  if (result.status == 1) {
    					  alert("删除成功！");
    				  }
    				  else{
    					  alert(result.message);
    				  }
    			  },
    			  error : function() {
    				  alert("请求失败！");
    			  }
    		  });
    	  }
       }); 
      
      
      $("#downloadBtn").click(function(){
    	  var outName= $("#outName").val();
    	  console.log("outName = "+outName);
    	  $("#downloadHref").attr("href",$("#tempHref").attr("href")+"?outName="+outName);
    	  console.log($("#downloadHref").attr("href"));
      }); 
      
      
      
  });
