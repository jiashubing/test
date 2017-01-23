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
				var con = confirm("恭喜你完成了数独，真厉害！" + "\n还要再来一次吗？");
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

function autoCheck(){
	var ans="";
	var tmp = ""; 
	var flag=true;
	$(".wo").each(function(i){
		tmp = $($(".wo")[i]).text();
		if(tmp==""){
			flag=false;
			return false;
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
				var con = confirm("恭喜你完成了数独，真厉害！" + "\n还要再来一次吗？");
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

 };
 
 
 $("#fuyuan").click(function(){
	 var con = confirm("你确定要丢失当前进度，并且重新开始吗？");
	 if (con) {
		 $(".zb").each(function(i){
			 $($(".zb")[i]).text("");
		 });
		 $(".wo").each(function(i){
			    $($(".wo")[i]).css("background","transparent");
			    $($(".wo")[i]).css("border","2px solid transparent");
		});
		time_stop();
		time_fun();
	 } else {
		 return false;
	 }				
 });
 
$("#newgame").click(function(){
	var con = confirm("你确定要开始一局新游戏吗？");
	if (con) {
		window.location.href = window.location.href;
	} else {
		return false;
	}
 });
 
 $(".wo").on("click",function(){
	$(".wo").each(function(i){
	    $($(".wo")[i]).css("background","transparent");
	    $($(".wo")[i]).css("border","2px solid transparent");
	});
 	var base = $(this).text();
 	if(base==""){
	   $(this).css("background","rgb(221, 221, 0)");
	   //同一列的边框
	   var num = $(".wo").index(this);
	   var x = Math.floor(num/27);
	   var y = num%27;
	   var z = Math.floor((num%9)/3);
	   if(x>1){
		   changeRow(x-2,y,z);
		   changeRow(x-1,y,z);
		   changeRow(x,y,z);
	   }else if(x>0){
		   changeRow(x-1,y,z);
		   changeRow(x,y,z);
		   changeRow(x+1,y,z);
	   }else{
		   changeRow(x,y,z);
		   changeRow(x+1,y,z);
		   changeRow(x+2,y,z);
	   }
	   
	   //同一行的边框
	   x = num%27;
	   y = Math.floor(num/27);
	   z = (num%9)%3;
	   
	   if(x>17){
		   changeCol(x-18,y,z);
		   changeCol(x-9,y,z);
		   changeCol(x,y,z);
	   }
	   else if(x>8){
		   changeCol(x-9,y,z);
		   changeCol(x,y,z);
		   changeCol(x+9,y,z);
	   }
	   else{
		   changeCol(x,y,z);
		   changeCol(x+9,y,z);
		   changeCol(x+18,y,z);
	   }
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
 
 function changeRow(x,y,z){
	 if(z==0){
		 $($(".wo").eq(x*27+y)).css("background","rgb(203, 255, 181)");
		 $($(".wo").eq(x*27+y+3)).css("background","rgb(203, 255, 181)");
		 $($(".wo").eq(x*27+y+6)).css("background","rgb(203, 255, 181)");
	 }else if(z==1){
		 $($(".wo").eq(x*27+y-3)).css("background","rgb(203, 255, 181)");
		 $($(".wo").eq(x*27+y)).css("background","rgb(203, 255, 181)");
		 $($(".wo").eq(x*27+y+3)).css("background","rgb(203, 255, 181)");
	 }else if(z==2){
		 $($(".wo").eq(x*27+y-6)).css("background","rgb(203, 255, 181)");
		 $($(".wo").eq(x*27+y-3)).css("background","rgb(203, 255, 181)");
		 $($(".wo").eq(x*27+y)).css("background","rgb(203, 255, 181)");
	 }
 }
 
 function changeCol(x,y,z){
	 if(z==0){
		 $($(".wo").eq(y*27+x)).css("background","rgb(203, 255, 181)");
		 $($(".wo").eq(y*27+x+1)).css("background","rgb(203, 255, 181)");
		 $($(".wo").eq(y*27+x+2)).css("background","rgb(203, 255, 181)");
	 }
	 else if(z==1){
		 $($(".wo").eq(y*27+x-1)).css("background","rgb(203, 255, 181)");
		 $($(".wo").eq(y*27+x)).css("background","rgb(203, 255, 181)");
		 $($(".wo").eq(y*27+x+1)).css("background","rgb(203, 255, 181)");
	 }
	 else if(z==2){
		 $($(".wo").eq(y*27+x-2)).css("background","rgb(203, 255, 181)");
		 $($(".wo").eq(y*27+x-1)).css("background","rgb(203, 255, 181)");
		 $($(".wo").eq(y*27+x)).css("background","rgb(203, 255, 181)");
	 }
 }
 
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
		$(".wo").each(function(i){
	    	tmp = $($(".wo")[i]).text();
	    	if(tmp==base){
	    		$($(".wo")[i]).css("border","2px solid rgb(255, 0, 0)");
	    	}else{
	    		$($(".wo")[i]).css("border","2px solid transparent");
	    	}
	    });
	}else if(base=="C"){
		var con = confirm("你确定要丢失当前进度，并且重新开始吗？");
		if (con) {
			$(".zb").each(function(i){
				$($(".zb")[i]).text("");
			});
			$(".wo").each(function(i){
			    $($(".wo")[i]).css("background","transparent");
			    $($(".wo")[i]).css("border","2px solid transparent");
			});
			time_stop();
			time_fun();
		} else {
			return false;
		}
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
		$(".wo").each(function(i){
	    	tmp = $($(".wo")[i]).text();
	    	if(tmp==base){
	    		$($(".wo")[i]).css("border","2px solid rgb(255, 0, 0)");
	    	}else{
	    		$($(".wo")[i]).css("border","2px solid transparent");
	    	}
	    });
		autoCheck();
	}
 });
 
 var timer;
 function two_char(n) {
     return n >= 10 ? n : "0" + n;
 }
 function time_fun() {
     var sec=0;
     clearInterval(timer);
     timer = setInterval(function () {
         sec++;
         var date = new Date(0, 0)
         date.setSeconds(sec);
         var m = date.getMinutes(), s = date.getSeconds();
         document.getElementById("y2").innerText =two_char(m) + ":" + two_char(s);
     }, 1000);
 }
 function time_stop(){
	clearInterval(timer);
	document.getElementById("y2").innerText = "00:00";
}