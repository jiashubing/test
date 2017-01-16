package common.service.Impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import common.po.Opinion;
import common.service.OpinionService;

@Service
@Transactional
public class OpinionServiceImpl implements OpinionService{

	@PersistenceContext 
	protected EntityManager em;	

	@Override
	public void saveOpinion(Opinion opinion) {
		em.persist(opinion);
	}

}
