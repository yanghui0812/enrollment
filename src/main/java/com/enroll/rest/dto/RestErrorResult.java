package com.enroll.rest.dto;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.enroll.common.AppConstant;
import com.enroll.rest.enums.RestFieldError;
import com.enroll.rest.enums.RestResultEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class RestErrorResult extends RestBasicResult {
	
	@JsonIgnore
	public static final RestErrorResult SIGNATURE_ERROR_RESULT = new RestErrorResult(RestResultEnum.MALFORMED, Arrays.asList(new PropertyError(RestFieldError.INVALID_VALUE, "signature")));
	
	@JsonIgnore
	private List<PropertyError> fieldErrors;
	
	public RestErrorResult() {
		super();
	}
	
	public RestErrorResult(RestResultEnum result, String nonce) {
		super(result, nonce);
	}
	
	public RestErrorResult(RestResultEnum result, List<PropertyError> fieldErrors) {
		super(result, StringUtils.EMPTY);
		this.fieldErrors = fieldErrors;
	}
	
	public RestErrorResult(RestResultEnum result, List<PropertyError> fieldErrors, String nonce) {
		super(result, nonce);
		this.fieldErrors = fieldErrors;
	}

	public List<PropertyError> getFieldErrors() {
		return fieldErrors;
	}

	public void setFieldErrors(List<PropertyError> fieldErrors) {
		this.fieldErrors = fieldErrors;
	}

	public String getFieldErrorMessage() {
		if (CollectionUtils.isNotEmpty(fieldErrors)) {
			return fieldErrors.stream().map(error -> error.toString()).collect(Collectors.joining(AppConstant.COMMA));
		}
		return StringUtils.EMPTY;
	}
}