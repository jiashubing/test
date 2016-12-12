package forum.service.impl;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import forum.po.Zone;
import forum.service.ZoneService;
import forum.util.StringUtil;

@Service
@Transactional
public class ZoneServiceImpl implements ZoneService {
	
	@PersistenceContext 
	protected EntityManager em;	

	@Override
	public void saveZone(Zone zone) {
		em.merge(zone);
	}

	@Override
	public void deleteZone(Zone zone) {
		Query query=em.createQuery("delete from Zone where id= "+zone.getId());
		query.executeUpdate();
	}
	
	@Override
	public void deleteZoneById(Integer id) {
		Query query=em.createQuery("delete from Zone where id= "+id);
		query.executeUpdate();
	}

	@Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
	@Override
	public List<Zone> findZoneList(Zone s_zone,int pageSize,int pageNo) {
		StringBuffer hql=new StringBuffer("from Zone");
		if (s_zone!=null) {
			if (StringUtil.isNotEmpty(s_zone.getName())) {
				hql.append(" and name like %"+s_zone.getName()+"%");
			}
		}
		Query query = em.createQuery(hql.toString().replaceFirst("and", "where"));
		List<Zone> result = query.setMaxResults(pageSize).setFirstResult(pageNo*pageSize).getResultList();
		em.clear();
		return result;
	}
	
	@Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
	@Override
	public Long getZoneCount(Zone s_zone) {
		List<Object> param=new LinkedList<Object>();
		StringBuffer hql=new StringBuffer("select count(*) from Zone");
		if (s_zone!=null) {
			if (StringUtil.isNotEmpty(s_zone.getName())) {
				hql.append(" and name like %"+s_zone.getName()+"%");
			}
		}
		Query query = em.createQuery(hql.toString().replaceFirst("and", "where"));
		return (Long)query.getSingleResult();
	}

	@Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
	@Override
	public Zone findZoneById(int zoneId) {
		return em.find(Zone.class, zoneId);
	}

}
