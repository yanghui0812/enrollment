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

	@Column(name = "VALUE", nullable = false)
	private String value;

	@Column(name = "DISPLAY", nullable = false)
	private String display;

	@Column(name = "DESCRIPTION", nullable = false)
	private String description;

	public FormFieldOption() {
	}

	public FormFieldOption(int position, FormFieldMeta formField, String value, String display, String description) {
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
}