package forum.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import common.entity.Result;
import config.ValidatePcMobile;
import forum.po.DbUser;
import forum.po.Reply;
import forum.po.Section;
import forum.po.Topic;
import forum.po.User;
import forum.service.ReplyService;
import forum.service.SectionService;
import forum.service.TopicService;
import forum.service.UserService;
import forum.service.ZoneService;
import forum.util.PageUtil;

@Controller
public class TopicAction {
	
	public static int PageSize = 10;
	public static int MaxPageSize = 100;

	@Resource(name="zoneServiceImpl")
	private ZoneService zoneService;

	@Resource(name="topicServiceImpl")
	private TopicService topicService;

	@Resource(name="sectionServiceImpl")
	private SectionService sectionService;
	
	@Resource(name="replyServiceImpl")
	private ReplyService replyService;
	
	@Resource(name="userServiceImpl")
	private UserService userService;

	@RequestMapping("/forum/topicList")
	public String loadTopicList(@AuthenticationPrincipal DbUser dbUser,Integer sectionId,@RequestParam(required = false) Integer pageNo,Model model,HttpServletRequest request)throws Exception{
		if(dbUser != null){
			model.addAttribute("dbUser",dbUser);
		}
		
		pageNo = PageUtil.initPageNo(pageNo);
		
		Section section=sectionService.findSectionById(sectionId);
		List<Topic> zdTopicList=topicService.findZdTopicListBySectionId(sectionId,PageSize,0);
		List<Topic> ptTopicList=topicService.findPtTopicListBySectionId(sectionId,PageSize,pageNo);
		
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
		
		long totalNum=topicService.getPtTopicCountBySectionId(sectionId);
		int totalPages = PageUtil.getTotalPages(totalNum, PageSize);
		
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
	
	/**
	 * 后台加载帖子列表
	 */
	@RequestMapping("/admin/topicList")
	public String loadAdminTopicList(
			@RequestParam(required = false) Integer pageNo,
			@RequestParam(required = false) String s_title,
			@RequestParam(required = false) String s_userNickName,
			@RequestParam(required = false) Integer s_sectionId,
			@RequestParam(required = false) Integer s_top,
			@RequestParam(required = false) Integer s_good,
			Model model,HttpServletRequest request)throws Exception{
		
		pageNo = PageUtil.initPageNo(pageNo);
		
		Topic s_topic = new Topic();
		if(s_title != null){
			s_topic.setTitle(s_title);
		}
		if(s_userNickName != null){
			User tmp = userService.getUserByNickName(s_userNickName);
			s_topic.setUser(tmp);
		}
		if(s_sectionId != null){
			Section tmp = sectionService.findSectionById(s_sectionId);
			s_topic.setSection(tmp);
		}
		if(s_top != null){
			s_topic.setTop(s_top);
		}else{
			//必填项，设成2不查询
			s_topic.setTop(2);
		}
		if(s_good != null){
			s_topic.setGood(s_good);
		}else{
			//必填项，设成2不查询
			s_topic.setGood(2);
		}
		
		List<Topic> topicList = topicService.findTopicList(s_topic,PageSize,pageNo);
		Long totalNum = topicService.getTopicCount(s_topic);
		int totalPages = PageUtil.getTotalPages(totalNum, PageSize);
		List<Section> sectionList = sectionService.findSectionList(null,MaxPageSize,pageNo);
		
		String mainPage="topic.html";
		
		model.addAttribute("pageNo",pageNo); 
		model.addAttribute("mainPage",mainPage); 
		model.addAttribute("totalPages",totalPages); 
		model.addAttribute("s_topic",s_topic); 
		model.addAttribute("topicList",topicList); 
		model.addAttribute("sectionList",sectionList); 
		model.addAttribute("flag","forum.html");  //此属性用来给前台确定当前是哪个页面
		return ValidatePcMobile.checkRequest(request, "/admin/main");
	}
	
	/**
	 * 管理员页面 异步 删除topic
	 */
	@RequestMapping("/admin/topicDelete")
    @ResponseBody
    public Result deleteTopicAdmin(Integer topicId,HttpServletRequest request) {
        Result result = new Result();
        replyService.deleteReplyByTopicId(topicId);
        topicService.deleteTopicById(topicId);
        result.setStatus(1);
        return result;
    }
	
	/**
	 * 论坛页面 异步 删除topic
	 */
	@RequestMapping("/forum/topicDelete")
	@ResponseBody
	public Result deleteTopicForum(Integer topicId,HttpServletRequest request) {
		Result result = new Result();
		replyService.deleteReplyByTopicId(topicId);
		topicService.deleteTopicById(topicId);
		result.setStatus(1);
		return result;
	}
	
	@RequestMapping("/forum/details")
	public String loadDetails(@AuthenticationPrincipal DbUser dbUser,Integer id,Model model,@RequestParam(required = false) Integer pageNo,HttpServletRequest request)throws Exception{
		if(dbUser != null){
			model.addAttribute("dbUser",dbUser);
		}
		
		Topic topic = topicService.findTopicById(id);
		pageNo = PageUtil.initPageNo(pageNo);
		List<Reply> replyList=replyService.findReplyListByTopicId(id, PageSize,pageNo);
		Long totalNum =replyService.getReplyCountByTopicId(id);
		int totalPages = PageUtil.getTotalPages(totalNum, PageSize);
		
		model.addAttribute("pageNo",pageNo); 
		model.addAttribute("totalPages",totalPages); 
		model.addAttribute("topic",topic); 
		model.addAttribute("replyList",replyList); 
		model.addAttribute("flag","forum.html");  //此属性用来给前台确定当前是哪个页面
		return ValidatePcMobile.checkRequest(request, "/forum/topicDetail");
	}
	
	@RequestMapping("/forum/preSave")
	public String preSave(@AuthenticationPrincipal DbUser dbUser,Model model,HttpServletRequest request)throws Exception{
		if(dbUser != null){
			model.addAttribute("dbUser",dbUser);
		}else{
			return "redirect:/login";
		}
		
		List<Section> sectionList = sectionService.findSectionList(null, MaxPageSize, 0);
		
		model.addAttribute("sectionList",sectionList); 
		model.addAttribute("flag","forum.html");  //此属性用来给前台确定当前是哪个页面
		return ValidatePcMobile.checkRequest(request, "/forum/topicAdd");
	}
	
	@RequestMapping("/forum/topicUpdate")
	public String updateToipc(@AuthenticationPrincipal DbUser dbUser,
			@RequestParam(required = false) Integer pageNo,
			@RequestParam(required = false) Integer topicId,
			@RequestParam(required = false) Integer topicGood,
			@RequestParam(required = false) Integer topicTop,
			@RequestParam(required = false) Integer sectionId,
			RedirectAttributes model,HttpServletRequest request)throws Exception{
		if(dbUser != null){
			model.addFlashAttribute("dbUser", dbUser); 
		}
		
		Topic topic = new Topic();
		if(topicId!=null){
			topic = topicService.findTopicById(topicId);
		}
		if(topicGood != null){
			topic.setGood(topicGood);
		}
		if(topicTop != null){
			topic.setTop(topicTop);
		}
		topicService.saveTopic(topic);
		
		//重定向时传递参数
//		model.addFlashAttribute("sectionId", sectionId);  
//		model.addFlashAttribute("pageNo", pageNo);  
		return "redirect:/forum/topicList?sectionId="+sectionId+"&pageNo="+pageNo;
	}
	
	
	
	
}
