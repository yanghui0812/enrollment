package com.enroll.core.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.enroll.core.enums.FormFieldType;
import com.enroll.core.enums.Options;

public class FormFieldMetaDTO implements Serializable {

	private static final long serialVersionUID = 5590060413365654998L;

	private long fieldId;

	private int position;

	private String required;

	private String label;

	private String type;

	private String fieldConstraint;

	private String fieldDefaultValue;

	private String className;
	
	private String name;

	private String inputFieldValue;

	private FormMetaDTO formMeta;

	private List<FormFieldOptionDTO> values;

	public FormFieldMetaDTO() {
	}

	public FormFieldMetaDTO(long fieldId, FormMetaDTO formMeta, String fieldName, String fieldType, int position) {
		this.fieldId = fieldId;
		this.formMeta = formMeta;
		this.label = fieldName;
		this.type = fieldType;
		this.position = position;
	}

	public FormFieldMetaDTO(long fieldId, FormMetaDTO formMeta, String fieldName, String fieldType,
			String fieldConstraint, int position, String fieldDefaultValue, String fieldStyle) {
		this.fieldId = fieldId;
		this.formMeta = formMeta;
		this.label = fieldName;
		this.type = fieldType;
		this.fieldConstraint = fieldConstraint;
		this.position = position;
		this.fieldDefaultValue = fieldDefaultValue;
		this.className = fieldStyle;
	}
	
	public List<FormFieldOptionDTO> getFieldOptionList() {
		return values;
	}

	public List<FormFieldOptionDTO> getSortedFieldOptions() {
		values.sort(Comparator.comparingInt(FormFieldOptionDTO::getPosition));
		return values;
	}

	public void addFieldOption(FormFieldOptionDTO dto) {
		if (values == null) {
			values = new ArrayList<FormFieldOptionDTO>();
		}
		dto.setFormField(this);
		values.add(dto);
	}

	public String getRequired() {
		return required;
	}

	public void setRequired(String required) {
		this.required = required;
	}

	public boolean isRequired() {
		return Options.TRUE.equals(Options.getOption(required));
	}

	public boolean isText() {
		return FormFieldType.TEXT.equals(FormFieldType.getFieldType(type));
	}

	public boolean isSelect() {
		return FormFieldType.SELECT.equals(FormFieldType.getFieldType(type));
	}

	public boolean isCheckbox() {
		return FormFieldType.CHECKBOX.equals(FormFieldType.getFieldType(type));
	}

	public boolean isRadio() {
		return FormFieldType.RADIO.equals(FormFieldType.getFieldType(type));
	}

	public boolean isTextarea() {
		return FormFieldType.TEXTAREA.equals(FormFieldType.getFieldType(type));
	}

	public boolean hasOptions() {
		return isCheckbox() || isRadio() || isSelect();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fieldConstraint == null) ? 0 : fieldConstraint.hashCode());
		result = prime * result + ((fieldDefaultValue == null) ? 0 : fieldDefaultValue.hashCode());
		result = prime * result + ((label == null) ? 0 : label.hashCode());
		result = prime * result + ((values == null) ? 0 : values.hashCode());
		result = prime * result + ((className == null) ? 0 : className.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		if (label == null) {
			if (other.label != null)
				return false;
		} else if (!label.equals(other.label))
			return false;
		if (values == null) {
			if (other.values != null)
				return false;
		} else if (!values.equals(other.values))
			return false;
		if (className == null) {
			if (other.className != null)
				return false;
		} else if (!className.equals(other.className))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
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

	public long getFieldId() {
		return fieldId;
	}

	public void setFieldId(long fieldId) {
		this.fieldId = fieldId;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getFieldConstraint() {
		return fieldConstraint;
	}

	public void setFieldConstraint(String fieldConstraint) {
		this.fieldConstraint = fieldConstraint;
	}

	public String getFieldDefaultValue() {
		return fieldDefaultValue;
	}

	public void setFieldDefaultValue(String fieldDefaultValue) {
		this.fieldDefaultValue = fieldDefaultValue;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getInputFieldValue() {
		return inputFieldValue;
	}

	public void setInputFieldValue(String inputFieldValue) {
		this.inputFieldValue = inputFieldValue;
	}

	public FormMetaDTO getFormMeta() {
		return formMeta;
	}

	public void setFormMeta(FormMetaDTO formMeta) {
		this.formMeta = formMeta;
	}

	public void setValues(List<FormFieldOptionDTO> values) {
		this.values = values;
	}
}