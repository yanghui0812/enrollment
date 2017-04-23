package com.enroll.security.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.hibernate.annotations.BatchSize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.enroll.security.enums.EntityStatus;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "TBL_USER_INFO")
public class User implements UserDetails {

	private static final long serialVersionUID = -6130666978254887990L;

	@Id
	@Column(name = "USER_ID")
	private String id;

	@Column(name = "USER_NAME", nullable = false)
	private String name;

	@Column(name = "PASSWORD")
	private String password;

	@Column(name = "ACTIVE")
	private String active = EntityStatus.ACIVE.getKey();

	@Column(name = "USER_PHOTO_URL")
	private String imageUrl;

	@Column(name = "FULL_NAME")
	private String fullName;

	@Column(name = "TITLE")
	private String title;
	
	@Column(name = "DEPARTMENT")
	private String department;
	
	@ManyToMany(fetch = FetchType.LAZY, targetEntity = Role.class)
	@JoinTable(name = "TBL_USER_ROLE_XREF", joinColumns = @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID") , inverseJoinColumns = @JoinColumn(name = "ROLE_ID", referencedColumnName = "ROLE_ID") )
	@BatchSize(size = 50)
	private Set<Role> allRoles = new HashSet<Role>();

	@Column(name = "CREATE_USER_ID", nullable = false)
	private String createuserId;

	@Column(name = "CREATE_USER_NAME")
	private String createUser;

	@Column(name = "MODIFY_USER_ID")
	private String modifyUserId;

	@Column(name = "MODIFY_USER_NAME")
	private String modifyUser;

	@Column(name = "CREATE_TIMESTAMP", updatable = false)
	private Date createTimestamp;

	@Version
	@Column(name = "MODIFY_TIMESTAMP")
	private Date modifyTimestamp;
	
	@Transient
	private Collection<GrantedAuthority> grantedAuthority = new ArrayList<GrantedAuthority>();

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

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getCreateuserId() {
		return createuserId;
	}

	public void setCreateuserId(String createuserId) {
		this.createuserId = createuserId;
	}

	public String getModifyUserId() {
		return modifyUserId;
	}

	public void setModifyUserId(String modifyUserId) {
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Set<Role> getAllRoles() {
		return allRoles;
	}

	public void setAllRoles(Set<Role> allRoles) {
		this.allRoles = allRoles;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}
}