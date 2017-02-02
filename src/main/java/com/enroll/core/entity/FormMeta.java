package com.enroll.core.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@Table(name = "TBL_FORM_META")
public class FormMeta implements Serializable {

	private static final long serialVersionUID = -5313989726810258220L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "FORM_ID", nullable = false)
	private long formId;

	@Column(name = "FORM_NAME", nullable = false)
	private String formName;

	@Column(name = "FORM_DESCRIPTION", nullable = false)
	private String formDescription;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "formMeta")
	private List<FormFieldMeta> formFieldMetaList = new ArrayList<FormFieldMeta>();

	@Column(name = "CREATED_TIMESTAMP", updatable = false)
	private Date createTimestamp;

	@Version
	@Column(name = "UPDATED_TIMESTAMP")
	private Date modifyTimestamp;

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
	
	public void addFormFieldMeta(FormFieldMeta dto) {
		dto.setFormMeta(this);
		formFieldMetaList.add(dto);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((formDescription == null) ? 0 : formDescription.hashCode());
		result = prime * result + ((formFieldMetaList == null) ? 0 : formFieldMetaList.hashCode());
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
		if (formDescription == null) {
			if (other.formDescription != null)
				return false;
		} else if (!formDescription.equals(other.formDescription))
			return false;
		if (formFieldMetaList == null) {
			if (other.formFieldMetaList != null)
				return false;
		} else if (!formFieldMetaList.equals(other.formFieldMetaList))
			return false;
		if (formId != other.formId)
			return false;
		if (formName == null) {
			if (other.formName != null)
				return false;
		} else if (!formName.equals(other.formName))
			return false;
		return true;
	}

	public Date getCreateTimestamp() {
		return createTimestamp;
	}

	public void setCreateTimestamp(Date createTimestamp) {
		this.createTimestamp = createTimestamp;
	}

	public Date getModifyTimestamp() {
		return modifyTimestamp;
	}

	public void setModifyTimestamp(Date modifyTimestamp) {
		this.modifyTimestamp = modifyTimestamp;
	}
}