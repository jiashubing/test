function autoScroll(obj){   
	$(obj).find("#shubing-news-list").animate({   
		marginTop : "-40px"   
	},500,function(){   
		$(this).css({marginTop : "0px"}).find("li:first").appendTo(this);   
	})   
}   
$(function(){   
	setInterval('autoScroll("#shubing-news-scroll")',3000)   
})   