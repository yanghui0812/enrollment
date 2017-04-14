package com.enroll.core.enums;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

public enum FormFieldSubtype {

	TEXT("text", "文本框"), PASSWORD("password", "密码"), EMAIL("email", "电子邮件"), Tel("tel", "手机号");

	private String type;
	private String name;

	private FormFieldSubtype(String type, String name) {
		this.type = type;
		this.name = name;
	}

	public static Map<String, String> getAllFieldTypeMap() {
		Map<String, String> map = new HashMap<String, String>();
		for (FormFieldSubtype fieldType : values()) {
			if (!StringUtils.isEmpty(fieldType.type)) {
				map.put(fieldType.type, fieldType.getName());
			}
		}
		return map;
	}

	public static FormFieldSubtype getFieldType(String type) {
		for (FormFieldSubtype fieldType : values()) {
			if (fieldType.type.equals(type)) {
				return fieldType;
			}
		}
		return null;
	}

	public String getType() {
		return type;
	}

	public String getName() {
		return name;
	}
}