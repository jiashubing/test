package forum.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import config.ValidatePcMobile;
import forum.po.DbUser;
import forum.po.Zone;
import forum.service.ZoneService;

@Controller
public class InitAction {
	
	@Resource(name="zoneServiceImpl")
	private ZoneService zoneService;
	
	@RequestMapping("/forum")
	public String loadIndex(@AuthenticationPrincipal DbUser dbUser,Model model,HttpServletRequest request)throws Exception{
		if(dbUser != null){
			model.addAttribute("dbUser",dbUser);
		}
		List<Zone> zoneList=zoneService.findZoneList(null, 10,0);
		model.addAttribute("zoneList",zoneList);
		
		model.addAttribute("flag","forum.html");  //此属性用来给前台确定当前是哪个页面
		return ValidatePcMobile.checkRequest(request, "/forum");
	}
	
}
