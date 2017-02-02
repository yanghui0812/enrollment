package com.enroll.core.enums;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

public enum FormStatus {

	DRAFT("1", "草稿"), PUBLISH("2", "发布"), INACTIVE("3", "废弃");

	private String type;
	private String desc;

	private FormStatus(String type, String desc) {
		this.type = type;
		this.desc = desc;
	}

	public static Map<String, String> getMap() {
		Map<String, String> map = new HashMap<String, String>();
		for (FormStatus status : values()) {
			if (!StringUtils.isEmpty(status.type)) {
				map.put(status.type, status.getDesc());
			}
		}
		return map;
	}

	public static FormStatus getDesc(String type) {
		for (FormStatus fieldType : values()) {
			if (fieldType.type.equals(type)) {
				return fieldType;
			}
		}
		return null;
	}

	public static FormStatus getMessage(String type) {
		for (FormStatus fieldType : values()) {
			if (fieldType.desc.equals(type)) {
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