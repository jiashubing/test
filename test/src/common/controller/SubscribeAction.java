package common.controller;

import java.net.URL;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import common.entity.Result;
import common.po.Blog;
import common.po.Weather;
import common.po.WeiBo;
import common.service.BlogService;
import common.service.WeatherService;
import common.service.WeiBoService;
import config.ValidatePcMobile;

@Controller
public class SubscribeAction {
	
	private static final String URL_BLOG = "http://www.cnblogs.com/acm-bingzi/article/rss";
	private static final String URL_WEIBO = "http://rss.weibodangan.com/weibo/rss/3225300442/";
	
	@Resource(name="blogServiceImpl")
	private BlogService blogService;
	
	@Resource(name="weiBoServiceImpl")
	private WeiBoService weiBoService;
	
	@Resource(name="weatherServiceImpl")
	private WeatherService weatherService;
	
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
				blogList = blogService.updateBlogList(new URL(URL_BLOG));
			}
			model.addAttribute("blogList",blogList);
			if(!ValidatePcMobile.checkRequest(request)){
				return ValidatePcMobile.checkRequest(request, "/subscribe/blog");
			}
		}else if(showId == 2){
			if(!ValidatePcMobile.checkRequest(request)){
				return ValidatePcMobile.checkRequest(request, "/subscribe/weather");
			}
		}else if(showId == 3){
			List<WeiBo> weiBoList = weiBoService.findWeiBoList(10, 0);
			//先从数据库里获取，主要目的是为了快速
			//数据库每一个小时更新一次微博的数据，或者点击“更新”按钮，更新实时动态到数据库
			if(weiBoList == null || weiBoList.size()==0){
				weiBoList = weiBoService.updateWeiBoList(new URL(URL_WEIBO));
			}
			model.addAttribute("weiBoList",weiBoList);
			if(!ValidatePcMobile.checkRequest(request)){
				return ValidatePcMobile.checkRequest(request, "/subscribe/weibo");
			}
		}
		
		model.addAttribute("showId",showId);
		model.addAttribute("flag","subscribe.html");  //此属性用来给前台确定当前是哪个页面
		return ValidatePcMobile.checkRequest(request, "/subscribe");
	}
	
	
	@RequestMapping("/subscribe/updateBlog")
	public String updateBlog(Model model,HttpServletRequest request)throws Exception{
		Integer showId = 1;
		
		List<Blog> blogList = blogService.updateBlogList(new URL(URL_BLOG));
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
	
	@RequestMapping("/subscribe/updateWeiBo")
	public String updateWeiBo(Model model,HttpServletRequest request)throws Exception{
		Integer showId = 3;
		
		List<WeiBo> weiBoList = weiBoService.updateWeiBoList(new URL(URL_WEIBO));
		if(weiBoList != null && weiBoList.size()>0){
			model.addAttribute("weiBoList",weiBoList);
		}
			
		if(!ValidatePcMobile.checkRequest(request)){
			return ValidatePcMobile.checkRequest(request, "/subscribe/weibo");
		}
		
		model.addAttribute("showId",showId);
		model.addAttribute("flag","subscribe.html");  //此属性用来给前台确定当前是哪个页面
		return ValidatePcMobile.checkRequest(request, "/subscribe");
	}
	
	
	@RequestMapping("/mobilesubscribe")
	public String loadMobileSubscribe(HttpServletRequest request)throws Exception{
		return ValidatePcMobile.checkRequest(request, "/subscribes");
	}
	
	@RequestMapping(value={"/subscribe/mobileweather","/pcweather"})
	@ResponseBody
	public Result getWeather(String cityName) {
		Weather weather = null;
		try{
			String tmp = cityName.substring(cityName.indexOf('(')+1,cityName.indexOf(')'));
			weather = weatherService.getRemoteWeather(tmp);
		}catch (Exception e){
			//do nothing
		}
		Result result = new Result();
		if(weather!=null){
			result.setStatus(1);
			result.setBody(weather);
		}else{
			result.setStatus(0);
		}
		return result;
	}
}
