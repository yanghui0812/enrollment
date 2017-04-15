package com.enroll.core.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "TBL_FORM_FIELD_OPTION")
public class FormFieldOption implements Serializable {

	private static final long serialVersionUID = 3730670324483749534L;
	
	@Id
	@Column(name = "POSITION", nullable = false)
	private int position;

	@Id
	@ManyToOne
	@JoinColumn(name = "FIELD_ID", nullable = false)
	private FormFieldMeta formField;

	@Id
	@Column(name = "VALUE", nullable = false)
	private String value;

	@Id
	@Column(name = "LABEL", nullable = false)
	private String label;
	
	@Column(name = "SLOT", nullable = false)
	private String slot;
	
	@Column(name = "SELECTED", nullable = false)
	private String selected;

	@Column(name = "DESCRIPTION", nullable = false)
	private String description;

	public FormFieldOption() {
	}

	public FormFieldOption(int position, FormFieldMeta formField, String value, String display, String description) {
		super();
		this.position = position;
		this.formField = formField;
		this.value = value;
		this.label = display;
		this.description = description;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public FormFieldMeta getFormField() {
		return formField;
	}

	public void setFormField(FormFieldMeta formField) {
		this.formField = formField;
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

	public String getSelected() {
		return selected;
	}

	public void setSelected(String selected) {
		this.selected = selected;
	}

	public String getSlot() {
		return slot;
	}

	public void setSlot(String slot) {
		this.slot = slot;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((formField == null) ? 0 : formField.hashCode());
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
		FormFieldOption other = (FormFieldOption) obj;
		if (formField == null) {
			if (other.formField != null)
				return false;
		} else if (!formField.equals(other.formField))
			return false;
		if (position != other.position)
			return false;
		return true;
	}
}