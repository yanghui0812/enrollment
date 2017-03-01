package com.enroll.core.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FormMetaDTO implements Serializable {

	private static final long serialVersionUID = -5319832726810258220L;

	private long formId;

	private String formName;

	private String formDescription;

	private String status;

	private String rawJson;

	private List<FormFieldMetaDTO> fields = new ArrayList<FormFieldMetaDTO>();

	public FormMetaDTO() {
	}

	public FormMetaDTO(int formId) {
		this.formId = formId;
	}

	public FormMetaDTO(long formId, String formName, String formDescription) {
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

	public List<FormFieldMetaDTO> getFields() {
		return fields;
	}

	public void addFormFieldMeta(FormFieldMetaDTO dto) {
		fields.add(dto);
	}

	public List<FormFieldMetaDTO[]> getPageFields() {
		List<FormFieldMetaDTO[]> list = new ArrayList<FormFieldMetaDTO[]>();
		if (fields.isEmpty()) {
			return list;
		}
		int index = 0;
		FormFieldMetaDTO[] array = new FormFieldMetaDTO[2];
		for (FormFieldMetaDTO field : fields) {
			if (index > 0 && index % 2 == 0) {
				array = new FormFieldMetaDTO[2];
			}
			if (index % 2 == 0) {
				list.add(array);
			}
			array[index % 2] = field;
			index++;
		}
		if (array[1] == null) {
			array[1] = new FormFieldMetaDTO();
		}
		return list;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setFields(List<FormFieldMetaDTO> fields) {
		this.fields = fields;
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
		result = prime * result + ((formDescription == null) ? 0 : formDescription.hashCode());
		result = prime * result + ((fields == null) ? 0 : fields.hashCode());
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
		FormMetaDTO other = (FormMetaDTO) obj;
		if (formDescription == null) {
			if (other.formDescription != null)
				return false;
		} else if (!formDescription.equals(other.formDescription))
			return false;
		if (fields == null) {
			if (other.fields != null)
				return false;
		} else if (!fields.equals(other.fields))
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
}