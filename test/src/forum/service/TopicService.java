package forum.service;

import java.util.List;

import forum.po.Topic;

public interface TopicService {

	public void saveTopic(Topic topic);
	
	public void deleteTopic(Topic topic);
	
//	public List<Topic> findTopicList(Topic s_topic,PageBean pageBean);
	public List<Topic> findTopicList(Topic s_topic, int pageSize,int pageNo);
	
	public Long getTopicCount(Topic s_topic);
	
	public Topic findTopicById(int topicId);	
	
//	public List<Topic> findZdTopicListBySectionId(int sectionId,PageBean pageBean);
	public List<Topic> findZdTopicListBySectionId(int sectionId, int pageSize,int pageNo);
	
//	public List<Topic> findPtTopicListBySectionId(int sectionId,PageBean pageBean);
	public List<Topic> findPtTopicListBySectionId(int sectionId, int pageSize,int pageNo);
	
	public Long getPtTopicCountBySectionId(int sectionId);
	
//	public List<Topic> findGoodTopicListBySectionId(int sectionId,PageBean pageBean);
	public List<Topic> findGoodTopicListBySectionId(int sectionId, int pageSize,int pageNo);
	
//	public List<Topic> findNotGoodTopicListBySectionId(int sectionId,PageBean pageBean);
	public List<Topic> findNotGoodTopicListBySectionId(int sectionId, int pageSize,int pageNo);
	
	public Long getNoReplyTopicCount(Topic s_topic);
}
