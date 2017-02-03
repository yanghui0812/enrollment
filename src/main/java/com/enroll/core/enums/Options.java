package com.enroll.core.enums;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

public enum Options {

	TRUE("1", "是"), FALSE("0", "否");

	private String type;
	private String desc;

	private Options(String type, String desc) {
		this.type = type;
		this.desc = desc;
	}

	public static Map<String, String> getOptionMap() {
		Map<String, String> map = new HashMap<String, String>();
		for (Options status : values()) {
			if (!StringUtils.isEmpty(status.type)) {
				map.put(status.type, status.getDesc());
			}
		}
		return map;
	}

	public static Options getOption(String type) {
		for (Options fieldType : values()) {
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