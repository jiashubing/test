package forum.service;

import java.util.List;

import forum.po.Reply;
import forum.po.ReplyContent;

public interface ReplyService {

	public Reply findLastReplyByTopicId(long topicId);
	
	public ReplyContent findReplyContentByReplyId(long replyId);
	
	public List<ReplyContent> findReplyContentListByTopicId(long topicId,  int pageSize,int pageNo);
	
	public Long getReplyCountByTopicId(long topicId);
	
	public List<Reply> findReplyListByTopicId(long topicId,  int pageSize,int pageNo);
	
	public List<ReplyContent> getReplyContentListByTopicId(long topicId,  int pageSize,int pageNo);
	
	public Reply saveReply(Reply reply);
	
	public ReplyContent saveReplyContent(ReplyContent replyContent);
	
	public void deleteReply(Reply reply);
	
	public void deleteReplyById(long replyId);
	
	public void deleteReplyByTopicId(long topicId);
	
	public Reply findReplyById(long replyId);
}
