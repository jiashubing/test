function saveReply(){
	/*<![CDATA[*/
 	var flag= /*[[${dbUser==null}]]*/true;
    /*]]>*/
    
    if(flag){
   	 	alert("请先登陆，再回帖！");
		return false;
    }
    
    var len = $("#myContent").val().length;
    if (10 > len) {
		alert("最少输入10个字符！");
		return false;
	}
	
	if (len > 1000) {
		alert("最多输入1000个字符！");
		return false;
	}
	
	$("#fm").submit();
	alert("回复成功！");
	resetValue();    
}
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