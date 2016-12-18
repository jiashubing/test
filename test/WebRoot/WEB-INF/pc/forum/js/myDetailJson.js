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