package com.enroll.core.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


public class EnrollmentDTO implements Serializable {
	
	private static final long serialVersionUID = -3302285451035479529L;

	private String registerId;
	
	private String status;
	
	private String phoneNumber;
	
	private String id;
	
	private long formId;
	
	private LocalDateTime registerDate;
	
	private FormMetaDTO formMeta;
	
	private LocalDateTime modifiedDate;
	
	private List<FormFieldValueDTO> fieldValueList = new ArrayList<FormFieldValueDTO>();

	public List<FormFieldValueDTO> getFieldValueList() {
		return fieldValueList;
	}

	public void setFieldValueList(List<FormFieldValueDTO> fieldValueList) {
		this.fieldValueList = fieldValueList;
	}
	
	public void addFieldValue(FormFieldValueDTO fieldValue) {
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

	public String getRegisterId() {
		return registerId;
	}

	public void setRegisterId(String registerId) {
		this.registerId = registerId;
	}

	public LocalDateTime getModifiedDate() {
		return modifiedDate;
	}
	
	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
	
	private static final DateTimeFormatter YYYY_MM_DD = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
	
	public String getRegisterDateStr() {
		return registerDate.format(YYYY_MM_DD);
	}
	
	public void setRegisterDate(String modifiedDateStr) {
		registerDate = LocalDateTime.parse(modifiedDateStr, YYYY_MM_DD);
	}
	
	public String getModifiedDateStr() {
		return modifiedDate.format(FORMATTER);
	}
	
	public void setModifiedDateStr(String modifiedDateStr) {
		modifiedDate = LocalDateTime.parse(modifiedDateStr, FORMATTER);
	}

	public void setModifiedDate(LocalDateTime modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public FormMetaDTO getFormMeta() {
		return formMeta;
	}

	public void setFormMeta(FormMetaDTO formMeta) {
		this.formMeta = formMeta;
	}

	public LocalDateTime getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(LocalDateTime registerDate) {
		this.registerDate = registerDate;
	}
}