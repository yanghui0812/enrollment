package com.enroll.rest.dto;

import java.util.List;

import javax.validation.constraints.NotNull;

public class RestRequest {
	
	@NotNull
	private String formId;
	
	@NotNull
	private String appId;
	
	@NotNull
	private String signature;
	
	private String nonce;

	private List<RestFieldValue> data;

	public List<RestFieldValue> getData() {
		return data;
	}

	public void setData(List<RestFieldValue> data) {
		this.data = data;
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

	public String getFormId() {
		return formId;
	}

	public void setFormId(String formId) {
		this.formId = formId;
	}

	public String getNonce() {
		return nonce;
	}

	public void setNonce(String nonce) {
		this.nonce = nonce;
	}
}