package forum.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import config.ValidatePcMobile;
import forum.po.Reply;
import forum.po.Section;
import forum.po.Topic;
import forum.po.Zone;
import forum.service.ReplyService;
import forum.service.SectionService;
import forum.service.TopicService;
import forum.service.ZoneService;

@Controller
public class TopicAction {
	
	public static int pageSize = 3;

	@Resource(name="zoneServiceImpl")
	private ZoneService zoneService;

	@Resource(name="topicServiceImpl")
	private TopicService topicService;

	@Resource(name="sectionServiceImpl")
	private SectionService sectionService;
	
	@Resource(name="replyServiceImpl")
	private ReplyService replyService;

	@RequestMapping("/forum/topicList")
	public String loadIndex(Integer id,@RequestParam(required = false) Integer pageNo,Model model,HttpServletRequest request)throws Exception{
		
		if (pageNo == null || pageNo < 0) {
            pageNo = 0;
        }
		
		Section section=sectionService.findSectionById(id);
		List<Topic> zdTopicList=topicService.findZdTopicListBySectionId(id,pageSize,pageNo);
		List<Topic> ptTopicList=topicService.findPtTopicListBySectionId(id,pageSize,pageNo);
		
		Map<Topic, Reply> topicLastReply=new HashMap<Topic, Reply>(0);
		Map<Topic, Long> topicReplyCount=new HashMap<Topic, Long>(0);
		for(int i=0;i<zdTopicList.size();i++){
			Topic topic = zdTopicList.get(i);
			Reply reply=replyService.findLastReplyByTopicId(topic.getId());
			Long replyCount=replyService.getReplyCountByTopicId(topic.getId());
			if (reply!=null) {
				topicLastReply.put(topic, reply);
			}
			topicReplyCount.put(topic, replyCount);
		}
		
		for(int i=0;i<ptTopicList.size();i++){
			Topic topic = ptTopicList.get(i);
			Reply reply=replyService.findLastReplyByTopicId(topic.getId());
			Long replyCount=replyService.getReplyCountByTopicId(topic.getId());
			if (reply!=null) {
				topicLastReply.put(topic, reply);
			}
			topicReplyCount.put(topic, replyCount);
		}
		
		//计算总页数
		//TODO 写成公共方法吧
		long totalNum=topicService.getPtTopicCountBySectionId(id);
		long totalPages=totalNum%pageSize==0?totalNum/pageSize:totalNum/pageSize+1;
		
		model.addAttribute("pageNo",pageNo); 
		model.addAttribute("totalPages",totalPages); 
		model.addAttribute("section",section); 
		model.addAttribute("zdTopicList",zdTopicList); 
		model.addAttribute("ptTopicList",ptTopicList); 
		model.addAttribute("topicLastReply",topicLastReply); 
		model.addAttribute("topicReplyCount",topicReplyCount); 
		model.addAttribute("flag","forum.html");  //此属性用来给前台确定当前是哪个页面
		return ValidatePcMobile.checkRequest(request, "/forum/topicList");
	}

	//	@RequestMapping("/forum")
	//	public String loadIndex(Model model,HttpServletRequest request)throws Exception{
	//		List<Zone> zoneList=zoneService.findZoneList(null, 10,0);
	//		model.addAttribute("zoneList",zoneList);
	//		
	//		for (Zone zone : zoneList) {
	//			for (Section section : zone.getSectionList()) {
	//				Topic s_topic=new Topic(); 
	//				s_topic.setSection(section);
	//				Long totalCount=topicService.getTopicCount(s_topic);			//
	//				s_topic.setGood(1);
	//				Long goodCount=topicService.getTopicCount(s_topic);			//
	//				s_topic.setGood(0);
	//				Long noReplyCount=topicService.getNoReplyTopicCount(s_topic);			//
	//				
	//				section.setTotalCount(totalCount);
	//				section.setGoodCount(goodCount);
	//				section.setNoReplyCount(noReplyCount);
	//				
	//				sectionService.saveSection(section);
	//			}
	//		}
	//		
	//		 model.addAttribute("flag","forum.html");  //此属性用来给前台确定当前是哪个页面
	//		return ValidatePcMobile.checkRequest(request, "/forum");
	//	}

}
