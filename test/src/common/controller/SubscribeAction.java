package common.controller;

import java.net.URL;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import common.service.SubscribeService;
import common.vo.DingYueVo;
import config.ValidatePcMobile;

@Controller
public class SubscribeAction {
	
	@Resource(name="subscribeServiceImpl")
	private SubscribeService subscribeService;
	
	@RequestMapping("/subscribe")
	public String loadSubscribe(@RequestParam(required=false)Integer showId,Model model,HttpServletRequest request)throws Exception{
		if(showId == null){
			showId = 1;
		}
		if(showId == 1){
			List<DingYueVo> subscribeList = subscribeService.parseXml(new URL("http://www.cnblogs.com/acm-bingzi/article/rss"));
			model.addAttribute("subscribeList",subscribeList);
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
