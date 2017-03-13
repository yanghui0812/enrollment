package com.enroll.rest.dto;

import com.enroll.rest.enums.RestResultEnum;

public class RestBasicResult {

	private int status;
	
	private int code;
	
	private String message;
	
	private String signature;
	
	private String nonce;

	public RestBasicResult() {
	}

	public RestBasicResult(RestResultEnum result, String nonce) {
		setMessageAndcode(result);
		this.nonce = nonce;
	}

	public void setMessageAndcode(RestResultEnum result) {
		this.code = result.getCode();
		this.message = result.getMessage();
		this.status = result.getStatus();
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getNonce() {
		return nonce;
	}

	public void setNonce(String nonce) {
		this.nonce = nonce;
	}

	@Override
	public String toString() {
		return "RestBasicResult [status=" + status + ", code=" + code + ", message=" + message + "]";
	}
}