package com.enroll.core.dto;

import java.io.Serializable;

public class FormFieldOptionDTO implements Serializable {
	
	private static final long serialVersionUID = 4768670324483749534L;

	private int position;
	
	private String value;
	
	private String display;
	
	private String description;
	
	private FormFieldMetaDTO formField;
	
	public FormFieldOptionDTO() {
	}
	
	public FormFieldOptionDTO(int position, FormFieldMetaDTO formField, String value, String display, String description) {
		super();
		this.position = position;
		this.formField = formField;
		this.value = value;
		this.display = display;
		this.description = description;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getDisplay() {
		return this.display;
	}

	public void setDisplay(String display) {
		this.display = display;
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
		result = prime * result + ((display == null) ? 0 : display.hashCode());
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
		if (display == null) {
			if (other.display != null)
				return false;
		} else if (!display.equals(other.display))
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
}