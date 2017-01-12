package common.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.util.Assert;

public class SimpleLogoutSuccessHandler implements LogoutSuccessHandler{
	protected final Log logger = LogFactory.getLog(getClass());
	private String defaultTargetUrl = "/";	//默认是/，实际上会通过getset方法从xml中获取
	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException{
		//因为在登出成功以前会默认注销session，所以应当配置 invalidate-session="false"，然后在这里再注销session
		HttpSession session= request.getSession(false);
		String targetUrl = "";
		if(session != null && session.getAttribute("logoutPath")!=null){
			targetUrl = (String)session.getAttribute("logoutPath");
		}

		//如果session中没有退出后的返回路径，那就使用默认路径
		if(targetUrl==null || "".equals(targetUrl)){
			targetUrl = defaultTargetUrl;
		}

		//这里的写法 同jar包中 AbstractAuthenticationTargetUrlRequestHandler.classs
		if (response.isCommitted()) {
			this.logger.debug("Response has already been committed. Unable to redirect to " + targetUrl);
			return;
		}

		//注销session
		if (session != null) {
			this.logger.debug("Invalidating session: " + session.getId());
			session.invalidate();
		}

		this.redirectStrategy.sendRedirect(request, response, targetUrl);

	}

	protected final String getDefaultTargetUrl(){
		return this.defaultTargetUrl;
	}

	public void setDefaultTargetUrl(String defaultTargetUrl){
		Assert.isTrue(UrlUtils.isValidRedirectUrl(defaultTargetUrl), "defaultTarget must start with '/' or with 'http(s)'");
		this.defaultTargetUrl = defaultTargetUrl;
	}
}