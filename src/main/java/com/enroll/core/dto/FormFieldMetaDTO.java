package com.enroll.core.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.enroll.common.AppConstant;
import com.enroll.core.enums.FormFieldSubtype;
import com.enroll.core.enums.FormFieldType;
import com.enroll.core.enums.Options;

public class FormFieldMetaDTO implements Serializable {

	private static final long serialVersionUID = 5590060413365654998L;

	private long fieldId;

	private int position;

	private String required;

	private String label;

	private String type;

	private String subtype;
	
	private String slot;
	
	private String uniqueKey;

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
	
	public boolean isDate() {
		return FormFieldType.DATE.equals(FormFieldType.getFieldType(type));
	}
	
	public boolean isNumber() {
		return FormFieldType.NUMBER.equals(FormFieldType.getFieldType(type));
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

	public boolean isTel() {
		return FormFieldSubtype.Tel.equals(FormFieldSubtype.getFieldType(subtype));
	}
	
	public boolean hasApplicantSlot() {
		return AppConstant.TRUE.equals(getSlot());
	}
	
	public boolean isUniqueKey() {
		return AppConstant.TRUE.equals(StringUtils.trim(getUniqueKey()));
	}

	public String getCssClass() {
		StringBuilder sb = new StringBuilder();
		if (isRequired()) {
			sb.append("required ");
		}
		if (isTel()) {
			sb.append("telphone ");
		}
		if (isEmail()) {
			sb.append("email ");
		}
		return sb.toString();
	}

	public boolean isEmail() {
		return FormFieldSubtype.EMAIL.equals(FormFieldSubtype.getFieldType(subtype));
	}

	public boolean hasOptions() {
		return isCheckbox() || isRadio() || isSelect();
	}

	public String getRequired() {
		return required;
	}

	public void setRequired(String required) {
		this.required = required;
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

	public List<FormFieldOptionDTO> getOptions() {
		return options;
	}

	public void setOptions(List<FormFieldOptionDTO> options) {
		this.options = options;
	}

	public String getSubtype() {
		return subtype;
	}

	public void setSubtype(String subtype) {
		this.subtype = subtype;
	}
	
	public String getSlot() {
		return slot;
	}

	public void setSlot(String slot) {
		this.slot = slot;
	}

	public String getUniqueKey() {
		return uniqueKey;
	}

	public void setUniqueKey(String uniqueKey) {
		this.uniqueKey = uniqueKey;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((className == null) ? 0 : className.hashCode());
		result = prime * result + ((fieldConstraint == null) ? 0 : fieldConstraint.hashCode());
		result = prime * result + ((fieldDefaultValue == null) ? 0 : fieldDefaultValue.hashCode());
		result = prime * result + (int) (fieldId ^ (fieldId >>> 32));
		result = prime * result + ((formMeta == null) ? 0 : formMeta.hashCode());
		result = prime * result + ((inputFieldValue == null) ? 0 : inputFieldValue.hashCode());
		result = prime * result + ((label == null) ? 0 : label.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((options == null) ? 0 : options.hashCode());
		result = prime * result + position;
		result = prime * result + ((required == null) ? 0 : required.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		if (className == null) {
			if (other.className != null)
				return false;
		} else if (!className.equals(other.className))
			return false;
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
		if (fieldId != other.fieldId)
			return false;
		if (formMeta == null) {
			if (other.formMeta != null)
				return false;
		} else if (!formMeta.equals(other.formMeta))
			return false;
		if (inputFieldValue == null) {
			if (other.inputFieldValue != null)
				return false;
		} else if (!inputFieldValue.equals(other.inputFieldValue))
			return false;
		if (label == null) {
			if (other.label != null)
				return false;
		} else if (!label.equals(other.label))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (options == null) {
			if (other.options != null)
				return false;
		} else if (!options.equals(other.options))
			return false;
		if (position != other.position)
			return false;
		if (required == null) {
			if (other.required != null)
				return false;
		} else if (!required.equals(other.required))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "FormFieldMetaDTO [fieldId=" + fieldId + ", position=" + position + ", required=" + required + ", label="
				+ label + ", type=" + type + ", fieldConstraint=" + fieldConstraint + ", fieldDefaultValue="
				+ fieldDefaultValue + ", className=" + className + ", name=" + name + ", inputFieldValue="
				+ inputFieldValue + ", formMeta=" + formMeta + ", options=" + options + "]";
	}
}