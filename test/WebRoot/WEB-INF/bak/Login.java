//package com.huotu.hotedu.entity;
//
//import org.springframework.security.core.userdetails.UserDetails;
//
//import javax.persistence.*;
//import java.io.Serializable;
//import java.util.Date;
//
///**
// * Created by luffy on 2015/6/10.
// * Modify by cwb on 2015/7/15
// * 可登录角色
// *
// * @author luffy luffy.ja at gmail.com
// */
//@Entity
//@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"loginName"}))
//@Inheritance(strategy = InheritanceType.JOINED)
//public abstract class Login implements UserDetails,Serializable {
//    private static final long serialVersionUID = -349012453592429794L;
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//    /**
//     * 账号名称
//     */
//    @Column
//    private String loginName;
//    /**
//     * 账号密码
//     */
//    private String password;
//    /**
//     * 是否可用
//     */
//    private boolean enabled = true;
//
//    /**
//     * 上次登录时间
//     */
//    @Temporal(TemporalType.TIMESTAMP)
//    private Date lastLoginDate;
//    /**
//     * 上次登录IP
//     */
//    private String lastLoginIP;
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getLoginName() {
//        return loginName;
//    }
//
//    public void setLoginName(String loginName) {
//        this.loginName = loginName;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return enabled;
//    }
//
//    public void setEnabled(boolean enabled) {
//        this.enabled = enabled;
//    }
//
//    public Date getLastLoginDate() {
//        return lastLoginDate;
//    }
//
//    public void setLastLoginDate(Date lastLoginDate) {
//        this.lastLoginDate = lastLoginDate;
//    }
//
//    public String getLastLoginIP() {
//        return lastLoginIP;
//    }
//
//    public void setLastLoginIP(String lastLoginIP) {
//        this.lastLoginIP = lastLoginIP;
//    }
//    @Override
//    public String getUsername() {
//        return loginName;
//    }
//    @Override
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//    @Override
//    public boolean isAccountNonLocked() {
//        return true;
//    }
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
//}


//AuthenticationPrincipal
