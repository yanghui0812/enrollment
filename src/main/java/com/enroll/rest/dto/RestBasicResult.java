package com.enroll.rest.dto;

import com.enroll.rest.enums.RestResultEnum;

public class RestBasicResult {

	private int httpStatus;
	
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
		this.httpStatus = result.getStatus();
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
	
	public int getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(int httpStatus) {
		this.httpStatus = httpStatus;
	}

	@Override
	public String toString() {
		return "RestBasicResult [status=" + httpStatus + ", code=" + code + ", message=" + message + "]";
	}
}