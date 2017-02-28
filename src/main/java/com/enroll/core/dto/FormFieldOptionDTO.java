package com.enroll.core.dto;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

public class FormFieldOptionDTO implements Serializable {

	private static final long serialVersionUID = 4768670324483749534L;

	private int position;

	private String value;

	private String label;

	private String description;

	private String selected;

	private FormFieldMetaDTO formField;

	public FormFieldOptionDTO() {
	}

	public FormFieldOptionDTO(int position, FormFieldMetaDTO formField, String value, String display,
			String description) {
		super();
		this.position = position;
		this.formField = formField;
		this.value = value;
		this.label = display;
		this.description = description;
	}

	public boolean isChecked() {
		return StringUtils.contains(formField.getInputFieldValue(), value);
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getDisplay() {
		return this.label;
	}

	public void setDisplay(String display) {
		this.label = display;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public FormFieldMetaDTO getFormField() {
		return formField;
	}

	public void setFormField(FormFieldMetaDTO formField) {
		this.formField = formField;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((label == null) ? 0 : label.hashCode());
		result = prime * result + ((formField == null) ? 0 : formField.hashCode());
		result = prime * result + position;
		result = prime * result + ((value == null) ? 0 : value.hashCode());
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
		FormFieldOptionDTO other = (FormFieldOptionDTO) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (label == null) {
			if (other.label != null)
				return false;
		} else if (!label.equals(other.label))
			return false;
		if (formField == null) {
			if (other.formField != null)
				return false;
		} else if (!formField.equals(other.formField))
			return false;
		if (position != other.position)
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getSelected() {
		return selected;
	}

	public void setSelected(String selected) {
		this.selected = selected;
	}
}