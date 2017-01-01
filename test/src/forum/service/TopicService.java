package forum.service;

import java.util.List;

import forum.po.Topic;

public interface TopicService {

	public void saveTopic(Topic topic);
	public Topic saveTopic2(Topic topic);
	
	public void deleteTopic(Topic topic);
	public void deleteTopicById(Integer topicId);
	
	public List<Topic> findTopicList(Topic s_topic, int pageSize,int pageNo);
	
	public Long getTopicCount(Topic s_topic);
	
	public Topic findTopicById(int topicId);	
	
	/**
	 * 搜索置顶的帖子
	 * @param sectionId
	 * @param pageSize
	 * @param pageNo
	 * @return
	 */
	public List<Topic> findZdTopicListBySectionId(int sectionId, int pageSize,int pageNo);
	
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
	public List<Topic> findPtTopicListBySectionId(int sectionId, int pageSize,int pageNo);
	
	public Long getPtTopicCountBySectionId(int sectionId);
	
	public List<Topic> findGoodTopicListBySectionId(int sectionId, int pageSize,int pageNo);
	
	public List<Topic> findNotGoodTopicListBySectionId(int sectionId, int pageSize,int pageNo);
	
	/**
	 * 搜索未回复帖子
	 * @param sectionId
	 * @return
	 */
	public Long getNoReplyTopicCount(int sectionId);
	
	/**
	 * 搜索精华帖子数
	 * @param sectionId
	 * @return
	 */
	public Long getGoodTopicCount(int sectionId);
	
	/**
	 * 搜索帖子总数
	 * @param sectionId
	 * @return
	 */
	public Long getTotalTopicCount(int sectionId);
}
