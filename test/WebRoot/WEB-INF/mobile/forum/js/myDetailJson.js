$("#pinglun").click(function(){
	$("#replydiv").css("display", "block");
});
$("#bigger").click(function(){ 
	var thisEle = $(".content").css("font-size");
	var textFontSize = parseFloat(thisEle , 10); 
	var unit = thisEle.slice(-2); //获取单位 
	if( 24 >= textFontSize){ 
		textFontSize += 2; 
	} 
	$(".content").css("font-size", textFontSize + unit);
	$(".content").css("line-height", (textFontSize*2-9) + unit); 
}); 
$("#smaller").click(function(){ 
	var thisEle = $(".content").css("font-size"); 
	var textFontSize = parseFloat(thisEle , 10); 
	var unit = thisEle.slice(-2); //获取单位 
	if( textFontSize >= 10 ){ 
		textFontSize -= 2; 
	} 
	$(".content").css("font-size", textFontSize + unit); 
	$(".content").css("line-height", (textFontSize*2-9) + unit); 
}); 
