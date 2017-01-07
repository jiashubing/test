function showClear(obj){
	$(".input-clearValue").css("display","none");
	var len = $(obj).val().length;
	if(len>0){
		$(obj).next(".input-clearValue").css("display","block");
	}else{
		$(obj).next(".input-clearValue").css("display","none");
	}
};
function shubingClear(obj){
	$(obj).prev("input").val("");
	$(obj).css("display","none");
}