package com.enroll.core.dto;

import java.io.Serializable;

import com.enroll.core.enums.FormFieldType;

public class FormFieldValueDTO implements Serializable {
	
	private static final long serialVersionUID = 6679862982785736942L;

	private long formId;
	
	private long fieldId;
	
	private String registerId;
	
	private String fieldValue;
	
	private String fieldName;
	
	private String fieldDisplay;
	
	private String fieldtype;

	public FormFieldValueDTO() {
	}

	public FormFieldValueDTO(int fieldId) {
		this.fieldId = fieldId;
	}
	
	public FormFieldValueDTO(long formId, long fieldId, String registrId, String fieldValue) {
		super();
		this.formId = formId;
		this.fieldId = fieldId;
		this.registerId = registrId;
		this.fieldValue = fieldValue;
	}

	public FormFieldValueDTO(int fieldId, String fieldValue) {
		this.fieldId = fieldId;
		this.fieldValue = fieldValue;
	}

	public long getFieldId() {
		return this.fieldId;
	}

	public void setFieldId(int fieldId) {
		this.fieldId = fieldId;
	}

	public String getFieldValue() {
		return this.fieldValue;
	}

	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}

	public long getFormId() {
		return formId;
	}

	public void setFormId(long formId) {
		this.formId = formId;
	}

	public void setFieldId(long fieldId) {
		this.fieldId = fieldId;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getRegisterId() {
		return registerId;
	}

	public void setRegisterId(String registerId) {
		this.registerId = registerId;
	}
	

	public String getFieldDisplay() {
		return fieldDisplay;
	}

	public void setFieldDisplay(String fieldDisplay) {
		this.fieldDisplay = fieldDisplay;
	}
	
	public String getValue() {
		if (FormFieldType.hasOption(fieldtype)) {
			return this.fieldDisplay;
		}
		return fieldValue;
	}

	public String getFieldtype() {
		return fieldtype;
	}

	public void setFieldtype(String fieldtype) {
		this.fieldtype = fieldtype;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (fieldId ^ (fieldId >>> 32));
		result = prime * result + ((fieldName == null) ? 0 : fieldName.hashCode());
		result = prime * result + (int) (formId ^ (formId >>> 32));
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
		FormFieldValueDTO other = (FormFieldValueDTO) obj;
		if (fieldId != other.fieldId)
			return false;
		if (fieldName == null) {
			if (other.fieldName != null)
				return false;
		} else if (!fieldName.equals(other.fieldName))
			return false;
		if (formId != other.formId)
			return false;
		if (registerId == null) {
			if (other.registerId != null)
				return false;
		} else if (!registerId.equals(other.registerId))
			return false;
		return true;
	}
}