package common.controller;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import config.ValidatePcMobile;
import forum.po.DbUser;
import forum.service.DbUserService;

@Controller
public class ToolsAction {
	
	@Resource(name="dbUserServiceImpl")
	private DbUserService dbUserService;
	
	@RequestMapping("/tools")
	public String loadTools(@AuthenticationPrincipal DbUser dbUser,@RequestParam(required=false)Integer showId,Model model,HttpServletRequest request)throws Exception{
		if(dbUser != null){
			model.addAttribute("dbUser",dbUser);
		}
		if(showId == null){
			showId = 1;
		}
		model.addAttribute("showId",showId); 
		model.addAttribute("flag","tools.html");  //此属性用来给前台确定当前是哪个页面
		return ValidatePcMobile.checkRequest(request, "/tools");
	}
	
	@RequestMapping("/tools/one")
	public String loadToolOne(@AuthenticationPrincipal DbUser dbUser,@RequestParam(required=false)Integer showId,Model model,HttpServletRequest request)throws Exception{
		if(dbUser != null){
			model.addAttribute("dbUser",dbUser);
		}
		if(showId == null){
			showId = 1;
		}
		model.addAttribute("showId",showId); 
		model.addAttribute("flag","tools.html");  //此属性用来给前台确定当前是哪个页面
		return ValidatePcMobile.checkRequest(request, "/tools");
	}
	
	@RequestMapping("/tools/two")
	public String loadToolTwo(@AuthenticationPrincipal DbUser dbUser,@RequestParam(required=false)Integer showId,Model model,HttpServletRequest request)throws Exception{
		if(dbUser != null){
			model.addAttribute("dbUser",dbUser);
		}
		if(showId == null){
			showId = 2;
		}
		model.addAttribute("showId",showId); 
		model.addAttribute("flag","tools.html");  //此属性用来给前台确定当前是哪个页面
		return ValidatePcMobile.checkRequest(request, "/tools");
	}
	
	
	
}
