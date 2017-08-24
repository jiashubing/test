package forum.service;

import java.util.List;

import forum.po.Topic;
import forum.po.TopicContent;

public interface TopicService {

	public void saveTopic(Topic topic);
	public Topic saveTopic2(Topic topic);
	public TopicContent saveTopicContent(TopicContent topicContent);
	
	public void deleteTopic(Topic topic);
	public void deleteTopicById(long topicId);
	
	public List<Topic> findTopicList(Topic s_topic, int pageSize,int pageNo);
	
	public Long getTopicCount(Topic s_topic);
	
	public Topic findTopicById(long topicId);	
	
	public TopicContent findTopicContentByTopicId(long topicId);	
	
	/**
	 * 搜索置顶的帖子
	 * @param sectionId
	 * @param pageSize
	 * @param pageNo
	 * @return
	 */
	public List<Topic> findZdTopicListBySectionId(long sectionId, int pageSize,int pageNo);
	
	/**
	 * 搜索精华的帖子
	 * @param sectionId
	 * @param pageSize
	 * @param pageNo
	 * @return
	 */
	public List<Topic> findGoodTopicListDesc(int pageSize,int pageNo);
	
	/**
	 * 搜索最新的帖子
	 * @param sectionId
	 * @param pageSize
	 * @param pageNo
	 * @return
	 */
	public List<Topic> findNewTopicListDesc(int pageSize,int pageNo);
	
	/**
	 * 搜索普通的帖子
	 * @param sectionId
	 * @param pageSize
	 * @param pageNo
	 * @return
	 */
	public List<Topic> findPtTopicListBySectionId(long sectionId, int pageSize,int pageNo);
	
	public Long getPtTopicCountBySectionId(long sectionId);
	
	public List<Topic> findGoodTopicListBySectionId(long sectionId, int pageSize,int pageNo);
	
	public List<Topic> findNotGoodTopicListBySectionId(long sectionId, int pageSize,int pageNo);
	
	/**
	 * 搜索未回复帖子
	 * @param sectionId
	 * @return
	 */
	public Long getNoReplyTopicCount(long sectionId);
	
	/**
	 * 搜索精华帖子数
	 * @param sectionId
	 * @return
	 */
	public Long getGoodTopicCount(long sectionId);
	
	/**
	 * 搜索帖子总数
	 * @param sectionId
	 * @return
	 */
	public Long getTotalTopicCount(long sectionId);
	
	/**
	 * 搜索帖子总数
	 * @return
	 */
	public Long getAllTopicCount();
	
	/**
	 *获取帖子内容
	 * @return
	 */
	public TopicContent getTopicContent(long topicId);
}
