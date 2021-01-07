package org.doudou.doudouflow.dao.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * 用户实体<br>
 * 
 * @author decai
 * @since 1.0
 * @version 1.0
 */
@Entity
@Table(name = "d_user")
@org.hibernate.annotations.Table(appliesTo = "d_user", comment = "用户表")
public class User {

	/**
	 * username
	 */
	@Id
	@Column(name = "username", nullable = false)
	private String username;

	/**
	 * 用户姓名
	 */
	@Column(name = "name")
	private String name;

	/**
	 * 密码
	 */
	@Column(name = "password")
	private String password;

	/**
	 * 电话
	 */
	@Column(name = "phone")
	private String phone;

	/**
	 * 性别 0男1女
	 */
	@Column(name = "sex")
	private Integer sex = 0;

	/**
	 * 上次登录时间
	 */
	@Column(name = "last_login_time")
	private Date lastLoginTime;

	/**
	 * 账号被锁定时间
	 */
	@Column(name = "locked_time")
	private Date lockedTime;

	/**
	 * 是否启用
	 */
	@Column(name = "is_enabled", nullable = false)
	private boolean enabled = true;

	/**
	 * 是否逻辑删除
	 */
	@Column(name = "is_deleted", nullable = false)
	private boolean deleted;

	/**
	 * 是否内建, true代表系统内建的账号，比如系统管理员，不可删除
	 */
	@Column(name = "is_built_in", nullable = false)
	private boolean builtIn;

	@Column(name = "is_account_non_expired", nullable = false)
	private boolean accountNonExpired = true;

	@Column(name = "is_account_non_locked", nullable = false)
	private boolean isAccountNonLocked = true;

	@Column(name = "is_credentials_non_expired", nullable = false)
	private boolean credentialsNonExpired = true;

	/**
	 * 帐号创建时间
	 */
	@Column(name = "create_time")
	private Date createTime = new Date();

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * @return the sex
	 */
	public Integer getSex() {
		return sex;
	}

	/**
	 * @param sex the sex to set
	 */
	public void setSex(Integer sex) {
		this.sex = sex;
	}

	/**
	 * @return the lastLoginTime
	 */
	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	/**
	 * @param lastLoginTime the lastLoginTime to set
	 */
	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	/**
	 * @return the lockedTime
	 */
	public Date getLockedTime() {
		return lockedTime;
	}

	/**
	 * @param lockedTime the lockedTime to set
	 */
	public void setLockedTime(Date lockedTime) {
		this.lockedTime = lockedTime;
	}

	/**
	 * @return the enabled
	 */
	public boolean isEnabled() {
		return enabled;
	}

	/**
	 * @param enabled the enabled to set
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	/**
	 * @return the deleted
	 */
	public boolean isDeleted() {
		return deleted;
	}

	/**
	 * @param deleted the deleted to set
	 */
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	/**
	 * @return the builtIn
	 */
	public boolean isBuiltIn() {
		return builtIn;
	}

	/**
	 * @param builtIn the builtIn to set
	 */
	public void setBuiltIn(boolean builtIn) {
		this.builtIn = builtIn;
	}

	/**
	 * @return the accountNonExpired
	 */
	public boolean isAccountNonExpired() {
		return accountNonExpired;
	}

	/**
	 * @param accountNonExpired the accountNonExpired to set
	 */
	public void setAccountNonExpired(boolean accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}

	/**
	 * @return the isAccountNonLocked
	 */
	public boolean isAccountNonLocked() {
		return isAccountNonLocked;
	}

	/**
	 * @param isAccountNonLocked the isAccountNonLocked to set
	 */
	public void setAccountNonLocked(boolean isAccountNonLocked) {
		this.isAccountNonLocked = isAccountNonLocked;
	}

	/**
	 * @return the credentialsNonExpired
	 */
	public boolean isCredentialsNonExpired() {
		return credentialsNonExpired;
	}

	/**
	 * @param credentialsNonExpired the credentialsNonExpired to set
	 */
	public void setCredentialsNonExpired(boolean credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
	}

	/**
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
