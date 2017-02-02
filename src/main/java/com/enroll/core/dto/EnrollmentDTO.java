package com.enroll.core.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class EnrollmentDTO implements Serializable {
	
	private static final long serialVersionUID = -3302285451035479529L;

	private String registrId;
	
	private String status;
	
	private String phoneNumber;
	
	private String id;
	
	private Date registrDate;
	
	private List<FormFieldValueDTO> fieldValueList = new ArrayList<FormFieldValueDTO>();

	public List<FormFieldValueDTO> getFieldValueList() {
		return fieldValueList;
	}

	public void setFieldValueList(List<FormFieldValueDTO> fieldValueList) {
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