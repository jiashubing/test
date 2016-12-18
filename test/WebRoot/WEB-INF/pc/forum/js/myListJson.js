function deleteTopic(obj){
	if(confirm("您确定要删除这条数据吗？")){
		var topicId=$(obj).closest(".topictr").find(".topicid").text();
		$.ajax({
				url : "topicDelete",
				type : "post",
				data:{"topicId":topicId},
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
function modifyTopic(obj){
	var topicId = $(obj).closest(".topictr").find(".topicid").text();
	var topicTop = $(obj).closest(".topictr").find(".topictop").text();
	var topicGood = $(obj).closest(".topictr").find(".topicgood").text();
	$("#topicId").val(topicId);
	$("#topicTop").val(topicTop);
	$("#topicGood").val(topicGood);
}
function saveTopic(){
	var topicId=$("#topicId").val();
	var topicTop=$("#topicTop").val();
	var topicGood=$("#topicGood").val();
	$("#fm").submit();
	alert("保存成功！");
	resetValue();
}

function resetValue(){
	$("#topicId").val("");
	$("#topicTop").val("");
	$("#topicGood").val("");
}