package forum.service.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import forum.po.Reply;
import forum.po.ReplyContent;
import forum.service.ReplyService;

@Service
@Transactional
public class ReplyServiceImpl implements ReplyService {

    @PersistenceContext
    protected EntityManager em;

    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    @Override
    public Reply findLastReplyByTopicId(long topicId) {
        StringBuffer hql = new StringBuffer("from Reply");
        if (topicId > 0) {
            hql.append(" where topicId = ").append(topicId);
        }
        hql.append(" order by publishTime desc");

        Query query = em.createQuery(hql.toString());
        @SuppressWarnings("unchecked")
        List<Reply> result = query.getResultList();
        em.clear();
        if (result != null && result.size() > 0) {
            return result.get(0);
        } else {
            return null;
        }
    }

    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    @Override
    public Long getReplyCountByTopicId(long topicId) {
        StringBuffer hql = new StringBuffer("select count(*) from Reply");
        if (topicId > 0) {
            hql.append(" where topicId =").append(topicId);
        }
        Query query = em.createQuery(hql.toString());
        return (Long) query.getSingleResult();
    }

    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    @Override
    public List<Reply> findReplyListByTopicId(long topicId, int pageSize, int pageNo) {
        String hql = "from Reply where topicId=" + topicId;
        Query query = em.createQuery(hql);
        @SuppressWarnings("unchecked")
        List<Reply> result = query.setMaxResults(pageSize).setFirstResult(pageNo * pageSize).getResultList();
        em.clear();
        return result;
    }

    @Override
    public Reply saveReply(Reply reply) {
        reply = em.merge(reply);
        return reply;
    }

    @Override
    public void deleteReply(Reply reply) {
        Query query = em.createQuery("delete from Reply ReplyContent replyId= " + reply.getId());
        query.executeUpdate();
        query = em.createQuery("delete from Reply where id= " + reply.getId());
        query.executeUpdate();
    }

    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    @Override
    public Reply findReplyById(long replyId) {
        return em.find(Reply.class, replyId);
    }

    @Override
    public void deleteReplyByTopicId(long topicId) {
        Query query = em.createQuery("delete from ReplyContent where topicId= " + topicId);
        query.executeUpdate();
        query = em.createQuery("delete from Reply where topicId= " + topicId);
        query.executeUpdate();
    }

    @Override
    public void deleteReplyById(long replyId) {
        Query query = em.createQuery("delete from Reply where id= " + replyId);
        query.executeUpdate();
        query = em.createQuery("delete from ReplyContent where replyId= " + replyId);
        query.executeUpdate();
    }

    @Override
    public List<ReplyContent> getReplyContentListByTopicId(long topicId, int pageSize,
                                                           int pageNo) {
        String hql = "from ReplyContent where topicId=" + topicId;
        Query query = em.createQuery(hql);
        @SuppressWarnings("unchecked")
        List<ReplyContent> result = query.setMaxResults(pageSize).setFirstResult(pageNo * pageSize).getResultList();
        em.clear();
        return result;
    }

    @Override
    public ReplyContent saveReplyContent(ReplyContent replyContent) {
        replyContent = em.merge(replyContent);
        return replyContent;
    }

    @Override
    public ReplyContent findReplyContentByReplyId(long replyId) {
        String hql = "from ReplyContent where replyId = " + replyId;
        Query query = em.createQuery(hql);
        ReplyContent replyContent = null;
        replyContent = (ReplyContent) query.getSingleResult();
        em.clear();
        return replyContent;
    }

}
