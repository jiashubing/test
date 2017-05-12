package common.controller;

import java.net.URL;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import common.po.Blog;
import common.service.BlogService;
import common.service.SubscribeService;
import config.ValidatePcMobile;

@Controller
public class SubscribeAction {
	
	@Resource(name="subscribeServiceImpl")
	private SubscribeService subscribeService;
	
	@Resource(name="blogServiceImpl")
	private BlogService blogService;
	
	@RequestMapping("/subscribe")
	public String loadSubscribe(@RequestParam(required=false)Integer showId,Model model,HttpServletRequest request)throws Exception{
		if(showId == null){
			showId = 1;
		}
		
		if(showId == 1){
			List<Blog> blogList = blogService.findBlogList(10, 0);
			//先从数据库里获取，主要目的是为了快速
			//数据库每一个小时更新一次博客园的数据，或者点击“更新”按钮，更新实时动态到数据库
			if(blogList == null || blogList.size()==0){
				blogList = blogService.updateBlogList(new URL("http://www.cnblogs.com/acm-bingzi/article/rss"));
			}
			model.addAttribute("blogList",blogList);
			if(!ValidatePcMobile.checkRequest(request)){
				return ValidatePcMobile.checkRequest(request, "/subscribe/blog");
			}
		}else if(showId == 2){
			
		}
		
		model.addAttribute("showId",showId);
		model.addAttribute("flag","subscribe.html");  //此属性用来给前台确定当前是哪个页面
		return ValidatePcMobile.checkRequest(request, "/subscribe");
	}
	
	
	@RequestMapping("/subscribe/updateBlog")
	public String updateBlog(Model model,HttpServletRequest request)throws Exception{
		Integer showId = 1;
		
		List<Blog> blogList = blogService.updateBlogList(new URL("http://www.cnblogs.com/acm-bingzi/article/rss"));
		if(blogList != null && blogList.size()>0){
			model.addAttribute("blogList",blogList);
		}
			
		if(!ValidatePcMobile.checkRequest(request)){
			return ValidatePcMobile.checkRequest(request, "/subscribe/blog");
		}
		
		model.addAttribute("showId",showId);
		model.addAttribute("flag","subscribe.html");  //此属性用来给前台确定当前是哪个页面
		return ValidatePcMobile.checkRequest(request, "/subscribe");
	}
	
	
	@RequestMapping("/mobilesubscribe")
	public String loadMobileSubscribe(Model model,HttpServletRequest request)throws Exception{
		return ValidatePcMobile.checkRequest(request, "/subscribes");
	}
}
