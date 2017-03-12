package com.enroll.rest.dto;

import java.util.List;

import com.enroll.rest.enums.RestResultEnum;

public class RestErrorResult extends RestBasicResult {

	public RestErrorResult() {
		super();
	}
	
	public RestErrorResult(RestResultEnum result) {
		super(result);
	}

	private List<PropertyError> fieldErrors;

	public List<PropertyError> getFieldErrors() {
		return fieldErrors;
	}

	public void setFieldErrors(List<PropertyError> fieldErrors) {
		this.fieldErrors = fieldErrors;
	}
}