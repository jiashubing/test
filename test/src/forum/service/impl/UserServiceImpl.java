package forum.service.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import forum.po.User;
import forum.service.UserService;
import forum.util.StringUtil;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @PersistenceContext
    protected EntityManager em;

    @Override
    public void saveUser(User user) {
        em.merge(user);
    }

    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    @Override
    public boolean existUserWithNickName(String nickName) {
        String hql = "select count(*) from User where nickName=?1";
        Query query = em.createQuery(hql);
        query.setParameter(1, nickName);
        long count = (Long) query.getSingleResult();
        return count > 0;
    }

    /**
     * fasle：邮箱不可以用   true：邮箱可以使用
     */
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    @Override
    public boolean checkEmail(String email) {
        String hql = "select count(*) from User where email=?1";
        Query query = em.createQuery(hql);
        query.setParameter(1, email);
        long count = (Long) query.getSingleResult();
        return count <= 0;
    }

    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    @Override
    public User login(User user) {
        String hql = "from User u where u.nickName=?1 and u.password=?2";
        Query query = em.createQuery(hql);
        query.setParameter(1, user.getNickName());
        query.setParameter(2, user.getPassword());
        return (User) query.getSingleResult();
    }

    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    @Override
    public List<User> findUserList(User s_user, int pageSize, int pageNo) {
        StringBuffer hql = new StringBuffer("from User");
        addQueryCondition(s_user, hql);
        Query query = em.createQuery(hql.toString().replaceFirst("and", "where"));
        @SuppressWarnings("unchecked")
        List<User> result = query.setMaxResults(pageSize).setFirstResult(pageNo * pageSize).getResultList();
        em.clear();
        return result;
    }

    private void addQueryCondition(User s_user, StringBuffer hql) {
        if (s_user != null) {
            if (StringUtil.isNotEmpty(s_user.getNickName())) {
                hql.append(" and nickName like '%").append(s_user.getNickName()).append("%'");
            }
            if (s_user.getType() > 0) {
                hql.append(" and type = ").append(s_user.getType());
            }
        }
    }

    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    @Override
    public Long getUserCount(User s_user) {
        StringBuffer hql = new StringBuffer("select count(*) from User");
        addQueryCondition(s_user, hql);
        Query query = em.createQuery(hql.toString().replaceFirst("and", "where"));
        return (Long) query.getSingleResult();
    }

    @Override
    public void delete(User user) {
        Query query = em.createQuery("delete from User where id= " + user.getId());
        query.executeUpdate();
    }

    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    @Override
    public User getUserById(long id) {
        return em.find(User.class, id);
    }

    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    @Override
    public User getUserByNickName(String nickName) {
        StringBuffer hql = new StringBuffer("from User");
        if (StringUtil.isNotEmpty(nickName)) {
            hql.append(" where nickName = '").append(nickName + "'");
        }
        Query query = em.createQuery(hql.toString());

        Object obj;
        try {
            obj = query.getSingleResult();
            return (User) obj;
        } catch (Exception e) {
            return null;
        }
    }

}
