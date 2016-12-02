package forum.service.impl;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import forum.po.Reply;
import forum.service.ReplyService;

@Service
@Transactional
public class ReplyServiceImpl implements ReplyService {
	
	@PersistenceContext 
	protected EntityManager em;	

	@Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
	@Override
	public Reply findLastReplyByTopicId(int topicId) {
		StringBuffer hql=new StringBuffer("from Reply");
		if (topicId>0) {
			hql.append(" where topicId = "+topicId);
		}
		hql.append(" order by publishTime desc");
		
		Query query = em.createQuery(hql.toString());
		List<Reply> result = query.getResultList();
		em.clear();
		if (result != null && result.size()>0) {
			return result.get(0);
		} else {
			return null;
		}
	}

	@Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
	@Override
	public Long getReplyCountByTopicId(int topicId) {
		StringBuffer hql=new StringBuffer("select count(*) from Reply");
		if (topicId>0) {
			hql.append(" where topicId ="+topicId);
		}
		Query query = em.createQuery(hql.toString());
		return (Long)query.getSingleResult();
	}

	@Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
	@Override
	public List<Reply> findReplyListByTopicId(int topicId, int pageSize,int pageNo) {
		List<Object> param=new LinkedList<Object>();
		StringBuffer hql=new StringBuffer("from Reply");
		if (topicId>0) {
			hql.append(" where topicId="+topicId);
		}
		//hql.append(" order by publishTime desc");
		Query query = em.createQuery(hql.toString());
		List<Reply> result = query.setMaxResults(pageSize).setFirstResult(pageNo).getResultList();
		em.clear();
		return result;
	}

	@Override
	public void saveReply(Reply reply) {
		em.merge(reply);
	}

	@Override
	public void deleteReply(Reply reply) {
		Query query=em.createQuery("delete from Reply where id= "+reply.getId());
		query.executeUpdate();
	}

	@Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
	@Override
	public Reply findReplyById(int replyId) {
		return em.find(Reply.class, replyId);
	}

}
