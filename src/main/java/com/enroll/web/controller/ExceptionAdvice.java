package com.enroll.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/*@ControllerAdvice(annotations = Controller.class)*/
public class ExceptionAdvice {
	
	private static final Logger LOGGER = LogManager.getLogger(ExceptionAdvice.class);

	@ExceptionHandler(value = { RuntimeException.class, Exception.class })
	public String exceptionHandler(Exception e, HttpServletRequest req) {
		LOGGER.error(e);
		return "error";
	}
}
