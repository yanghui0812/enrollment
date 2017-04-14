package com.enroll.rest.handler;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.enroll.rest.dto.RestBasicResult;
import com.enroll.rest.dto.RestErrorResult;
import com.enroll.rest.enums.RestResultEnum;


@ControllerAdvice(annotations = RestController.class)
@Order(0)
public class RestControllerAdvice {
	
	private static final Logger LOGGER = LogManager.getLogger(RestControllerAdvice.class);
	
	@ResponseBody
	@ExceptionHandler(value = { RuntimeException.class, Exception.class })
	public RestBasicResult handleCSEWebServiceException(Exception e, HttpServletRequest req) {
		RestBasicResult result =  new RestErrorResult();
		result.setMessageAndcode(RestResultEnum.UNKNOWN_ERROR);
		LOGGER.error(result, e);
		return result;
	}
}
