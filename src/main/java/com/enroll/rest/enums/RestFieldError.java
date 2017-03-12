package com.enroll.rest.enums;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public enum RestFieldError {
	
	MISSING_VALUE(Arrays.asList("NotNull"), 2001, "<字段名字>缺失"),
	INVALID_VALUE(Arrays.asList("Min"), 2002, "值<字段名字>无效"),
	INVALID_FORMAT(Arrays.asList("Pattern"), 2003, "值<字段名字>格式错误"),
	INVALID_VALUE_DETAIL(Arrays.asList("Min"), 2002, "值<字段名字>无效，<详细信息>"),
	MISSING_FIELD(Arrays.asList(), 2004, "<字段名字>不存在");
	
	private int code;
	private List<String> keyList;
	private String desc;
	private RestFieldError(List<String> keyList, int code, String desc) {
		this.keyList = keyList;
		this.code = code;
		this.desc = desc;
	}
	
	public static RestFieldError getDefinition(String key) {
		for (RestFieldError error : values()) {
			if (error.keyList.contains(key)) {
				return error;
			}
		}
		return null;
	}
	public int getCode() {
		return code;
	}
	public String getDesc() {
		return desc;
	}
	
	public String getReplacedDesc(String value) {
		return StringUtils.replace(desc, "<字段名字>", value);
	}
	public String getReplacedDescAndDetail(String value, String detail) {
		return StringUtils.replaceEach(desc, new String[]{"<字段名字>","<详细信息>"},new String[]{value, detail});
	}
}