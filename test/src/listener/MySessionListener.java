package listener;

//import java.util.Date;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class MySessionListener implements HttpSessionListener {

	@Override
	public void sessionCreated(HttpSessionEvent arg0) {
//		System.out.println("sessionCreated" + "," + new Date());
		Object lineCount = arg0.getSession().getServletContext().getAttribute("lineCount");
		Integer count;
		if (lineCount == null) {
			lineCount = "0";
		}
		count = Integer.valueOf(lineCount.toString());
		count++;
//		System.out.println("新上线一人，历史在线人数：" + lineCount + "个,当前在线人数有： " + count + " 个");
		arg0.getSession().getServletContext().setAttribute("lineCount", count);
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent arg0) {
//		System.out.println("sessionDestroyed" + "," + new Date());
		Object lineCount = arg0.getSession().getServletContext().getAttribute("lineCount");
		Integer count = Integer.valueOf(lineCount.toString());
		count--;
//		System.out.println("一人下线，历史在线人数：" + lineCount + "个，当前在线人数: " + count + " 个");
		arg0.getSession().getServletContext().setAttribute("lineCount", count);
	}

}
