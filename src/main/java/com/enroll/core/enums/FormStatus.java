package com.enroll.core.enums;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

public enum FormStatus {

	DRAFT("draft", "草稿"), PUBLISH("publish", "发布"), INACTIVE("inactive", "废弃");

	private String type;
	private String desc;

	private FormStatus(String type, String desc) {
		this.type = type;
		this.desc = desc;
	}

	public static Map<String, String> getFormStatusMap() {
		Map<String, String> map = new HashMap<String, String>();
		for (FormStatus status : values()) {
			if (!StringUtils.isEmpty(status.type)) {
				map.put(status.type, status.getDesc());
			}
		}
		return map;
	}

	public static FormStatus getFormStatus(String type) {
		for (FormStatus fieldType : values()) {
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