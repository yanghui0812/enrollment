package com.enroll.core.enums;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

public enum EnrollmentStatus {
	
	DRAFT("draft", "暂存"),
	ENROLL("enroll", "注册成功"),
	CANCEL("cancel", "注册删除");

	private String type;
	private String desc;

	private EnrollmentStatus(String type, String desc) {
		this.type = type;
		this.desc = desc;
	}

	public static Map<String, String> getEnrollStatusMap() {
		Map<String, String> map = new HashMap<String, String>();
		for (EnrollmentStatus fieldType : values()) {
			if (!StringUtils.isEmpty(fieldType.type)) {
				map.put(fieldType.type, fieldType.getDesc());
			}
		}
		return map;
	}

	public static EnrollmentStatus getEnrollStatus(String type) {
		for (EnrollmentStatus fieldType : values()) {
			if (fieldType.type.equals(type)) {
				return fieldType;
			}
		}
		return null;
	}

	public String getType() {
		return type;
	}

	public String getDesc() {
		return desc;
	}
}