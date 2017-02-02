package com.enroll.core.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TBL_FORM_FIELD_VALUE" )
public class FormFieldValue implements Serializable {
	
	private static final long serialVersionUID = 5411862982785736942L;
	
	@Id
	@Column(name = "FIELD_ID", nullable = false)
	private long fieldId;

	@Id
	@Column(name = "FORM_ID", nullable = false)
	private long formId;
	
	@Id
	@Column(name = "ID_NUMBER", nullable = false)
	private String idNumber;
	
	@Column(name = "FIELD_VALUE", nullable = false)
	private String fieldValue;

	public FormFieldValue() {
	}

	public FormFieldValue(int fieldId) {
		this.fieldId = fieldId;
	}

	public FormFieldValue(int fieldId, String fieldValue) {
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
		FormFieldValue other = (FormFieldValue) obj;
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