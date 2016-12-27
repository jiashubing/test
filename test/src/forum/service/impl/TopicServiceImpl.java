package forum.service.impl;

import java.util.List;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import forum.po.Topic;
import forum.service.ReplyService;
import forum.service.TopicService;
import forum.util.StringUtil;

@Service
@Transactional
public class TopicServiceImpl implements TopicService {
	
	@PersistenceContext 
	protected EntityManager em;	
	
	@Resource(name="replyServiceImpl")
	private ReplyService replyService;

	@Override
	public Topic saveTopic2(Topic topic) {
		topic = em.merge(topic);
		return topic;
	}
	
	@Override
	public void saveTopic(Topic topic) {
		em.merge(topic);
	}

	@Override
	public void deleteTopic(Topic topic) {
		replyService.deleteReplyByTopicId(topic.getId());
		Query query=em.createQuery("delete from Topic where id= "+topic.getId());
		query.executeUpdate();
	}
	
	@Override
	public void deleteTopicById(Integer topicId) {
		replyService.deleteReplyByTopicId(topicId);
		Query query=em.createQuery("delete from Topic where id= "+topicId);
		query.executeUpdate();
	}

	@Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
	@Override
	public List<Topic> findTopicList(Topic s_topic, int pageSize,int pageNo) {
		StringBuffer hql=new StringBuffer("from Topic t");
		if (s_topic!=null) {
			if (StringUtil.isNotEmpty(s_topic.getTitle())) {
				hql.append(" and t.title like '%"+s_topic.getTitle()+"%'");
			}
			if (s_topic.getUser()!=null) {
				if (StringUtil.isNotEmpty(s_topic.getUser().getNickName())) {
					hql.append(" and t.user.nickName like '%"+s_topic.getUser().getNickName()+"%'");
				}
			}
			if (StringUtil.isNotEmpty(s_topic.getContent())) {
				hql.append(" and t.content like '%"+s_topic.getContent()+"%'");
			}
			if (s_topic.getPublishTime()!=null) {
				hql.append(" and t.publishTime="+s_topic.getPublishTime());
			}
			if (s_topic.getModifyTime()!=null) {
				hql.append(" and t.modifyTime="+s_topic.getModifyTime());
			}
			if (s_topic.getSection()!=null) {
				if (s_topic.getSection().getId()>0) {
					hql.append(" and t.section.id="+s_topic.getSection().getId());
				}
			}
			if (s_topic.getTop()!=2) {
				hql.append(" and t.top="+s_topic.getTop());
			}
			if (s_topic.getGood()!=2) {
				hql.append(" and t.good="+s_topic.getGood());
			}
		}
		Query query = em.createQuery(hql.toString().replaceFirst("and", "where"));
//		query.setParameter(1, s_topic.getTitle());
		@SuppressWarnings("unchecked")
		List<Topic> result = query.setMaxResults(pageSize).setFirstResult(pageNo*pageSize).getResultList();
		em.clear();
		return result;
	}
	
	@Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
	@Override
	public List<Topic> findGoodTopicListDesc(int pageSize,int pageNo){
		StringBuffer hql=new StringBuffer("from Topic t where t.good=1 order by t.publishTime desc");
		Query query = em.createQuery(hql.toString());
		@SuppressWarnings("unchecked")
		List<Topic> result = query.setMaxResults(pageSize).setFirstResult(pageNo*pageSize).getResultList();
		em.clear();
		return result;
	}
	
	@Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
	@Override
	public List<Topic> findNewTopicListDesc(int pageSize,int pageNo){
		StringBuffer hql=new StringBuffer("from Topic t order by t.modifyTime desc");
		Query query = em.createQuery(hql.toString());
		@SuppressWarnings("unchecked")
		List<Topic> result = query.setMaxResults(pageSize).setFirstResult(pageNo*pageSize).getResultList();
		em.clear();
		return result;
	}
	

	@Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
	@Override
	public Long getTopicCount(Topic s_topic) {
		StringBuffer hql=new StringBuffer("select count(*) from Topic t");
		if (s_topic!=null) {
			if (StringUtil.isNotEmpty(s_topic.getTitle())) {
				hql.append(" and t.title like '%"+s_topic.getTitle()+"%'");
			}
			if (s_topic.getUser()!=null) {
				if (StringUtil.isNotEmpty(s_topic.getUser().getNickName())) {
					hql.append(" and t.user.nickName like '%"+s_topic.getUser().getNickName()+"%'");
				}
			}
			if (StringUtil.isNotEmpty(s_topic.getContent())) {
				hql.append(" and t.content like '%"+s_topic.getContent()+"%'");
			}
			if (s_topic.getPublishTime()!=null) {
				hql.append(" and t.publishTime="+s_topic.getPublishTime());
			}
			if (s_topic.getModifyTime()!=null) {
				hql.append(" and t.modifyTime="+s_topic.getModifyTime());
			}
			if (s_topic.getSection()!=null) {
				if (s_topic.getSection().getId()>0) {
					hql.append(" and t.section.id="+s_topic.getSection().getId());
				}
			}
			if (s_topic.getTop()!=2) {
				hql.append(" and t.top="+s_topic.getTop());
			}
			if (s_topic.getGood()!=2) {
				hql.append(" and t.good="+s_topic.getGood());
			}
		}
		Query query = em.createQuery(hql.toString().replaceFirst("and", "where"));
		return (Long)query.getSingleResult();
	}

	@Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
	@Override
	public Topic findTopicById(int topicId) {
		return em.find(Topic.class, topicId);
	}

	@Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
	@Override
	public List<Topic> findZdTopicListBySectionId(int sectionId,int pageSize,int pageNo) {
		StringBuffer hql=new StringBuffer("from Topic");
		if (sectionId>0) {
			hql.append(" and sectionId="+sectionId);
		}
		hql.append(" and top=1");
		Query query = em.createQuery(hql.toString().replaceFirst("and", "where"));
		@SuppressWarnings("unchecked")
		List<Topic> result = query.setMaxResults(pageSize).setFirstResult(pageNo*pageSize).getResultList();
		em.clear();
		return result;
	}

	@Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
	@Override
	public List<Topic> findPtTopicListBySectionId(int sectionId,int pageSize,int pageNo) {
		StringBuffer hql=new StringBuffer("from Topic");
		if (sectionId>0) {
			hql.append(" and sectionId="+sectionId);
		}
		hql.append(" and top=0 order by modifyTime desc");
		Query query = em.createQuery(hql.toString().replaceFirst("and", "where"));
		@SuppressWarnings("unchecked")
		List<Topic> result = query.setMaxResults(pageSize).setFirstResult(pageNo*pageSize).getResultList();
		em.clear();
		return result;
	}

	@Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
	@Override
	public Long getNoReplyTopicCount(Topic s_topic) {
		//StringBuffer hql=new StringBuffer("select count(*) from Topic t where t.id not in (select t.id from Reply r,Topic t where r.topic.id=t.id)");
		StringBuffer hql=new StringBuffer("select count(*) from Topic where id not in (select r.topic.id from Reply r)");
		if (s_topic!=null) {
			if (s_topic.getSection().getId()>0) {
				hql.append(" and sectionId="+s_topic.getSection().getId());
			}
		}
		Query query = em.createQuery(hql.toString());
		return (Long)query.getSingleResult();
	}

	@Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
	@Override
	public List<Topic> findGoodTopicListBySectionId(int sectionId,
			int pageSize,int pageNo) {
		StringBuffer hql=new StringBuffer("from Topic");
		if (sectionId>0) {
			hql.append(" and sectionId="+sectionId);
		}
		hql.append(" and good=1");
		Query query = em.createQuery(hql.toString().replaceFirst("and", "where"));
		@SuppressWarnings("unchecked")
		List<Topic> result = query.setMaxResults(pageSize).setFirstResult(pageNo*pageSize).getResultList();
		em.clear();
		return result;
	}

	@Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
	@Override
	public List<Topic> findNotGoodTopicListBySectionId(int sectionId,
			int pageSize,int pageNo) {
		StringBuffer hql=new StringBuffer("from Topic");
		if (sectionId>0) {
			hql.append(" and sectionId="+sectionId);
		}
		hql.append(" and good=0");
		Query query = em.createQuery(hql.toString().replaceFirst("and", "where"));
		@SuppressWarnings("unchecked")
		List<Topic> result = query.setMaxResults(pageSize).setFirstResult(pageNo*pageSize).getResultList();
		em.clear();
		return result;
	}

	@Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
	@Override
	public Long getPtTopicCountBySectionId(int sectionId) {
		StringBuffer hql=new StringBuffer("select count(*) from Topic");
		if (sectionId>0) {
			hql.append(" and sectionId="+sectionId);
		}
		hql.append(" and top=0");
		Query query = em.createQuery(hql.toString().replaceFirst("and", "where"));
		return (Long)query.getSingleResult();
	}
}
