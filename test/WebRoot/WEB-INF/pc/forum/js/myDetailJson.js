function resetValue(){
	$("#replyContent").val("");
}
function deleteReply(obj){
	if(confirm("您确定要删除这条数据吗？")){
		var replyId=$(obj).closest(".replytr").find(".replyid").val();
		$.ajax({
				url : "replyDelete",
				type : "post",
				data:{"replyId":replyId},
				dataType : "json",
				success : function(result) {
					if (result.status == 1) {
						alert("删除成功！");
						window.location.reload(true);
					}
				},
				error : function() {
					alert("请求失败！");
				}
		});
	}
}
$("#bigger").click(function(){ 
	var thisEle = $(".show_e").css("font-size"); 
	var textFontSize = parseFloat(thisEle , 10); 
	var unit = thisEle.slice(-2); //获取单位 
	if( 24 >= textFontSize){ 
		textFontSize += 2; 
	} 
	$(".show_e").css("font-size", textFontSize + unit);
	$(".show_e").css("line-height", (textFontSize-2)*2 + unit); 
}); 
$("#smaller").click(function(){ 
	var thisEle = $(".show_e").css("font-size"); 
	var textFontSize = parseFloat(thisEle , 10); 
	var unit = thisEle.slice(-2); //获取单位 
	if( textFontSize >= 10 ){ 
		textFontSize -= 2; 
	} 
	$(".show_e").css("font-size", textFontSize + unit); 
	$(".show_e").css("line-height", (textFontSize-2)*2 + unit); 
}); 
$("#printPdf").click(function(){ 
	if(confirm("生成当前帖子的PDF文件，可以保存起来！" + "\n确定要继续吗？")){
		$("#printPdfForm").submit();
	}
}); 