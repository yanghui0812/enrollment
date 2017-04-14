package com.enroll.rest.dto;

import org.apache.commons.lang3.StringUtils;

import com.enroll.rest.enums.RestFieldError;

public class PropertyError {
	
	private int errorcode;
	private String field;
	private String message;
	
	public PropertyError() {
		super();
	}
	
	public PropertyError(RestFieldError fieldError, String fieldName) {
		this.errorcode = fieldError.getCode();
		this.message = StringUtils.replace(fieldError.getDesc(), "<字段名字>", fieldName);
	}
	
	public PropertyError(String field, int errorcode, String message) {
		this.field = field;
		this.errorcode = errorcode;
		this.message = message;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public int getErrorcode() {
		return errorcode;
	}

	public void setErrorcode(int errorcode) {
		this.errorcode = errorcode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "PropertyError [errorcode=" + errorcode + ", field=" + field + ", message=" + message + "]";
	}
}