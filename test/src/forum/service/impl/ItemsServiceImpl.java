//package shubing.study.service.impl;
//
//import java.util.List;
//
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//import javax.persistence.Query;
//
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Propagation;
//import org.springframework.transaction.annotation.Transactional;
//
//import shubing.study.po.Items;
//import shubing.study.service.ItemsService;
//
//@Service
//@Transactional
//public class ItemsServiceImpl implements ItemsService {
//	@PersistenceContext protected EntityManager em;	
//	
//	@Override
//	public void save(Items item) {
//		em.persist(item);
//	}
//
//	
//	@Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
//	@Override	
//	public Items getById(int id) {
//		return em.find(Items.class, id);
//	}
//	
//	@Override
//	public void merge (Items item) {
//		em.merge(item);
//	}
//
//
//	/**
//	 * 分页查询
//	 */
//	@Override
//	public List<Items> getAllItems(int pageSize,int pageNo) {
//		try {
//			int index = (pageNo-1)*pageSize;
//			Query query = em.createQuery("from Items order by id asc");
//			@SuppressWarnings("unchecked")
//			List<Items> list = query.setMaxResults(pageSize).setFirstResult(index).getResultList();
//			em.clear();
//			return list;
//		} catch (Exception e) {
//			e.printStackTrace();
//			return null;
//		}
//	}
//
//}
