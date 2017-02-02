package com.enroll.core.enums;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

public enum FormFieldType {

	TEXT("text", "输入框"), SELECT("select", "下拉框"), CHECKBOX("checkbox", "多选框"), RADIO("radio",
			"单选框"), TEXTAREA("textarea", "多行的文本输入");

	private String type;
	private String name;

	private FormFieldType(String type, String name) {
		this.type = type;
		this.name = name;
	}

	public static Map<String, String> getMap() {
		Map<String, String> map = new HashMap<String, String>();
		for (FormFieldType fieldType : values()) {
			if (!StringUtils.isEmpty(fieldType.type)) {
				map.put(fieldType.type, fieldType.getName());
			}
		}
		return map;
	}

	public static FormFieldType getDescription(String type) {
		for (FormFieldType fieldType : values()) {
			if (fieldType.type.equals(type)) {
				return fieldType;
			}
		}
		return null;
	}

	public static FormFieldType getMessage(String type) {
		for (FormFieldType fieldType : values()) {
			if (fieldType.name.equals(type)) {
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