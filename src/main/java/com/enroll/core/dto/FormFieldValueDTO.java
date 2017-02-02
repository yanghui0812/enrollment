package com.enroll.core.dto;

import java.io.Serializable;

public class FormFieldValueDTO implements Serializable {
	
	private static final long serialVersionUID = 6679862982785736942L;

	private long formId;
	
	private long fieldId;
	
	private String idNumber;
	
	private String fieldValue;

	public FormFieldValueDTO() {
	}

	public FormFieldValueDTO(int fieldId) {
		this.fieldId = fieldId;
	}
	
	public FormFieldValueDTO(long formId, long fieldId, String idNumber, String fieldValue) {
		super();
		this.formId = formId;
		this.fieldId = fieldId;
		this.idNumber = idNumber;
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

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (fieldId ^ (fieldId >>> 32));
		result = prime * result + ((fieldValue == null) ? 0 : fieldValue.hashCode());
		result = prime * result + (int) (formId ^ (formId >>> 32));
		result = prime * result + ((idNumber == null) ? 0 : idNumber.hashCode());
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
		if (fieldValue == null) {
			if (other.fieldValue != null)
				return false;
		} else if (!fieldValue.equals(other.fieldValue))
			return false;
		if (formId != other.formId)
			return false;
		if (idNumber == null) {
			if (other.idNumber != null)
				return false;
		} else if (!idNumber.equals(other.idNumber))
			return false;
		return true;
	}
}