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

import common.entity.Result;

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
	public String loadZoneList(@RequestParam(required = false) Integer pageNo,Model model,HttpServletRequest request)throws Exception{
		
		pageNo = PageUtil.initPageNo(pageNo);
		List<Zone> zoneList = zoneService.findZoneList(null, PageSize,pageNo);
		String mainPage="zone.html";
		
		long totalNum = zoneService.getZoneCount(null);
		int totalPages = PageUtil.getTotalPages(totalNum, PageSize);
		
		model.addAttribute("pageNo",pageNo); 
		model.addAttribute("mainPage",mainPage); 
		model.addAttribute("totalPages",totalPages); 
		model.addAttribute("zoneList",zoneList); 
		model.addAttribute("flag","forum.html");  //此属性用来给前台确定当前是哪个页面
		return ValidatePcMobile.checkRequest(request, "/admin/main");
	}
	
	@RequestMapping("/admin/zoneAdd")
	public String saveZone(@RequestParam(required = false) Integer pageNo,@RequestParam Integer zid,
			@RequestParam String zname,@RequestParam String zdescription,Model model,HttpServletRequest request)throws Exception{
		Zone zone =new Zone();
		if(zid != null){
			zone.setId(zid);
		}
		zone.setName(zname);
		zone.setDescription(zdescription);
		zoneService.saveZone(zone);
		return "redirect:/admin/zoneList";
	}
	
	@RequestMapping("/admin/zoneDelete")
    @ResponseBody
    public Result loadLottery(Integer id) {
        Result result = new Result();
        zoneService.deleteZoneById(id);
        result.setStatus(1);
        return result;
    }
	
}
