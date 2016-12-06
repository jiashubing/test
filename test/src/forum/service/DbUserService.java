package forum.service;

import java.util.List;

import forum.po.DbUser;

public interface DbUserService {
	public void save(DbUser user);
	public DbUser getById(int id);
	public DbUser getByName(String name);
	public void merge(DbUser user);
	public List<DbUser> getAllUser(int pageSize,int pageNo);
	

}
