package com.enroll.core.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.enroll.common.AppConstant;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "TBL_USER_INFO")
public class User implements UserDetails {

	private static final long serialVersionUID = -6130666978254887990L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "USER_ID")
	private Long id;

	@Column(name = "USER_NAME", nullable = false)
	private String name;

	@Column(name = "PASSWORD")
	private String password;

	@Column(name = "USER_CODE")
	private String userCode;

	@Column(name = "ACTIVE")
	private String active = AppConstant.TRUE;

	@Transient
	private String unencodedPassword;

	@Column(name = "USER_PHOTO_URL")
	private String imageUrl;

	@Column(name = "FULL_NAME")
	private String fullName;

	@Column(name = "TITLE")
	private String title;
	
	@Column(name = "DEPARTMENT")
	private String DEPARTMENT;

	@Column(name = "CREATE_USER_ID", nullable = false)
	private Long createuserId;

	@Column(name = "CREATE_USER_NAME")
	private String createUser;

	@Column(name = "MODIFY_USER_ID")
	private Long modifyUserId;// 修改用户编号

	@Column(name = "MODIFY_USER_NAME")
	private String modifyUser;

	@Column(name = "CREATE_TIMESTAMP", updatable = false)
	private Date createTimestamp;

	@Version
	@Column(name = "MODIFY_TIMESTAMP")
	private Date modifyTimestamp;
	
	@Transient
	private Collection<GrantedAuthority> grantedAuthority = new ArrayList<GrantedAuthority>();

	public String getUnencodedPassword() {
		return unencodedPassword;
	}

	public void setUnencodedPassword(String unencodedPassword) {
		this.unencodedPassword = unencodedPassword;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Collections.unmodifiableCollection(grantedAuthority);
	}

	public void addAuthority(GrantedAuthority authority) {
		grantedAuthority.add(authority);
	}

	@Override
	public String getUsername() {
		return getName();
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
		return true;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public Long getCreateuserId() {
		return createuserId;
	}

	public void setCreateuserId(Long createuserId) {
		this.createuserId = createuserId;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Long getModifyUserId() {
		return modifyUserId;
	}

	public void setModifyUserId(Long modifyUserId) {
		this.modifyUserId = modifyUserId;
	}

	public String getModifyUser() {
		return modifyUser;
	}

	public void setModifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
	}

	public Date getCreateTimestamp() {
		return createTimestamp;
	}

	public void setCreateTimestamp(Date createTimestamp) {
		this.createTimestamp = createTimestamp;
	}

	public Date getModifyTimestamp() {
		return modifyTimestamp;
	}

	public void setModifyTimestamp(Date modifyTimestamp) {
		this.modifyTimestamp = modifyTimestamp;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getDEPARTMENT() {
		return DEPARTMENT;
	}

	public void setDEPARTMENT(String dEPARTMENT) {
		DEPARTMENT = dEPARTMENT;
	}
}