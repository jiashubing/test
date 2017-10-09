package forum.service;

import java.util.List;

import forum.po.Reply;
import forum.po.ReplyContent;

public interface ReplyService {

	Reply findLastReplyByTopicId(long topicId);
	
	ReplyContent findReplyContentByReplyId(long replyId);
	
	Long getReplyCountByTopicId(long topicId);
	
	List<Reply> findReplyListByTopicId(long topicId,  int pageSize,int pageNo);
	
	List<ReplyContent> getReplyContentListByTopicId(long topicId,  int pageSize,int pageNo);
	
	Reply saveReply(Reply reply);
	
	ReplyContent saveReplyContent(ReplyContent replyContent);
	
	void deleteReply(Reply reply);
	
	void deleteReplyById(long replyId);
	
	void deleteReplyByTopicId(long topicId);
	
	Reply findReplyById(long replyId);
}
