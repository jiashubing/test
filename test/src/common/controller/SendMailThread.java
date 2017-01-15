package common.controller;

import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.ApplicationContext;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import common.entity.EmailVo;

public class SendMailThread extends Thread {  
    private String nickName;
    private String email;
    private ApplicationContext ctx;
    public SendMailThread(String nickName,String email){
    	ctx= new ClassPathXmlApplicationContext("resources/spring-mail.xml");
    	this.nickName = nickName;
    	this.email = email;
    }
    public void run() {  
    	
    	EmailVo emailVo = new EmailVo();
		emailVo.setReceivers(new String[]{email});
		emailVo.setCc(new String[]{});
		emailVo.setBcc(new String[]{});
		emailVo.setSubject("感谢您的注册");
		emailVo.setSender("jiashubingcn@aliyun.com");
		java.text.DateFormat format1 = new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	    String tmpDate = format1.format(new Date());
		emailVo.setEmailContent("<html><body><p><strong>亲爱的&nbsp;<span style='color:red;'>"+nickName+"</span>：</strong></p><p style='text-indent:2em;'>感谢您注册我的网站（http://www.jiashubing.cn），非常欢迎您能来这里发表帖子和评论，使用在线小工具等功能。网站将会持续更新，感谢您的关注。</p><p style='text-indent:2em;'>如果您没有注册，请忽略此邮件。	 如有打扰之处，万分抱歉。</p><p style='text-indent:2em;'>----------------------------------------------</p><p style='text-indent:2em;'>"+tmpDate+"</p><p style='text-indent:2em;'>（本邮件由系统自动发出，请勿回复。）</p><p>来自贾树丙的个人网站</p></body></html>");

		try {
			JavaMailSender emailTemplate = (JavaMailSender) ctx.getBean("emailTemplate");
	    	
			MimeMessage message = emailTemplate.createMimeMessage();
	        MimeMessageHelper helper = new MimeMessageHelper(message);

	        helper.setFrom(emailVo.getSender());
	        helper.setValidateAddresses(true);
	        if(emailVo.getEmailContent().trim()!="")
	        {
	            helper.setText(emailVo.getEmailContent(), true);
	        }
	        helper.setSubject(emailVo.getSubject());
	        helper.setCc(emailVo.getCc());
	        helper.setTo(emailVo.getReceivers());
	        helper.setBcc(emailVo.getBcc());
	        helper.setSentDate(new Date());

	        emailTemplate.send(message);
		} catch (MessagingException e) {
			System.out.println("发送邮件失败");
		}
    	
    }
}