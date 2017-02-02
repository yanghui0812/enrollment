package com.enroll.core.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@Table(name = "TBL_ENROLLMENT" )
public class Enrollment implements Serializable {
	
	private static final long serialVersionUID = 7006047972093916813L;

	@Id
	@Column(name = "REGISTR_ID", nullable = false)
	private String registrId;
	
	@Column(name = "STATUS")
	private String status;
	
	@Column(name = "PHONE_NUMBER")
	private String phoneNumber;
	
	@Column(name = "ID")
	private String id;
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<FormFieldValue> fieldValueList = new ArrayList<FormFieldValue>();

	@Column(name = "REGISTR_DATE", updatable = false)
	private Date registrDate;
	
	@Version
	@Column(name = "UPDATED_TIMESTAMP")
	private Date modifiedDate;

	public List<FormFieldValue> getFieldValueList() {
		return fieldValueList;
	}

	public void setFieldValueList(List<FormFieldValue> fieldValueList) {
		this.fieldValueList = fieldValueList;
	}

	public String getRegistrId() {
		return registrId;
	}

	public void setRegistrId(String registrId) {
		this.registrId = registrId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getRegistrDate() {
		return registrDate;
	}

	public void setRegistrDate(Date registrDate) {
		this.registrDate = registrDate;
	}
}