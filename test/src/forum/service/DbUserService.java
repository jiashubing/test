package forum.service;

import java.util.List;

import forum.po.DbUser;

public interface DbUserService {
	public void save(DbUser user);
	public DbUser getById(int id);
	public DbUser getByName(String name);
	/**
	 * 校验用户名是否唯一
	 * @param name
	 * @return true:用户名唯一  false:用户名不唯一
	 */
	public boolean checkUserName(String name);
	public void merge(DbUser user);
	public List<DbUser> getAllUser(int pageSize,int pageNo);
	

}
