package com.enroll.core.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FormFieldMetaDTO implements Serializable {
	
	private static final long serialVersionUID = 5590060413365654998L;

	private long fieldId;

	private FormMetaDTO formMeta;
	
	private String fieldName;
	
	private String fieldType;
	
	private String fieldConstraint;
	
	private int position;
	
	private String fieldDefaultValue;
	
	private String fieldStyle;
	
	private String fieldValue;
	
	private List<FormFieldOptionDTO> fieldOptionList;

	public FormFieldMetaDTO() {
	}

	public FormFieldMetaDTO(long fieldId, FormMetaDTO formMeta, String fieldName, String fieldType, int position) {
		this.fieldId = fieldId;
		this.formMeta = formMeta;
		this.fieldName = fieldName;
		this.fieldType = fieldType;
		this.position = position;
	}

	public FormFieldMetaDTO(long fieldId, FormMetaDTO formMeta, String fieldName, String fieldType, String fieldConstraint,
			int position, String fieldDefaultValue, String fieldStyle) {
		this.fieldId = fieldId;
		this.formMeta = formMeta;
		this.fieldName = fieldName;
		this.fieldType = fieldType;
		this.fieldConstraint = fieldConstraint;
		this.position = position;
		this.fieldDefaultValue = fieldDefaultValue;
		this.fieldStyle = fieldStyle;
	}

	public long getFieldId() {
		return this.fieldId;
	}

	public void setFieldId(long fieldId) {
		this.fieldId = fieldId;
	}
	
	public String getFieldName() {
		return this.fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getFieldType() {
		return this.fieldType;
	}

	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}

	public String getFieldConstraint() {
		return this.fieldConstraint;
	}

	public void setFieldConstraint(String fieldConstraint) {
		this.fieldConstraint = fieldConstraint;
	}

	public String getFieldDefaultValue() {
		return this.fieldDefaultValue;
	}

	public void setFieldDefaultValue(String fieldDefaultValue) {
		this.fieldDefaultValue = fieldDefaultValue;
	}

	public String getFieldStyle() {
		return this.fieldStyle;
	}

	public void setFieldStyle(String fieldStyle) {
		this.fieldStyle = fieldStyle;
	}

	public FormMetaDTO getFormMeta() {
		return formMeta;
	}

	public void setFormMeta(FormMetaDTO formMeta) {
		this.formMeta = formMeta;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public List<FormFieldOptionDTO> getFieldOptionList() {
		return fieldOptionList;
	}
	
	public void addFieldOption(FormFieldOptionDTO dto) {
		if (fieldOptionList == null) {
			fieldOptionList = new ArrayList<FormFieldOptionDTO>();
		}
		fieldOptionList.add(dto);
	}

	public String getFieldValue() {
		return fieldValue;
	}

	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fieldConstraint == null) ? 0 : fieldConstraint.hashCode());
		result = prime * result + ((fieldDefaultValue == null) ? 0 : fieldDefaultValue.hashCode());
		result = prime * result + ((fieldName == null) ? 0 : fieldName.hashCode());
		result = prime * result + ((fieldOptionList == null) ? 0 : fieldOptionList.hashCode());
		result = prime * result + ((fieldStyle == null) ? 0 : fieldStyle.hashCode());
		result = prime * result + ((fieldType == null) ? 0 : fieldType.hashCode());
		result = prime * result + ((formMeta == null) ? 0 : formMeta.hashCode());
		result = prime * result + position;
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
		FormFieldMetaDTO other = (FormFieldMetaDTO) obj;
		if (fieldConstraint == null) {
			if (other.fieldConstraint != null)
				return false;
		} else if (!fieldConstraint.equals(other.fieldConstraint))
			return false;
		if (fieldDefaultValue == null) {
			if (other.fieldDefaultValue != null)
				return false;
		} else if (!fieldDefaultValue.equals(other.fieldDefaultValue))
			return false;
		if (fieldName == null) {
			if (other.fieldName != null)
				return false;
		} else if (!fieldName.equals(other.fieldName))
			return false;
		if (fieldOptionList == null) {
			if (other.fieldOptionList != null)
				return false;
		} else if (!fieldOptionList.equals(other.fieldOptionList))
			return false;
		if (fieldStyle == null) {
			if (other.fieldStyle != null)
				return false;
		} else if (!fieldStyle.equals(other.fieldStyle))
			return false;
		if (fieldType == null) {
			if (other.fieldType != null)
				return false;
		} else if (!fieldType.equals(other.fieldType))
			return false;
		if (formMeta == null) {
			if (other.formMeta != null)
				return false;
		} else if (!formMeta.equals(other.formMeta))
			return false;
		if (position != other.position)
			return false;
		return true;
	}

	public void setFieldOptionList(List<FormFieldOptionDTO> fieldOptionList) {
		this.fieldOptionList = fieldOptionList;
	}
}