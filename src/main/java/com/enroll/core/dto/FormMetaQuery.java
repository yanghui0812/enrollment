package com.enroll.core.dto;

import java.io.Serializable;

public class FormMetaQuery implements Serializable {

	private static final long serialVersionUID = -1791723346286706414L;
	
	private long formId;
	
	private String formName;

	public long getFormId() {
		return formId;
	}

	public void setFormId(long formId) {
		this.formId = formId;
	}

	public String getFormName() {
		return formName;
	}

	public void setFormName(String formName) {
		this.formName = formName;
	}
}
