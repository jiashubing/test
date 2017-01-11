package common.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import config.ValidatePcMobile;
import forum.po.DbUser;

@Controller
public class LoginLogoutController {

	/**
	 * 指向登录界面
	 * 
	 * @param error
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/login")
	public String getLoginPage(@RequestParam(value = "error", required = false) boolean error, ModelMap model,HttpServletRequest request) {
		HttpSession session = request.getSession();
		if (error == true) {
			// Assign an error message
			model.put("errInfo", "用户名或密码错误!");
		} else {
			model.put("errInfo", "");
		}
		model.put("loginFlag", 1);
		if(ValidatePcMobile.checkRequest(request)){
			return "/pc/index";
		}else{
			if(session.getAttribute("dbUser") != null){
				return "/mobile/person";
			}
			return "/mobile/login";
		}
	}
	
	/**
	 * 手机端指向登录前界面
	 * 
	 * @param error
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/beforelogin")
	public String getBeforeLoginPage( ModelMap model,HttpServletRequest request) {
		HttpSession session = request.getSession();
		if(session.getAttribute("dbUser") != null){
			return "/mobile/person";
		}
		return "/mobile/beforelogin";
	}

	/**
	 * 指定无权限访问界面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/denied")
	public String getDeniedPage(HttpServletRequest request) {
		return ValidatePcMobile.checkRequest(request, "/deniedpage");
	}

}
