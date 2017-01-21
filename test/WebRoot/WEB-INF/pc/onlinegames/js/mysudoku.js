 $("#tijiao").click(function(){
    var ans="";
    var tmp = ""; 
    var flag=true;
    $(".wo").each(function(i){
    	tmp = $($(".wo")[i]).text();
    	if(tmp==""){
    		alert("您必须填完所有的空格，才可以验证数独是否正确。");
    		flag=false;
    		return false;
    		/* tmp = "0";
    		ans += tmp; */
    	}else{
    		ans += tmp;
    	}
    });
    if(!flag){
    	return false;
    }
    
    $.ajax({
         url:"checkSudoku",
         type:"post",
         data:{"sudokuList":ans},
         dataType:"json",
         success:function(result){
         if(result.status==1){
         	var con = confirm("恭喜你完成了数独，你真厉害！" + "\n还要再来一次吗？");
			if (con) {
				window.location.href = window.location.href;
			} else {
				return false;
			}				
         }else{
             alert("数度验证失败，请再仔细检查下吧！");					
         }
     },
   error: function () {
       alert("请求失败！");
   }
  });
    
 });
 
 $("#fuyuan").click(function(){
    $(".zb").each(function(i){
	    $($(".zb")[i]).text("");
	});
 });
 
$("#newgame").click(function(){
   window.location.href = window.location.href;
 });
 
 $(".wo").on("click",function(){
	$(".wo").each(function(i){
	    $($(".wo")[i]).css("background","transparent");
	    $($(".wo")[i]).css("border","2px solid transparent");
	});
 	var base = $(this).text();
 	if(base==""){
	   $(this).css("background","rgb(221, 221, 0)");
 	}
 	else{
	 	$(".wo").each(function(i){
	    	tmp = $($(".wo")[i]).text();
	    	if(tmp==base){
	    		$($(".wo")[i]).css("border","2px solid rgb(255, 0, 0)");
	    	}else{
	    		$($(".wo")[i]).css("border","2px solid transparent");
	    	}
	    });
 	}
 });
 
 $(".zb").on("click",function(){
	$(this).css("background","rgb(221, 221, 0)");
 });
 
 $(".xa").on("click",function(){
	var base = $(this).text();
	if(base=="X"){
		var xflag=true;
		$(".zb").each(function(i){
	 		if($($(".zb")[i]).css("background-color") == "rgb(221, 221, 0)"){
	 			$($(".zb")[i]).text("");
	 			xflag=false;
	 			return;
	 		}
		});
		if(xflag){
			$("#k6").text("只能删除空格中的数字");
		}
	}else if(base=="^"){
		$(".zb").each(function(i){
	    	$($(".zb")[i]).text("");
		});
	}else{
		var nflag=true;
	 	$(".zb").each(function(i){
	 		if($($(".zb")[i]).css("background-color") == "rgb(221, 221, 0)"){
	 			$($(".zb")[i]).text(base);
	 			nflag=false;
	 			return;
	 		}
		});
		if(nflag){
			$("#k6").text("请先选择一个空格");
		}
	}
 });