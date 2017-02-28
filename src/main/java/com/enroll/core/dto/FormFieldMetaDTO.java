package com.enroll.core.dto;

import java.io.Serializable;
import java.util.ArrayList;
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

	private List<FormFieldOptionDTO> options = new ArrayList<>();

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

	public void addFieldOption(FormFieldOptionDTO dto) {
		if (options == null) {
			options = new ArrayList<FormFieldOptionDTO>();
		}
		dto.setFormField(this);
		options.add(dto);
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

	public boolean isCheckboxGroup() {
		return FormFieldType.CHECKBOX_GROUP.equals(FormFieldType.getFieldType(type));
	}

	public boolean isRadioGroup() {
		return FormFieldType.RADIO_GROUP.equals(FormFieldType.getFieldType(type));
	}

	public boolean hasOptions() {
		return isCheckbox() || isRadio() || isSelect();
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

	public String getRequired() {
		return required;
	}

	public void setRequired(String required) {
		this.required = required;
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

	public List<FormFieldOptionDTO> getOptions() {
		return options;
	}

	public void setOptions(List<FormFieldOptionDTO> options) {
		this.options = options;
	}
}