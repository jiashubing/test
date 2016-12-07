package common.service;

import javax.annotation.Resource;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import forum.po.DbUser;
import forum.service.DbUserService;

public class CustomUserDetailsService implements UserDetailsService {

	@Resource(name="dbUserServiceImpl")
	private DbUserService dbUserService;

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		DbUser dbUser = null;
		try {
			dbUser = dbUserService.getByName(username);

		} catch (Exception e) {
			throw new UsernameNotFoundException("Error in retrieving user");
		}
		return dbUser;

	}

}
