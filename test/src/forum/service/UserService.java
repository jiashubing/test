package forum.service;

import java.util.List;

import forum.po.User;

public interface UserService {

	void saveUser(User user);
	
	boolean existUserWithNickName(String nickName);
	boolean checkEmail(String email);
	
	User login(User user);
	
	List<User> findUserList(User s_user,int pageSize,int pageNo);
	
	Long getUserCount(User s_user);
	
	void delete(User user);
	
	User getUserById(long id);
	
	User getUserByNickName(String nickName);
}
