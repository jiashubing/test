package forum.service.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import forum.po.DbUser;
import forum.service.DbUserService;


@Service
@Transactional
public class DbUserServiceImpl implements DbUserService {
	@PersistenceContext protected EntityManager em;	
	
	@Override
	public void save(DbUser user) {
		em.persist(user);
	}

	
	@Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
	@Override	
	public DbUser getById(int id) {
		return em.find(DbUser.class, id);
	}
	
	@Override
	public void merge (DbUser user) {
		em.merge(user);
	}


	/**
	 * 分页查询
	 */
	@Override
	public List<DbUser> getAllUser(int pageSize,int pageNo) {
		try {
			int index = (pageNo-1)*pageSize;
			Query query = em.createQuery("from DbUser order by id asc");
			@SuppressWarnings("unchecked")
			List<DbUser> list = query.setMaxResults(pageSize).setFirstResult(index).getResultList();
			em.clear();
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}


	@Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
	@Override	
	public DbUser getByName(String name) {
//		Query query = em.createQuery("from DbUser where username="+name);
		String hql="from DbUser where username=?1";
		Query query = em.createQuery(hql);
		query.setParameter(1, name);
		List<DbUser> result = query.getResultList();
		em.clear();
		return result==null ? null : result.get(0);
	}

}
