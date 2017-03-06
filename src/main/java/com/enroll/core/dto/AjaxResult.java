package com.enroll.core.dto;

import com.enroll.core.enums.AjaxResultStatus;

public class AjaxResult<T> {

	private String status;
	private String message;
	private T data;

	public AjaxResult() {
	}
	
	public AjaxResult(AjaxResultStatus status) {
		this.status = status.getType();
		this.message = status.getDesc();
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getData() {
		return data;
	}
	
	public void setAjaxResultStatus(AjaxResultStatus status) {
		this.status = status.getType();
		this.message = status.getDesc();
	}

	public void setData(T data) {
		this.data = data;
	}
}
