package com.enroll.rest.enums;

public enum RestResultEnum {

	SUCCESS(1000, 200, "请求成功"), 
	CREATE_SUCCESS(1000, 201, "请求成功"), 
	UNKNOWN_ERROR(1001, 500, "未知的内部错误"), 
	MALFORMED(1002, 400, "错误的请求"),
	ENROLL_NOT_EXIST(1003, 404, "报名信息不存在！"),
	SIGNATURE_FAIL(1008, 400, "签名校验失败");

	private final int code;
	
	private final int status;

	private final String message;

	private RestResultEnum(int code, int status, String message) {
		this.code = code;
		this.status = status;
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	public int getStatus() {
		return status;
	}

	@Override
	public String toString() {
		return Integer.toString(code);
	}

	public static RestResultEnum valueOf(int errorCode) {
		for (RestResultEnum status : values()) {
			if (status.code == errorCode) {
				return status;
			}
		}
		throw new IllegalArgumentException("No matching constant for [" + errorCode + "]");
	}
}
