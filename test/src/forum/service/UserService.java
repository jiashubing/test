package forum.service;

import java.util.List;

import forum.po.User;

public interface UserService {

	public void saveUser(User user);
	
	public boolean existUserWithNickName(String nickName);
	public boolean checkEmail(String email);
	
	public User login(User user);
	
	public List<User> findUserList(User s_user,int pageSize,int pageNo);
	
	public Long getUserCount(User s_user);
	
	public void delete(User user);
	
	public User getUserById(long id);
	
	public User getUserByNickName(String nickName);
}
