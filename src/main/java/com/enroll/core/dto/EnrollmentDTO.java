package com.enroll.core.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.enroll.common.DateUtils;


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
	
	public List<FormFieldValueDTO[]> getPageFields() {
		List<FormFieldValueDTO[]> list = new ArrayList<FormFieldValueDTO[]>();
		if (fieldValueList.isEmpty()) {
			return list;
		}
		int index = 0;
		FormFieldValueDTO[] array = new FormFieldValueDTO[2];
		for (FormFieldValueDTO field : fieldValueList) {
			if (index > 0 && index % 2 == 0) {
				array = new FormFieldValueDTO[2];
			}
			if (index % 2 == 0) {
				list.add(array);
			}
			array[index % 2] = field;
			index++;
		}
		if (array[1] == null) {
			array[1] = new FormFieldValueDTO();
		}
		return list;
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
	
	public String getRegisterDateStr() {
		return registerDate.format(DateUtils.YYYY_MM_DD);
	}
	
	public void setRegisterDate(String modifiedDateStr) {
		registerDate = LocalDateTime.parse(modifiedDateStr, DateUtils.YYYY_MM_DD);
	}
	
	public String getModifiedDateStr() {
		return modifiedDate.format(DateUtils.YYYYMMDDHHMMSS);
	}
	
	public void setModifiedDateStr(String modifiedDateStr) {
		modifiedDate = LocalDateTime.parse(modifiedDateStr, DateUtils.YYYYMMDDHHMMSS);
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