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

import config.ValidatePcMobile;
import forum.po.DbUser;
import forum.po.Reply;
import forum.po.Section;
import forum.po.Topic;
import forum.po.Zone;
import forum.service.ReplyService;
import forum.service.SectionService;
import forum.service.TopicService;
import forum.service.ZoneService;
import forum.util.PageUtil;

@Controller
public class ZoneAction {
	
	public static int PageSize = 6;

	@Resource(name="zoneServiceImpl")
	private ZoneService zoneService;

	@Resource(name="topicServiceImpl")
	private TopicService topicService;

	@Resource(name="sectionServiceImpl")
	private SectionService sectionService;
	
	@Resource(name="replyServiceImpl")
	private ReplyService replyService;
	
	@RequestMapping("/admin/zoneList")
	public String loadZoneList(@AuthenticationPrincipal DbUser dbUser,@RequestParam(required = false) Integer pageNo,Model model,HttpServletRequest request)throws Exception{
		if(dbUser != null){
			model.addAttribute("dbUser",dbUser);
		}
		
		pageNo = PageUtil.initPageNo(pageNo);
//		List<Zone> zoneList = zoneService.findZoneList(s_zone, PageSize,pageNo);
//		PageBean pageBean=new PageBean(Integer.parseInt(page), 6);
//		zoneList=zoneService.findZoneList(s_zone, pageBean);
//		total=zoneService.getZoneCount(s_zone);
//		pageCode=PageUtil.genPagination(request.getContextPath()+"/admin/Zone_list.action", total, Integer.parseInt(page), 6,null);
		String mainPage="zone.html";
//		crumb1="�������";
		
		model.addAttribute("pageNo",pageNo); 
		model.addAttribute("mainPage",mainPage); 
//		model.addAttribute("topic",topic); 
//		model.addAttribute("zoneList",zoneList); 
		model.addAttribute("flag","forum.html");  //此属性用来给前台确定当前是哪个页面
		return ValidatePcMobile.checkRequest(request, "/admin/main");
	}
	
}
