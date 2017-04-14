package com.enroll.core.enums;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

public enum AjaxResultStatus {
	
	SUCCESS("success", "成功"),
	FAIL("fail", "失败");

	private String type;
	private String desc;

	private AjaxResultStatus(String type, String desc) {
		this.type = type;
		this.desc = desc;
	}

	public static Map<String, String> getEnrollStatusMap() {
		Map<String, String> map = new HashMap<String, String>();
		for (AjaxResultStatus fieldType : values()) {
			if (!StringUtils.isEmpty(fieldType.type)) {
				map.put(fieldType.type, fieldType.getDesc());
			}
		}
		return map;
	}

	public static AjaxResultStatus getEnrollStatus(String type) {
		for (AjaxResultStatus fieldType : values()) {
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