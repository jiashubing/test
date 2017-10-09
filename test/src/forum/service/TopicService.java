package forum.service;

import java.util.List;

import forum.po.Topic;
import forum.po.TopicContent;

public interface TopicService {

	void saveTopic(Topic topic);
	Topic saveTopic2(Topic topic);
	TopicContent saveTopicContent(TopicContent topicContent);
	
	void deleteTopic(Topic topic);
	void deleteTopicById(long topicId);
	
	List<Topic> findTopicList(Topic s_topic, int pageSize,int pageNo);
	
	Long getTopicCount(Topic s_topic);
	
	Topic findTopicById(long topicId);
	
	TopicContent findTopicContentByTopicId(long topicId);
	
	/**
	 * 搜索置顶的帖子
	 */
	public List<Topic> findZdTopicListBySectionId(long sectionId, int pageSize,int pageNo);
	
	/**
	 * 搜索精华的帖子
	 */
	public List<Topic> findGoodTopicListDesc(int pageSize,int pageNo);
	
	/**
	 * 搜索最新的帖子
	 */
	public List<Topic> findNewTopicListDesc(int pageSize,int pageNo);
	
	/**
	 * 搜索普通的帖子
	 */
	public List<Topic> findPtTopicListBySectionId(long sectionId, int pageSize,int pageNo);
	
	public Long getPtTopicCountBySectionId(long sectionId);
	
	public List<Topic> findGoodTopicListBySectionId(long sectionId, int pageSize,int pageNo);
	
	public List<Topic> findNotGoodTopicListBySectionId(long sectionId, int pageSize,int pageNo);
	
	/**
	 * 搜索未回复帖子
	 */
	public Long getNoReplyTopicCount(long sectionId);
	
	/**
	 * 搜索精华帖子数
	 */
	public Long getGoodTopicCount(long sectionId);
	
	/**
	 * 搜索帖子总数
	 */
	public Long getTotalTopicCount(long sectionId);
	
	/**
	 * 搜索帖子总数
	 */
	public Long getAllTopicCount();
	
	/**
	 *获取帖子内容
	 */
	public TopicContent getTopicContent(long topicId);
}
