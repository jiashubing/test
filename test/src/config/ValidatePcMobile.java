package config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class ValidatePcMobile {
	/**
	 * true PC端  false手机端
	 */
	public static boolean checkRequest(HttpServletRequest request){
		HttpSession session = request.getSession();
		//检查是否已经记录访问方式（移动端或pc端）
		boolean isFromMobile = isFromMobile(request, session);
		return !isFromMobile;
	}

	private static boolean isFromMobile(HttpServletRequest request, HttpSession session) {
		boolean isFromMobile = false;
		if (null == session.getAttribute("ua")) {
			try {
				//获取ua，用来判断是否为移动端访问
				String userAgent = request.getHeader("USER-AGENT").toLowerCase();
				if (null == userAgent) {
					userAgent = "";
				}
				isFromMobile = CheckMobile.check(userAgent);
				//判断是否为移动端访问
				if (isFromMobile) {
					session.setAttribute("ua", "mobile");
				} else {
					session.setAttribute("ua", "pc");
				}
			} catch (Exception e) {
				//do nothing
			}
		} else {
			isFromMobile = session.getAttribute("ua").equals("mobile");
		}
		return isFromMobile;
	}

	public static String checkRequest(HttpServletRequest request,String returnPath){
		HttpSession session = request.getSession();
		//检查是否已经记录访问方式（移动端或pc端）
		boolean isFromMobile = isFromMobile(request, session);
		if (isFromMobile) {
			return "/mobile" +returnPath;
		}else{
			return "/pc" +returnPath;
		}
	}
	
	/**
	 * 返回上一页
	 * http://www.jiashuibing/forum/detail 返回http://www.jiashuibing/forum  
	 * http://www.jiashuibing/login 返回http://www.jiashuibing
	 */
	public static String getDefaultPrePage(HttpServletRequest request){
		String ans = "http://"+request.getHeader("Host")+request.getRequestURI();
		int x = ans.lastIndexOf('/');
		ans = ans.substring(0, x);
		return ans;
	}
}
