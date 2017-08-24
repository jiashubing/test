package forum.service.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import forum.po.Section;
import forum.service.SectionService;
import forum.util.StringUtil;


@Service
@Transactional
public class SectionServiceImpl implements SectionService {

	@PersistenceContext 
	protected EntityManager em;	
	
	@Override
	public void saveSection(Section section) {
		em.merge(section);
	}

	@Override
	public void deleteSection(Section section) {
		Query query=em.createQuery("delete from Section where id= "+section.getId());
		query.executeUpdate();
	}
	
	@Override
	public void deleteSectionById(long sectionId) {
		Query query=em.createQuery("delete from Section where id= "+sectionId);
		query.executeUpdate();
	}

	@Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
	@Override
	public List<Section> findSectionList(Section s_section,int pageSize,int pageNo) {
		StringBuffer hql=new StringBuffer("from Section");
		if (s_section!=null) {
			if (StringUtil.isNotEmpty(s_section.getName())) {
				hql.append(" and name like '%").append(s_section.getName()).append("%'");
			}
			if (s_section.getZone()!=null&&s_section.getZone().getId()>0) {
				hql.append(" and zoneId = ").append(s_section.getZone().getId());
			}
			if (s_section.getMaster()!=null&&s_section.getMaster().getId()>0) {
				hql.append(" and masterId = ").append(s_section.getMaster().getId());
			}
		}
		Query query = em.createQuery(hql.toString().replaceFirst("and", "where"));
		@SuppressWarnings("unchecked")
		List<Section> result = query.setMaxResults(pageSize).setFirstResult(pageNo*pageSize).getResultList();
		em.clear();
		return result;
	}
	
	@Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
	@Override
	public List<Section> findSectionListByZoneId(long zoneId,int pageSize,int pageNo) {
		String hql="from Section where zoneId = "+zoneId;
		Query query = em.createQuery(hql);
		@SuppressWarnings("unchecked")
		List<Section> result = query.setMaxResults(pageSize).setFirstResult(pageNo*pageSize).getResultList();
		em.clear();
		return result;
	}
	
	@Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
	@Override
	public Long getSectionCount(Section s_section) {
		StringBuffer hql=new StringBuffer("select count(*) from Section");
		if (s_section!=null) {
			if (StringUtil.isNotEmpty(s_section.getName())) {
				hql.append(" and name like '%").append(s_section.getName()).append("%'");
			}
			if (s_section.getZone()!=null&&s_section.getZone().getId()>0) {
				hql.append(" and zoneId = ").append(s_section.getZone().getId());
			}
			if (s_section.getMaster()!=null&&s_section.getMaster().getId()>0) {
				hql.append(" and masterId = ").append(s_section.getMaster().getId());
			}
		}
		Query query = em.createQuery(hql.toString().replaceFirst("and", "where"));
		return (Long)query.getSingleResult();
	}

	@Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
	@Override
	public Section findSectionById(long sectionId) {
		return em.find(Section.class, sectionId);
	}

}
