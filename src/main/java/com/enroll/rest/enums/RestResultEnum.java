package com.enroll.rest.enums;

public enum RestResultEnum {

	SUCCESS(1000, "请求成功"), 
	UNKNOWN_ERROR(1001, "未知的内部错误"), 
	MALFORMED(1002, "错误的请求"),
	ENROLL_NOT_EXIST(1003, "报名信息不存在！"),
	SIGNATURE_FAIL(1008, "签名校验失败");

	private final int code;

	private final String reasonPhrase;

	private RestResultEnum(int code, String reasonPhrase) {
		this.code = code;
		this.reasonPhrase = reasonPhrase;
	}

	public int getCode() {
		return code;
	}

	public String getReasonPhrase() {
		return reasonPhrase;
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
