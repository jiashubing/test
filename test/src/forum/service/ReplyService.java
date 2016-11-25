package forum.service;

import java.util.List;

import forum.po.PageBean;
import forum.po.Reply;

public interface ReplyService {

	public Reply findLastReplyByTopicId(int topicId);
	
	public Long getReplyCountByTopicId(int topicId);
	
//	public List<Reply> findReplyListByTopicId(int topicId, PageBean pageBean);
	public List<Reply> findReplyListByTopicId(int topicId,  int pageSize,int pageNo);
	
	public void saveReply(Reply reply);
	
	public void deleteReply(Reply reply);
	
	public Reply findReplyById(int replyId);
}
