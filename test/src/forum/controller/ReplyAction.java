package forum.controller;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import common.entity.Result;
import forum.po.DbUser;
import forum.po.Reply;
import forum.po.Topic;
import forum.service.ReplyService;
import forum.service.TopicService;
import forum.service.UserService;
import forum.util.FileEcodeUtil;

@Controller
public class ReplyAction {
	
	public static int PageSize = 10;
	public static int MaxPageSize = 100;

	@Resource(name="topicServiceImpl")
	private TopicService topicService;

	@Resource(name="replyServiceImpl")
	private ReplyService replyService;
	
	@Resource(name="userServiceImpl")
	private UserService userService;

	/**
	 * 论坛详情页面 异步 删除reply
	 */
	@RequestMapping("/forum/replyDelete")
	@ResponseBody
	public Result deleteReply(Integer replyId,HttpServletRequest request) {
		Result result = new Result();
		
		Reply reply = replyService.findReplyById(replyId);
		Topic topic = reply.getTopic();
		
		
		//删除数据之前，也要删除图片
		//提交的文件，先检查图片，将用到的图片移动到realImg文件夹下
		String[] url = reply.getContent().split("/");
        String imgName;
        int i = 0;
        for (i = 0 ; i <url.length ; i++ ) {
            if(url[i].contains(".png")){
                imgName = url[i].substring(0,40);
                String realOutPath=request.getSession().getServletContext().getRealPath("/WEB-INF/images/reaImg");
                String outName=realOutPath + '/' + imgName;
                //删除图片
                FileEcodeUtil.deleteFile(outName);
            }
        }
		
		
		replyService.deleteReplyById(replyId);
		
		topic.setModifyTime(new Date());
		Long tmp =replyService.getReplyCountByTopicId(topic.getId());
		if(tmp == null){
			topic.setReplySum(0);
		}else{
			topic.setReplySum(tmp);
		}
		topicService.saveTopic(topic);
		
		result.setStatus(1);
		return result;
	}
	
	@RequestMapping("/forum/replyAdd")
	public String addReply(
			@RequestParam(required = false) Integer pageNo,
			@RequestParam(required = false) Integer replyTopicId,
			@RequestParam(required = false) String replyContent,
			HttpServletRequest request
			)throws Exception{
		
		Reply reply = new Reply();
		
		reply.setPublishTime(new Date());
		
		
		//System.out.println("before : "+topicContent);		//首先检测上传内容中的图片
		//提交的文件，先检查图片，将用到的图片移动到realImg文件夹下
		String[] url = replyContent.split("/");
        String imgName;
        StringBuffer buf = new StringBuffer();
        int i = 0;
        for (i = 0 ; i <url.length ; i++ ) {
            if(url[i].contains(".png")){
                imgName = url[i].substring(0,40);
                //System.out.println("imgName = "+imgName);
                String realInPath=request.getSession().getServletContext().getRealPath("/WEB-INF/images/tmpImg");
                String realOutPath=request.getSession().getServletContext().getRealPath("/WEB-INF/images/reaImg");
                String inName=realInPath + '/' + imgName;
                String outName=realOutPath + '/' + imgName;
                boolean flag = FileEcodeUtil.fileRemove(inName, outName);
                //如果移动成功了，应该把内容中的路径修改，替换最后一个'tmpImg'为'reaImg'
                if(flag){
                	url[i-1]="reaImg";
                }
            }
            if(i>0){
            	buf.append(url[i-1]).append('/');
            }
        }
        if(i>0){
        	buf.append(url[i-1]);
        }
        //System.out.println("after : "+buf.toString());	//这就是实际应当报错到数据库里的内容
		
		if(replyTopicId!=null){
			Topic topic = topicService.findTopicById(replyTopicId);
			topic.setModifyTime(new Date());
			Long tmp =replyService.getReplyCountByTopicId(replyTopicId);
			if(tmp == null){
				topic.setReplySum(1);
			}else{
				topic.setReplySum(tmp+1);
			}
			topicService.saveTopic(topic);
			reply.setTopic(topic);
		}
		
		reply.setContent(buf.toString());
		
		replyService.saveReply(reply);
		
		//重定向时传递参数
		return "redirect:/forum/details?id="+replyTopicId+"&pageNo="+pageNo;
	}
	
	
	
	
}
