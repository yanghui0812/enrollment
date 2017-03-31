package com.enroll.core.enums;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

public enum FormFieldType {

	TEXT("text", "输入框"), DATE("date", "日期输入框"), NUMBER("number", "数字输入框"), SELECT("select", "下拉框"), CHECKBOX("checkbox", "多选框"), RADIO("radio",
			"单选框"), TEXTAREA("textarea", "多行的文本输入"), CHECKBOX_GROUP("checkbox-group", "多选框组"), RADIO_GROUP("radio-group", "单选组");

	private String type;
	private String name;

	private FormFieldType(String type, String name) {
		this.type = type;
		this.name = name;
	}

	public static Map<String, String> getAllFieldTypeMap() {
		Map<String, String> map = new HashMap<String, String>();
		for (FormFieldType fieldType : values()) {
			if (!StringUtils.isEmpty(fieldType.type)) {
				map.put(fieldType.type, fieldType.getName());
			}
		}
		return map;
	}

	public static FormFieldType getFieldType(String type) {
		for (FormFieldType fieldType : values()) {
			if (fieldType.type.equals(type)) {
				return fieldType;
			}
		}
		return null;
	}
	
	public static boolean hasOption(String type) {
		return SELECT.type.equals(type) || CHECKBOX.type.equals(type) || RADIO.type.equals(type);
	}

	public String getType() {
		return type;
	}

	public String getName() {
		return name;
	}
}