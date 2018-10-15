package com.enroll.core.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.BatchSize;

import com.enroll.security.entity.AbstractEntity;

@Entity
@Table(name = "TBL_FORM_META")
public class FormMeta extends AbstractEntity implements Serializable {

	private static final long serialVersionUID = -5313989726810258220L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "FORM_ID", nullable = false)
	private long formId;

	@Column(name = "FORM_NAME", nullable = false)
	private String formName;

	@Column(name = "FORM_DESCRIPTION", nullable = false)
	private String formDescription;
	
	@Column(name = "STATUS")
	private String status;
	
	@Column(name = "RAW_JSON")
	private String rawJson;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "formMeta")
	@BatchSize(size = 100)
	private List<FormFieldMeta> formFieldMetaList = new ArrayList<FormFieldMeta>();

	public FormMeta() {
	}

	public FormMeta(int formId) {
		this.formId = formId;
	}

	public FormMeta(long formId, String formName, String formDescription) {
		this.formId = formId;
		this.formName = formName;
		this.formDescription = formDescription;
	}

	public long getFormId() {
		return this.formId;
	}

	public void setFormId(long formId) {
		this.formId = formId;
	}

	public String getFormName() {
		return this.formName;
	}

	public void setFormName(String formName) {
		this.formName = formName;
	}

	public String getFormDescription() {
		return this.formDescription;
	}

	public void setFormDescription(String formDescription) {
		this.formDescription = formDescription;
	}

	public List<FormFieldMeta> getFormFieldMetaList() {
		return formFieldMetaList;
	}

	public void setFormFieldMetaList(List<FormFieldMeta> formFieldMetaList) {
		this.formFieldMetaList = formFieldMetaList;
	}
	
	public void addFormFieldMeta(FormFieldMeta formFieldMeta) {
		formFieldMeta.setFormMeta(this);
		formFieldMetaList.add(formFieldMeta);
	}
	
	
	public Map<String, FormFieldMeta> getFormFieldMetaMap() {
		Map<String, FormFieldMeta> formFieldMap = new HashMap<>();
		getFormFieldMetaList().stream().forEach(formField -> {
			formFieldMap.put(StringUtils.trim(formField.getName()), formField);
		});
		return formFieldMap;
	}
	
	public Map<String, FormFieldMeta> getFormFieldIgnoreCaseMetaMap() {
		Map<String, FormFieldMeta> formFieldMap = new HashMap<>();
		getFormFieldMetaList().stream().forEach(formField -> {
			formFieldMap.put(StringUtils.lowerCase(StringUtils.trim(formField.getName())), formField);
		});
		return formFieldMap;
	}
	
	public void clearAllFormFieldMeta() {
		formFieldMetaList.stream().forEach(formField -> {
			formField.setFormMeta(null);
		});
		formFieldMetaList.clear();
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRawJson() {
		return rawJson;
	}

	public void setRawJson(String rawJson) {
		this.rawJson = rawJson;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (formId ^ (formId >>> 32));
		result = prime * result + ((formName == null) ? 0 : formName.hashCode());
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
		FormMeta other = (FormMeta) obj;
		if (formId != other.formId)
			return false;
		if (formName == null) {
			if (other.formName != null)
				return false;
		} else if (!formName.equals(other.formName))
			return false;
		return true;
	}
}