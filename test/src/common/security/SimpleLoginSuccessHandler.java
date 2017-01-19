package common.security;

import java.io.IOException;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import forum.po.DbUser;
import forum.service.DbUserService;

public class SimpleLoginSuccessHandler implements AuthenticationSuccessHandler,InitializingBean {
	
	protected Log logger = LogFactory.getLog(getClass());
	
	/**来自于配置文件 登录成功后的默认指向路径 */
	private String defaultTargetUrl;
	
	/**来自于配置文件 值为true */
	private boolean forwardToDestination = false;
	
	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
	
	@Resource(name="dbUserServiceImpl")
	private DbUserService dbUserService;
	
	/* (non-Javadoc)
	 * @see org.springframework.security.web.authentication.AuthenticationSuccessHandler#onAuthenticationSuccess(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.springframework.security.core.Authentication)
	 */
	@Override
	@Transactional
	public void onAuthenticationSuccess(HttpServletRequest request,
			HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		
		this.saveLoginInfo(request, authentication);
		
		//登录成功以后，从session中获取保存的登录前路径，作为登陆成功后的返回路径
		HttpSession session= request.getSession(false);
		String targetUrl = "";
		if(session != null && session.getAttribute("loginPath")!=null){
			targetUrl = (String)session.getAttribute("loginPath");
			if(!targetUrl.equals("/security_check")){
				session.removeAttribute("loginPath");
			}
		}else if(session != null && session.getAttribute("addTopicFlag")!=null){
			//如果是通过发帖时跳转到登陆页，则返回发帖页面
			targetUrl = "/forum/preSave";
		}

		//如果session中没有登录后的返回路径，那就使用默认路径
		if(targetUrl==null || "".equals(targetUrl)){
			targetUrl = this.defaultTargetUrl;
		}
		
		if (session != null && session.getAttribute("addTopicFlag")!=null) {
			session.removeAttribute("addTopicFlag");
		}
		
		if(this.forwardToDestination){
			logger.info("Login success,Forwarding to "+this.defaultTargetUrl);
			request.getRequestDispatcher(this.defaultTargetUrl).forward(request, response);
		}else{
			logger.info("Login success,Redirecting to "+targetUrl);
			this.redirectStrategy.sendRedirect(request, response, targetUrl);
		}
	}
	
	/**
	 * 更新当前用户在数据库中的ip和最后更新时间
	 * @param request
	 * @param authentication
	 */
	@Transactional(readOnly=false,propagation= Propagation.REQUIRED,rollbackFor={Exception.class})
	public void saveLoginInfo(HttpServletRequest request,Authentication authentication){
		String username =(authentication.getPrincipal() == null) ? "NONE_PROVIDED" : authentication.getName();
		DbUser user = dbUserService.getByName(username);
		if(user != null){
			HttpSession session = request.getSession();
			session.setAttribute("dbUser", user);
		}
		
		try {
			String ip = this.getIpAddress(request);
			Date date = new Date();
			user.setIpaddr(ip);
			user.setLasttime(date);
			dbUserService.merge(user);
		} catch (DataAccessException e) {
			if(logger.isWarnEnabled()){
				logger.info("无法更新用户登录信息至数据库");
			}
		}
	}
	
	/**
	 * 根据request获取ip地址
	 * @param request
	 * @return
	 */
	public String getIpAddress(HttpServletRequest request){  
	    String ip = request.getHeader("x-forwarded-for");  
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	        ip = request.getHeader("Proxy-Client-IP");  
	    }  
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	        ip = request.getHeader("WL-Proxy-Client-IP");  
	    }  
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	        ip = request.getHeader("HTTP_CLIENT_IP");  
	    }  
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	        ip = request.getHeader("HTTP_X_FORWARDED_FOR");  
	    }  
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	        ip = request.getRemoteAddr();  
	    }  
	    return ip;  
	}

	public void setDefaultTargetUrl(String defaultTargetUrl) {
		this.defaultTargetUrl = defaultTargetUrl;
	}

	public void setForwardToDestination(boolean forwardToDestination) {
		this.forwardToDestination = forwardToDestination;
	}

	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		if(StringUtils.isEmpty(defaultTargetUrl))
			throw new Exception("You must configure defaultTargetUrl");
	}  
	
}
