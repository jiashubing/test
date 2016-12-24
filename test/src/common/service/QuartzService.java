package common.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import forum.util.FileEcodeUtil;
import forum.util.ImgUtil;

@Service  
@Lazy(false) 
public class QuartzService {

//	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");  
	
	 @Value("#{configProperties['http.windows']}")  
	 private String windowsUrl;   
	 
	 @Value("#{configProperties['http.linux']}")  
	 private String linuxUrl;   
	
//	private static final String toolsPath = (((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest())
//			.getSession().getServletContext().getRealPath(ImgUtil.TOOLS_PATH+ImgUtil.TOOLS_TXT)+"/";     

//	@Scheduled(cron = "0 */01 * * * *")//每十分钟执行一次  
//	public void reportCurrentTime() {  
//		System.out.println("The time is now " + dateFormat.format(new Date()));  
//	}  
//	  
//	@Scheduled(cron = "0-59 * * * * *")//每秒执行一次  
//	public void reportCurrentTime2() {  
//		System.out.println("****************** " + dateFormat.format(new Date()));  
//	}  
	
	@Scheduled(cron = "0 10 2 * * ?")//每天2：10触发
	public void reportCurrentTime3() {  
//		System.out.println("************toolsPath" + toolsPath);
//		String inPath2 = (((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest()).getSession().getServletContext().getRealPath(ImgUtil.TOOLS_PATH+ImgUtil.TOOLS_TXT)+"/";    
//		String inPath = request.getSession().getServletContext().getRealPath(ImgUtil.TOOLS_PATH+ImgUtil.TOOLS_TXT)+"/";
		String inPath = linuxUrl+ImgUtil.TOOLS_PATH;
//		System.out.println(windowsUrl);  
//		System.out.println(inPath);  
//		System.out.println("inPath = "+inPath);
		FileEcodeUtil.deleteDirectoryChildren(inPath);
//		System.out.println("*******************############ " + dateFormat.format(new Date()));  
	}  

}
