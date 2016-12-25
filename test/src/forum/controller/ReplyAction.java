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
	public String addReply(@AuthenticationPrincipal DbUser dbUser,
			@RequestParam(required = false) Integer pageNo,
			@RequestParam(required = false) Integer replyTopicId,
			@RequestParam(required = false) String replyContent
			)throws Exception{
		
		Reply reply = new Reply();
		
		reply.setPublishTime(new Date());
		
		if(replyTopicId!=null){
			Topic topic = topicService.findTopicById(replyTopicId);
			topic.setModifyTime(new Date());
			Long tmp =replyService.getReplyCountByTopicId(replyTopicId);
			if(tmp == null){
				topic.setReplySum(0);
			}else{
				topic.setReplySum(tmp);
			}
			topicService.saveTopic(topic);
			reply.setTopic(topic);
		}
		
		if(dbUser != null){
			reply.setUser(dbUser.getUser());
		}
		reply.setContent(replyContent);
		
		replyService.saveReply(reply);
		
		//重定向时传递参数
		return "redirect:/forum/details?id="+replyTopicId+"&pageNo="+pageNo;
	}
	
	
	
	
}
