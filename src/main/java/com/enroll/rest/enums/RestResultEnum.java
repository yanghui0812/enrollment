package com.enroll.rest.enums;

public enum RestResultEnum {

	SUCCESS(1000, 200, "请求成功"), 
	CREATE_SUCCESS(1000, 201, "创建请求成功"), 
	UNKNOWN_ERROR(1001, 500, "未知的内部错误"), 
	MALFORMED(1002, 200, "错误的请求"),
	ENROLL_NOT_EXIST(1003, 200, "报名信息不存在"),
	NO_SLOT_AVAILABLE(1004, 200, "报名名额已满"),
	DUPLICATED_ENROLL(1005, 200, "重复的报名信息"),
	SIGNATURE_FAIL(1008, 200, "签名校验失败");

	private final int code;
	
	private final int httpStatus;

	private final String message;

	private RestResultEnum(int code, int status, String message) {
		this.code = code;
		this.httpStatus = status;
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	public int getStatus() {
		return httpStatus;
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