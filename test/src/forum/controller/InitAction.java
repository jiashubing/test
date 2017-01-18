package forum.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import config.ValidatePcMobile;
import forum.po.Topic;
import forum.po.Zone;
import forum.service.TopicService;
import forum.service.ZoneService;

@Controller
public class InitAction {
	
	@Resource(name="zoneServiceImpl")
	private ZoneService zoneService;
	
	@Resource(name="topicServiceImpl")
	private TopicService topicService;
	
	@RequestMapping("/forum")
	public String loadIndex(Model model,HttpServletRequest request)throws Exception{
		List<Zone> zoneList=zoneService.findZoneList(null, 10,0);
		
		List<Topic> goodTopicList = topicService.findGoodTopicListDesc(7, 0);
		List<Topic> newTopicList = topicService.findNewTopicListDesc(1, 0);
		
		Topic newTopic = newTopicList.get(0);
		
		model.addAttribute("zoneList",zoneList);
		model.addAttribute("goodTopicList",goodTopicList);
		model.addAttribute("newTopicList",newTopicList);
		model.addAttribute("newTopic",newTopic);
		model.addAttribute("flag","forum.html");  //此属性用来给前台确定当前是哪个页面
		return ValidatePcMobile.checkRequest(request, "/forum");
	}
	
	@RequestMapping("/forum/tuijian")
	public String loadTuijian(Model model,HttpServletRequest request)throws Exception{
		
		List<Topic> goodTopicList = topicService.findGoodTopicListDesc(30, 0);
		model.addAttribute("goodTopicList",goodTopicList);
		
		//返回上一页的路径，赋值到页面中
		String tmpPage = ValidatePcMobile.getDefaultPrePage(request);
		model.addAttribute("prePage", tmpPage);
		
		return ValidatePcMobile.checkRequest(request, "/forum/tuijian");
	}
	
}
