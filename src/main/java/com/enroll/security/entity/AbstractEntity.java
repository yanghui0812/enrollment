package com.enroll.security.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;


@MappedSuperclass
public abstract class AbstractEntity {

	@Column(name = "CREATED_USER_ID", nullable = false, updatable = false)
	protected String createdUserId;

	@Column(name = "CREATED_USER_NAME", updatable = false)
	protected String createdUsername;

	@Column(name = "CREATED_TIMESTAMP" , updatable = false)
	protected LocalDateTime createdTimestamp;

	@Column(name = "MODIFIED_USER_ID")
	protected String modifiedUserId;// 修改用户编号

	@Column(name = "MODIFIED_USER_NAME")
	protected String modifiedUsername;

	@Version
	@Column(name = "MODIFIED_TIMESTAMP")
	protected LocalDateTime modifiedTimestamp;	

	public LocalDateTime getCreatedTimestamp() {
		return createdTimestamp;
	}

	public void setCreatedTimestamp(LocalDateTime createdTimestamp) {
		this.createdTimestamp = createdTimestamp;
	}

	public LocalDateTime getModifiedTimestamp() {
		return modifiedTimestamp;
	}

	public void setModifiedTimestamp(LocalDateTime modifiedTimestamp) {
		this.modifiedTimestamp = modifiedTimestamp;
	}

	public String getCreatedUsername() {
		return createdUsername;
	}

	public void setCreatedUsername(String createdUsername) {
		this.createdUsername = createdUsername;
	}

	public String getModifiedUsername() {
		return modifiedUsername;
	}

	public void setModifiedUsername(String modifiedUsername) {
		this.modifiedUsername = modifiedUsername;
	}

	public String getCreatedUserId() {
		return createdUserId;
	}

	public void setCreatedUserId(String createdUserId) {
		this.createdUserId = createdUserId;
	}

	public String getModifiedUserId() {
		return modifiedUserId;
	}

	public void setModifiedUserId(String modifiedUserId) {
		this.modifiedUserId = modifiedUserId;
	}
}