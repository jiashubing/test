package common.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import config.ValidatePcMobile;

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
		if (error == true) {
			// Assign an error message
			model.put("error", "用户名或密码错误!");
		} else {
			model.put("error", "");
		}
		model.put("loginFlag", 1);
		return ValidatePcMobile.checkRequest(request, "/index");
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
