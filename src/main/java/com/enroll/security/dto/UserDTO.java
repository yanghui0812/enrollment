package com.enroll.security.dto;

import java.io.Serializable;
import java.util.Date;

import com.enroll.security.enums.EntityStatus;

public class UserDTO implements Serializable  {

	private static final long serialVersionUID = -1406048897257538593L;

	private String id;
	
	private String name;

	private String password;

	private String active;

	private String fullName;

	private String title;
	
	private String department;

	private Long createuserId;

	private String createUser;

	private Long modifyUserId;

	private String modifyUser;

	private Date createTimestamp;

	private Date modifyTimestamp;

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

	public boolean isActive() {
		return EntityStatus.isAcive(active);
	}
	
	public String getActiveDesc() {
		return EntityStatus.getEntityStatus(active).getDesc();
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
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
}