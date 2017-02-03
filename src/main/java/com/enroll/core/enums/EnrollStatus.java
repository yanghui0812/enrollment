package com.enroll.core.enums;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

public enum EnrollStatus {

	SUBMIT("submit", "确认提交");

	private String type;
	private String desc;

	private EnrollStatus(String type, String desc) {
		this.type = type;
		this.desc = desc;
	}

	public static Map<String, String> getEnrollStatusMap() {
		Map<String, String> map = new HashMap<String, String>();
		for (EnrollStatus fieldType : values()) {
			if (!StringUtils.isEmpty(fieldType.type)) {
				map.put(fieldType.type, fieldType.getDesc());
			}
		}
		return map;
	}

	public static EnrollStatus getEnrollStatus(String type) {
		for (EnrollStatus fieldType : values()) {
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