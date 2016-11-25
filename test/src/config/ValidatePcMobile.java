package config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class ValidatePcMobile {
	public static String checkRequest(HttpServletRequest request,String returnPath){
		HttpSession session = request.getSession();
		//检查是否已经记录访问方式（移动端或pc端）
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
			}
		} else {
			isFromMobile = session.getAttribute("ua").equals("mobile");
		}
		if (isFromMobile) {
			return "/mobile" +returnPath;
		}else{
			return "/pc" +returnPath;
		}
	}
}
