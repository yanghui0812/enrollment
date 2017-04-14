package com.enroll.core.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.enroll.core.enums.FormFieldType;

@Entity
@Table(name = "TBL_FORM_FIELD_VALUE")
public class FormFieldValue implements Serializable {

	private static final long serialVersionUID = 5411862982785736942L;

	@Id
	@Column(name = "FIELD_ID", nullable = false)
	private long fieldId;

	@Id
	@Column(name = "FORM_ID", nullable = false)
	private long formId;

	@Id
	@ManyToOne
	@JoinColumn(name = "REGISTER_ID", nullable = false)
	private Enrollment enrollment;

	@Column(name = "FIELD_VALUE", nullable = false)
	private String fieldValue;

	@Column(name = "FIELD_NAME", nullable = false)
	private String fieldName;

	@Column(name = "FIELD_LABEL", nullable = false)
	private String label;

	@Column(name = "FIELD_DISPLAY", nullable = false)
	private String fieldDisplay;

	@Column(name = "FIELD_TYPE", nullable = false)
	private String fieldtype;

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

	public Enrollment getEnrollment() {
		return enrollment;
	}

	public void setEnrollment(Enrollment enrollment) {
		this.enrollment = enrollment;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getFieldDisplay() {
		return fieldDisplay;
	}

	public void setFieldDisplay(String fieldDisplay) {
		this.fieldDisplay = fieldDisplay;
	}

	public String getFieldtype() {
		return fieldtype;
	}

	public void setFieldtype(String fieldtype) {
		this.fieldtype = fieldtype;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public boolean isText() {
		return FormFieldType.TEXT.equals(FormFieldType.getFieldType(fieldtype));
	}

	public boolean isSelect() {
		return FormFieldType.SELECT.equals(FormFieldType.getFieldType(fieldtype));
	}

	public boolean isCheckbox() {
		return FormFieldType.CHECKBOX.equals(FormFieldType.getFieldType(fieldtype));
	}

	public boolean isRadio() {
		return FormFieldType.RADIO.equals(FormFieldType.getFieldType(fieldtype));
	}

	public boolean isTextarea() {
		return FormFieldType.TEXTAREA.equals(FormFieldType.getFieldType(fieldtype));
	}

	public boolean isCheckboxGroup() {
		return FormFieldType.CHECKBOX_GROUP.equals(FormFieldType.getFieldType(fieldtype));
	}

	public boolean isRadioGroup() {
		return FormFieldType.RADIO_GROUP.equals(FormFieldType.getFieldType(fieldtype));
	}

	public boolean hasOptions() {
		return isCheckbox() || isRadio() || isSelect();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((enrollment == null) ? 0 : enrollment.hashCode());
		result = prime * result + (int) (fieldId ^ (fieldId >>> 32));
		result = prime * result + (int) (formId ^ (formId >>> 32));
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
		if (enrollment == null) {
			if (other.enrollment != null)
				return false;
		} else if (!enrollment.equals(other.enrollment))
			return false;
		if (fieldId != other.fieldId)
			return false;
		if (formId != other.formId)
			return false;
		return true;
	}
}