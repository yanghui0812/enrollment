package com.enroll.rest.dto;

import com.enroll.rest.enums.RestResultEnum;

public class RestBasicResult {

	private int status;
	private int code;
	private String message;

	public RestBasicResult() {
	}

	public RestBasicResult(RestResultEnum result) {
		this.code = result.getCode();
		this.message = result.getReasonPhrase();
	}

	public void setMessageAndcode(RestResultEnum result) {
		this.code = result.getCode();
		this.message = result.getReasonPhrase();
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

	@Override
	public String toString() {
		return "RestBasicResult [status=" + status + ", code=" + code + ", message=" + message + "]";
	}
}