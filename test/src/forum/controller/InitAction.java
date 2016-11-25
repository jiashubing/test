package forum.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import config.ValidatePcMobile;
import forum.po.Section;
import forum.po.Topic;
import forum.po.Zone;
import forum.service.SectionService;
import forum.service.TopicService;
import forum.service.ZoneService;

@Controller
public class InitAction {
	
	@Resource(name="zoneServiceImpl")
	private ZoneService zoneService;
	
	@Resource(name="topicServiceImpl")
	private TopicService topicService;
	
	@Resource(name="sectionServiceImpl")
	private SectionService sectionService;
	
	@RequestMapping("/forum/index")
	public String loadIndex(Model model,HttpServletRequest request)throws Exception{
		List<Zone> zoneList=zoneService.findZoneList(null, 10,0);
		model.addAttribute("zoneList",zoneList);
		
		for (Zone zone : zoneList) {
			for (Section section : zone.getSectionList()) {
				Topic s_topic=new Topic(); 
				s_topic.setSection(section);
				Long totalCount=topicService.getTopicCount(s_topic);			//
				s_topic.setGood(1);
				Long goodCount=topicService.getTopicCount(s_topic);			//
				s_topic.setGood(0);
				Long noReplyCount=topicService.getNoReplyTopicCount(s_topic);			//
				
				section.setTotalCount(totalCount);
				section.setGoodCount(goodCount);
				section.setNoReplyCount(noReplyCount);
				
				sectionService.saveSection(section);
			}
		}
		
		return ValidatePcMobile.checkRequest(request, "/forum/index");
	}
	
}
