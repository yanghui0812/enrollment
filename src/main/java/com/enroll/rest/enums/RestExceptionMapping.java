package com.enroll.rest.enums;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;

public enum RestExceptionMapping {
	
	NotReadable(HttpMessageNotReadableException.class, "数据不能解析"),
	MethodArgument(MethodArgumentNotValidException.class, "数据不能解析,可能是缺少字段"),
	Unknow(Exception.class, "内部错误");
	
	@SuppressWarnings("rawtypes")
	private Class clz;
	private String message;
	
	@SuppressWarnings("rawtypes")
	private RestExceptionMapping(Class clz, String message) {
		this.clz = clz;
		this.message = message;
	}
	
	@SuppressWarnings("unchecked")
	public static String getMessage(Exception e) {
		for (RestExceptionMapping mapping : values()) {
			if (e.getClass().equals(mapping.clz) || mapping.clz.isAssignableFrom(e.getClass())){
				return mapping.message;
			}
		}
		return StringUtils.EMPTY;
	}
}