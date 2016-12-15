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
  });
