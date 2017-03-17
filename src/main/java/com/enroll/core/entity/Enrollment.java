package com.enroll.core.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.BatchSize;

@Entity
@Table(name = "TBL_ENROLLMENT" )
public class Enrollment implements Serializable {
	
	private static final long serialVersionUID = 7006047972093916813L;

	@Id
	@Column(name = "REGISTER_ID", nullable = false)
	private String registerId;
	
	@Column(name = "FORM_ID", nullable = false)
	private long formId;
	
	@Column(name = "STATUS")
	private String status;
	
	@Column(name = "PHONE_NUMBER")
	private String phoneNumber;
	
	@Column(name = "APPLICANT_NAME")
	private String applicantName;
	
	@Column(name = "ID")
	private String id;
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY, mappedBy = "enrollment")
	@BatchSize(size = 100)
	private List<FormFieldValue> fieldValueList = new ArrayList<FormFieldValue>();

	@Column(name = "REGISTER_DATE", updatable = false)
	private LocalDateTime registerDate;
	
	@Version
	@Column(name = "UPDATED_TIMESTAMP")
	private LocalDateTime modifiedDate;

	public List<FormFieldValue> getFieldValueList() {
		return fieldValueList;
	}

	public void setFieldValueList(List<FormFieldValue> fieldValueList) {
		this.fieldValueList = fieldValueList;
	}
	
	public void addFieldValue(FormFieldValue fieldValue) {
		//fieldValue.setRegistrId(registrId);
		fieldValue.setEnrollment(this);
		fieldValueList.add(fieldValue);
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

	public long getFormId() {
		return formId;
	}

	public void setFormId(long formId) {
		this.formId = formId;
	}

	public LocalDateTime getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(LocalDateTime modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getRegisterId() {
		return registerId;
	}

	public void setRegisterId(String registerId) {
		this.registerId = registerId;
	}

	public LocalDateTime getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(LocalDateTime registerDate) {
		this.registerDate = registerDate;
	}

	public String getApplicantName() {
		return applicantName;
	}

	public void setApplicantName(String applicantName) {
		this.applicantName = applicantName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((registerId == null) ? 0 : registerId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Enrollment other = (Enrollment) obj;
		if (registerId == null) {
			if (other.registerId != null)
				return false;
		} else if (!registerId.equals(other.registerId))
			return false;
		return true;
	}
}