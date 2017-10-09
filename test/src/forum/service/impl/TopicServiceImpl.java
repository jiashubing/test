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
import forum.po.TopicContent;
import forum.service.ReplyService;
import forum.service.TopicService;
import forum.util.StringUtil;

@Service
@Transactional
public class TopicServiceImpl implements TopicService {

    @PersistenceContext
    protected EntityManager em;

    @Resource(name = "replyServiceImpl")
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
        Query query = em.createQuery("delete from TopicContent where topicId= " + topic.getId());
        query.executeUpdate();
        query = em.createQuery("delete from Topic where id= " + topic.getId());
        query.executeUpdate();
    }

    @Override
    public void deleteTopicById(long topicId) {
        replyService.deleteReplyByTopicId(topicId);
        Query query = em.createQuery("delete from TopicContent where topicId= " + topicId);
        query.executeUpdate();
        query = em.createQuery("delete from Topic where id= " + topicId);
        query.executeUpdate();
    }

    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    @Override
    public List<Topic> findTopicList(Topic s_topic, int pageSize, int pageNo) {
        StringBuffer hql = new StringBuffer("from Topic t");
        addQueryCondition(s_topic, hql);
        Query query = em.createQuery(hql.toString().replaceFirst("and", "where"));
        @SuppressWarnings("unchecked")
        List<Topic> result = query.setMaxResults(pageSize).setFirstResult(pageNo * pageSize).getResultList();
        em.clear();
        return result;
    }

    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    @Override
    public List<Topic> findGoodTopicListDesc(int pageSize, int pageNo) {
        String hql = "from Topic t where t.good=1 order by t.publishTime desc";
        Query query = em.createQuery(hql);
        @SuppressWarnings("unchecked")
        List<Topic> result = query.setMaxResults(pageSize).setFirstResult(pageNo * pageSize).getResultList();
        em.clear();
        return result;
    }

    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    @Override
    public List<Topic> findNewTopicListDesc(int pageSize, int pageNo) {
        String hql = "from Topic t order by t.modifyTime desc";
        Query query = em.createQuery(hql);
        @SuppressWarnings("unchecked")
        List<Topic> result = query.setMaxResults(pageSize).setFirstResult(pageNo * pageSize).getResultList();
        em.clear();
        return result;
    }


    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    @Override
    public Long getTopicCount(Topic s_topic) {
        StringBuffer hql = new StringBuffer("select count(*) from Topic t");
        addQueryCondition(s_topic, hql);
        Query query = em.createQuery(hql.toString().replaceFirst("and", "where"));
        return (Long) query.getSingleResult();
    }

    private void addQueryCondition(Topic s_topic, StringBuffer hql) {
        if (s_topic != null) {
            if (StringUtil.isNotEmpty(s_topic.getTitle())) {
                hql.append(" and t.title like '%").append(s_topic.getTitle()).append("%'");
            }
            if (s_topic.getUser() != null) {
                if (StringUtil.isNotEmpty(s_topic.getUser().getNickName())) {
                    hql.append(" and t.user.nickName like '%").append(s_topic.getUser().getNickName()).append("%'");
                }
            }
            if (s_topic.getPublishTime() != null) {
                hql.append(" and t.publishTime=").append(s_topic.getPublishTime());
            }
            if (s_topic.getModifyTime() != null) {
                hql.append(" and t.modifyTime=").append(s_topic.getModifyTime());
            }
            if (s_topic.getSection() != null) {
                if (s_topic.getSection().getId() > 0) {
                    hql.append(" and t.section.id=").append(s_topic.getSection().getId());
                }
            }
            if (s_topic.getTop() != 2) {
                hql.append(" and t.top=").append(s_topic.getTop());
            }
            if (s_topic.getGood() != 2) {
                hql.append(" and t.good=").append(s_topic.getGood());
            }
        }
    }

    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    @Override
    public Topic findTopicById(long topicId) {
        return em.find(Topic.class, topicId);
    }

    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    @Override
    public List<Topic> findZdTopicListBySectionId(long sectionId, int pageSize, int pageNo) {
        StringBuffer hql = new StringBuffer("from Topic where top=1");
        if (sectionId > 0) {
            hql.append(" and sectionId=").append(sectionId);
        }
        Query query = em.createQuery(hql.toString());
        @SuppressWarnings("unchecked")
        List<Topic> result = query.setMaxResults(pageSize).setFirstResult(pageNo * pageSize).getResultList();
        em.clear();
        return result;
    }

    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    @Override
    public List<Topic> findPtTopicListBySectionId(long sectionId, int pageSize, int pageNo) {
        StringBuffer hql = new StringBuffer("from Topic");
        if (sectionId > 0) {
            hql.append(" and sectionId=").append(sectionId);
        }
        hql.append(" and top=0 order by modifyTime desc");
        Query query = em.createQuery(hql.toString().replaceFirst("and", "where"));
        @SuppressWarnings("unchecked")
        List<Topic> result = query.setMaxResults(pageSize).setFirstResult(pageNo * pageSize).getResultList();
        em.clear();
        return result;
    }

    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    @Override
    public Long getNoReplyTopicCount(long sectionId) {
        String hql = "select count(*) from Topic where replySum=0 and sectionId=" + sectionId;
        Query query = em.createQuery(hql);
        return (Long) query.getSingleResult();
    }

    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    @Override
    public Long getGoodTopicCount(long sectionId) {
        String hql = "select count(*) from Topic where good=1 and sectionId=" + sectionId;
        Query query = em.createQuery(hql);
        return (Long) query.getSingleResult();
    }

    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    @Override
    public Long getAllTopicCount() {
        String hql = "select count(*) from Topic t";
        Query query = em.createQuery(hql);
        return (Long) query.getSingleResult();
    }

    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    @Override
    public Long getTotalTopicCount(long sectionId) {
        String hql = "select count(*) from Topic where sectionId=" + sectionId;
        Query query = em.createQuery(hql);
        return (Long) query.getSingleResult();
    }

    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    @Override
    public List<Topic> findGoodTopicListBySectionId(long sectionId,
                                                    int pageSize, int pageNo) {
        StringBuffer hql = new StringBuffer("from Topic where good=1");
        if (sectionId > 0) {
            hql.append(" and sectionId=").append(sectionId);
        }
        Query query = em.createQuery(hql.toString());
        @SuppressWarnings("unchecked")
        List<Topic> result = query.setMaxResults(pageSize).setFirstResult(pageNo * pageSize).getResultList();
        em.clear();
        return result;
    }

    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    @Override
    public List<Topic> findNotGoodTopicListBySectionId(long sectionId,
                                                       int pageSize, int pageNo) {
        StringBuffer hql = new StringBuffer("from Topic where good=0");
        if (sectionId > 0) {
            hql.append(" and sectionId=").append(sectionId);
        }
        Query query = em.createQuery(hql.toString());
        @SuppressWarnings("unchecked")
        List<Topic> result = query.setMaxResults(pageSize).setFirstResult(pageNo * pageSize).getResultList();
        em.clear();
        return result;
    }

    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    @Override
    public Long getPtTopicCountBySectionId(long sectionId) {
        String hql = "select count(*) from Topic where top=0 and sectionId=" + sectionId;
        Query query = em.createQuery(hql);
        return (Long) query.getSingleResult();
    }

    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    @Override
    public TopicContent getTopicContent(long topicId) {
        String hql = "from TopicContent t where t.topicId=" + topicId;
        Query query = em.createQuery(hql);
        TopicContent result = (TopicContent) query.getSingleResult();
        em.clear();
        return result;
//		Map<String, Integer> map = new HashMap<String,Integer>(1);
//		map.put("topicId", topicId);
//		return em.find(TopicContent.class, map);

    }

    @Override
    public TopicContent saveTopicContent(TopicContent topicContent) {
        topicContent = em.merge(topicContent);
        return topicContent;
    }

    @Override
    public TopicContent findTopicContentByTopicId(long topicId) {
        String hql = "from TopicContent where topicId = " + topicId;
        Query query = em.createQuery(hql.toString());
        TopicContent result = null;
        result = (TopicContent) query.getSingleResult();
        em.clear();
        return result;
    }
}
