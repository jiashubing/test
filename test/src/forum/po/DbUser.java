package forum.po;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name="dbuser")
public class DbUser implements UserDetails,Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3275519034298486048L;
	
	@Id 
	@GeneratedValue
	private Integer id;
	
	@Column(length=20)
    private String username;
	
	@Column(length=255)
	private String password;

	@Column
	private Integer access;
	
	@Temporal(TemporalType.TIMESTAMP)
    private Date lasttime;
	
	@Column(length=20)
    private String ipaddr;
	
	@Column(columnDefinition="tinyint default 1")
	private boolean enabled = true;
	
	//指定了OneToOne的关联关系，mappedBy同样指定由对方来进行维护关联关系;
	//CascadeType.ALL包含所有；fetch=FetchType.LAZY懒加载
	@OneToOne(mappedBy="dbUser",cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	private User user;
	
	
	public DbUser() {
		
	}
    
	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getAccess() {
		return access;
	}

	public void setAccess(Integer access) {
		this.access = access;
	}

	public Date getLasttime() {
		return lasttime;
	}

	public void setLasttime(Date lasttime) {
		this.lasttime = lasttime;
	}

	public String getIpaddr() {
		return ipaddr;
	}

	public void setIpaddr(String ipaddr) {
		
		this.ipaddr = ipaddr;
	}
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.getAuthorities(this.access);
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

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
