package com.enroll.rest.dto;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotNull;

public class RestRequest implements Serializable {

	private static final long serialVersionUID = 5748159200905920457L;

	@NotNull
	private Long formId;
	
	@NotNull
	private String appId;
	
	@NotNull
	private String signature;

	private List<RestFieldValue> data;

	public List<RestFieldValue> getData() {
		return data;
	}

	public void setData(List<RestFieldValue> data) {
		this.data = data;
	}

	public Long getFormId() {
		return formId;
	}

	public void setFormId(Long formId) {
		this.formId = formId;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}
}
