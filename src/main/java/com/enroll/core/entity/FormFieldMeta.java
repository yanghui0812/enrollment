package com.enroll.core.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "TBL_FORM_FIELD_META" )
public class FormFieldMeta implements Serializable {
	
	private static final long serialVersionUID = 4474060413365654998L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "FIELD_ID")
	private long fieldId;
	
	@Column(name = "FIELD_POSITION", nullable = false)
	private int position;
	
	@Column(name = "REQUIRED", nullable = false)
	private String required;
	
	@ManyToOne
	@JoinColumn(name = "FORM_ID", nullable = false)
	private FormMeta formMeta;
	
	@Column(name = "FIELD_NAME", nullable = false)
	private String name;
	
	@Column(name = "FIELD_LABEL", nullable = false)
	private String label;
	
	@Column(name = "FIELD_TYPE", nullable = false)
	private String type;
	
	@Column(name = "FIELD_SUBTYPE", nullable = false)
	private String subtype;
	
	@Column(name = "FIELD_IS_SLOT", nullable = false)
	private String slot;
	
	@Column(name = "IS_UNIQUE_KEY", nullable = false) 
	private String uniqueKey;
	
	@Column(name = "FIELD_CONSTRAINT", nullable = false)
	private String fieldConstraint;
	
	@Column(name = "FIELD_DEFAULT_VALUE", nullable = false)
	private String fieldDefaultValue;
	
	@Column(name = "FIELD_STYLE", nullable = false)
	private String fieldStyle;
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "formField", fetch = FetchType.LAZY)
	private List<FormFieldOption> fieldOptionList = new ArrayList<FormFieldOption>();	

	public FormFieldMeta() {
	}

	public FormFieldMeta(long fieldId, FormMeta formMeta, String fieldName, String fieldType, int fieldPosition) {
		this.fieldId = fieldId;
		this.formMeta = formMeta;
		this.name = fieldName;
		this.type = fieldType;
		this.position = fieldPosition;
	}

	public FormFieldMeta(long fieldId, FormMeta formMeta, String fieldName, String fieldType, String fieldConstraint,
			int fieldPosition, String fieldDefaultValue, String fieldStyle) {
		this.fieldId = fieldId;
		this.formMeta = formMeta;
		this.name = fieldName;
		this.type = fieldType;
		this.fieldConstraint = fieldConstraint;
		this.position = fieldPosition;
		this.fieldDefaultValue = fieldDefaultValue;
		this.fieldStyle = fieldStyle;
	}

	public long getFieldId() {
		return this.fieldId;
	}

	public void setFieldId(long fieldId) {
		this.fieldId = fieldId;
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

	public FormMeta getFormMeta() {
		return formMeta;
	}

	public void setFormMeta(FormMeta formMeta) {
		this.formMeta = formMeta;
	}

	public Collection<FormFieldOption> getFieldOptionList() {
		return Collections.unmodifiableCollection(fieldOptionList);
	}
	
	public void clearFieldOption() {
		fieldOptionList.clear();
	}
	
	public Map<String, String> getFieldOptionMap() {
		Map<String, String> map = new HashMap<>();
		fieldOptionList.stream().forEach(option -> {
			map.put(option.getValue(), option.getLabel());
		});
		return map;
	}
	
	public Map<String, FormFieldOption> getFieldOptionMetaMap() {
		Map<String, FormFieldOption> map = new HashMap<>();
		fieldOptionList.stream().forEach(option -> {
			map.put(option.getLabel(), option);
		});
		return map;
	}
	
	public int getSizeOfFieldOptions() {
		return fieldOptionList.size();
	}
	
	public void addFormFieldOption(FormFieldOption dto) {
		dto.setFormField(this);
		fieldOptionList.add(dto);
	}
	
	public boolean hasApplicantSlot() {
		return "true".equals(getSlot());
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
		result = prime * result + ((formMeta == null) ? 0 : formMeta.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		FormFieldMeta other = (FormFieldMeta) obj;
		if (formMeta == null) {
			if (other.formMeta != null)
				return false;
		} else if (!formMeta.equals(other.formMeta))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}