package com.enroll.rest.dto;

import com.enroll.rest.enums.RestResultEnum;

public class RestResult<T> extends RestBasicResult {

	private T data;

	public RestResult(RestResultEnum result, String nonce, T data) {
		super(result, nonce);
		this.data = data;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
}