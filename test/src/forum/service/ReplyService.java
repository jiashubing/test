package forum.service;

import java.util.List;

import forum.po.Reply;
import forum.po.ReplyContent;

public interface ReplyService {

	public Reply findLastReplyByTopicId(int topicId);
	
	public ReplyContent findReplyContentByReplyId(int replyId);
	
	public List<ReplyContent> findReplyContentListByTopicId(int topicId,  int pageSize,int pageNo);
	
	public Long getReplyCountByTopicId(int topicId);
	
	public List<Reply> findReplyListByTopicId(int topicId,  int pageSize,int pageNo);
	
	public List<ReplyContent> getReplyContentListByTopicId(int topicId,  int pageSize,int pageNo);
	
	public Reply saveReply(Reply reply);
	
	public ReplyContent saveReplyContent(ReplyContent replyContent);
	
	public void deleteReply(Reply reply);
	
	public void deleteReplyById(Integer replyId);
	
	public void deleteReplyByTopicId(Integer topicId);
	
	public Reply findReplyById(int replyId);
}
