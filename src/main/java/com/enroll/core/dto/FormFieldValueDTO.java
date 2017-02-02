package com.enroll.core.dto;

import java.io.Serializable;

public class FormFieldValueDTO implements Serializable {
	
	private static final long serialVersionUID = 6679862982785736942L;

	private long formId;
	
	private long fieldId;
	
	private String registrId;
	
	private String fieldValue;

	public FormFieldValueDTO() {
	}

	public FormFieldValueDTO(int fieldId) {
		this.fieldId = fieldId;
	}
	
	public FormFieldValueDTO(long formId, long fieldId, String registrId, String fieldValue) {
		super();
		this.formId = formId;
		this.fieldId = fieldId;
		this.registrId = registrId;
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

	public String getRegistrId() {
		return registrId;
	}

	public void setRegistrId(String registrId) {
		this.registrId = registrId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (fieldId ^ (fieldId >>> 32));
		result = prime * result + ((fieldValue == null) ? 0 : fieldValue.hashCode());
		result = prime * result + (int) (formId ^ (formId >>> 32));
		result = prime * result + ((registrId == null) ? 0 : registrId.hashCode());
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
		if (registrId == null) {
			if (other.registrId != null)
				return false;
		} else if (!registrId.equals(other.registrId))
			return false;
		return true;
	}
}