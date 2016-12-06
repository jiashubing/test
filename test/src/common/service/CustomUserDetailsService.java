package common.service;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import forum.po.DbUser;
import forum.service.DbUserService;

public class CustomUserDetailsService implements UserDetailsService {

	@Resource(name="dbUserServiceImpl")
	private DbUserService dbUserService;

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserDetails user = null;
		try {
			// 搜索数据库以匹配用户登录名.
			// 我们可以通过dao使用JDBC来访问数据库
			DbUser dbUser = dbUserService.getByName(username);

			user = new User(dbUser.getUsername(), dbUser.getPassword().toLowerCase(), true, true, true, true,
					getAuthorities(dbUser.getAccess()));

		} catch (Exception e) {
			throw new UsernameNotFoundException("Error in retrieving user");
		}
		return user;

	}

	/**
	 * 获得访问角色权限
	 * 
	 * @param access
	 * @return
	 */
	private Collection<? extends GrantedAuthority> getAuthorities(Integer access) {
		List<GrantedAuthority> authList = new ArrayList<GrantedAuthority>(2);
		// 所有的用户默认拥有ROLE_USER权限
		authList.add(new SimpleGrantedAuthority("ROLE_USER"));

		// 如果参数access为1.则拥有ROLE_ADMIN权限
		if (access.compareTo(1) == 0) {
			authList.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		}
		return authList;
	}

}
