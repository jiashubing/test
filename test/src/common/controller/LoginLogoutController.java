package common.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import common.entity.Result;

import config.ValidatePcMobile;

@Controller
public class LoginLogoutController {

	/**
	 * 指向登录界面
	 */
	@RequestMapping(value = "/login")
	public String getLoginPage(@RequestParam(required = false) boolean error,@RequestParam( required = false)String beforepath,
			@RequestParam( required = false)String beforepar, ModelMap model,HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (error) {
			model.put("errInfo", "用户名或密码错误!");
		} else {
			model.put("errInfo", "");
		}
		model.put("loginFlag", 1);
		if(ValidatePcMobile.checkRequest(request)){
			return "redirect:/happy";
		}else{
			if(session!=null && session.getAttribute("dbUser") != null){
				return "/mobile/person";
			}
			//拼接登录前的路径
			String loginPath ="";
			if(beforepath != null && !"/login".equals(beforepath)){
				loginPath += beforepath;
				if(beforepar != null && !"".equals(beforepar)){
					loginPath +='?'+beforepar;
				}
			}
			//返回上一页的路径，赋值到页面中
			String prePage = request.getHeader("Referer");
			if(prePage != null && !"".equals(prePage) && !prePage.contains("/do") && !prePage.contains("/regist")){
				model.addAttribute("prePage", prePage);
			}else{
				String tmpPage = ValidatePcMobile.getDefaultPrePage(request);
				model.addAttribute("prePage", tmpPage);
			}
			//保存到session
			if(!"".equals(loginPath) && !loginPath.startsWith("/do") && session != null){
				session.setAttribute("loginPath", loginPath);
			}
			return "/mobile/login";
		}
	}
	
	/**
	 * 手机端指向登录前界面
	 */
	@RequestMapping(value = "/beforelogin")
	public String getBeforeLoginPage(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if(session!=null && session.getAttribute("dbUser") != null){
			return "/mobile/person";
		}
		return "/mobile/beforelogin";
	}

	/**
	 * 指定无权限访问界面
	 */
	@RequestMapping(value = "/denied")
	public String getDeniedPage(HttpServletRequest request) {
		return ValidatePcMobile.checkRequest(request, "/deniedpage");
	}
	
	/**
	 * 退出登录前，将退出后的跳转页面保存到session中
	 */
	@RequestMapping(value = "/mylogout")
	public String getLogoutReturnPage(HttpServletRequest request,@RequestParam( required = false) String beforepath,
			@RequestParam( required = false) String beforepar) {
		//拼接退出前的路径
		String logoutPath ="";
		if(beforepath != null){
			logoutPath += beforepath;
			if(beforepar != null && !"".equals(beforepar)){
				logoutPath +='?'+beforepar;
			}
		}
		//保存到session
		HttpSession session= request.getSession(false);
		if(!"".equals(logoutPath) &&!"/login".equals(logoutPath) && !logoutPath.startsWith("/do") && session != null){
			session.setAttribute("logoutPath", logoutPath);
		}
		return "redirect:/logout";
	}
	
	/**
	 * 登录前，将跳转页面保存到session中
	*/
	@RequestMapping(value={"/mylogin","/tools/mylogin","/forum/mylogin"})
    @ResponseBody
    public Result getLoginReturnPage(String beforepath,String beforepar,HttpServletRequest request) {
		//拼接登录前的路径
		String loginPath ="";
		if(beforepath != null && !"/login".equals(beforepath)){
			loginPath += beforepath;
			if(beforepar != null && !"".equals(beforepar)){
				loginPath +='?'+beforepar;
			}
		}
		//保存到session
		HttpSession session= request.getSession(false);
		if(!"".equals(loginPath) &&!"/login".equals(loginPath) && !loginPath.startsWith("/do") && session != null){
			session.setAttribute("loginPath", loginPath);
		}
		Result result = new Result();
       	 	result.setStatus(1);
        	return result;
    }
	
//	登录前，将跳转页面保存到session中
	/*
	@RequestMapping("/tools/mylogin")
	@ResponseBody
	public Result getLoginToolsReturnPage(String beforepath,String beforepar,HttpServletRequest request) {
		//拼接登录前的路径
		String loginPath ="";
		if(beforepath != null && !"/login".equals(beforepath)){
			loginPath += beforepath;
			if(beforepar != null && !"".equals(beforepar)){
				loginPath +='?'+beforepar;
			}
		}
		//保存到session
		HttpSession session= request.getSession(false);
		if(!"".equals(loginPath) && !loginPath.startsWith("/do") && session != null){
			session.setAttribute("loginPath", loginPath);
		}
		Result result = new Result();
		result.setStatus(1);
		return result;
	}*/
	

	//登录前，将跳转页面保存到session中
	/*
	@RequestMapping("/forum/mylogin")
	@ResponseBody
	public Result getLoginForumReturnPage(String beforepath,String beforepar,HttpServletRequest request) {
		//拼接登录前的路径
		String loginPath ="";
		if(beforepath != null && !"/login".equals(beforepath)){
			loginPath += beforepath;
			if(beforepar != null && !"".equals(beforepar)){
				loginPath +='?'+beforepar;
			}
		}
		//保存到session
		HttpSession session= request.getSession(false);
		if(!"".equals(loginPath) && !loginPath.startsWith("/do") && session != null){
			session.setAttribute("loginPath", loginPath);
		}
		Result result = new Result();
		result.setStatus(1);
		return result;
	}*/

}
