package com.enroll.common.utils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.time.LocalTime;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;

import com.enroll.security.utils.SessionContextHolder;

public final class MaintenanceFieldsFiller {
	
	private static final Logger LOGGER = LogManager.getLogger(MaintenanceFieldsFiller.class);
	
	public static void fillCreatedAndModifiedFields(Object obj) {
		fillModifiedFields(obj);
		filler(obj, "createdUserId", SessionContextHolder.getCurrentUserId());
		filler(obj, "createdUsername", SessionContextHolder.getCurrentUserName());
		filler(obj, "createdDatetime", LocalTime.now());
	}
	
	public static void fillModifiedFields(Object obj) {
		filler(obj, "modifiedUserId", SessionContextHolder.getCurrentUserId());
		filler(obj, "modifiedUsername", SessionContextHolder.getCurrentUserName());
	}
	
	private static void filler(Object obj, String fieldname, Object value) {
		PropertyDescriptor pd = BeanUtils.getPropertyDescriptor(obj.getClass(), fieldname);
		Method method = BeanUtils.getWriteMethodParameter(pd).getMethod();
		try {
			method.invoke(obj, value);
		} catch (Exception e) {
			LOGGER.error(e);
		}
	}
	
	private MaintenanceFieldsFiller() {}
}