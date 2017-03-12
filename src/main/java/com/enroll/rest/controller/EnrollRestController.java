package com.enroll.rest.controller;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.enroll.core.dto.EnrollmentDTO;
import com.enroll.core.service.EnrollmentService;
import com.enroll.rest.dto.PropertyError;
import com.enroll.rest.dto.RestBasicResult;
import com.enroll.rest.dto.RestErrorResult;
import com.enroll.rest.dto.RestFieldValue;
import com.enroll.rest.dto.RestRequest;
import com.enroll.rest.dto.RestResult;
import com.enroll.rest.enums.RestFieldError;
import com.enroll.rest.enums.RestResultEnum;


/**
 * @ClassName EnrollRestController
 * @Description
 * Provide restful service to enrollment information resource
 * @author Leo.yang
 * @Date Mar 12, 2017 3:23:49 PM
 * @version 1.0.0
 */
@RestController
@RequestMapping("/api/v1")
public class EnrollRestController {
	
	@Resource(name = "enrollmentService")
	private EnrollmentService enrollmentService;
	
	/**
	 * Retrieve the register information by register id;
	 * @param registerId
	 * @return RestBasicResult
	 */
	@RequestMapping(value = "/enrolls/{registerId}", produces = "application/json;charset=UTF-8", method = RequestMethod.GET)
	public RestBasicResult getEnrollment(@PathVariable String registerId) {
		if (StringUtils.isBlank(registerId)) {
			RestErrorResult errorResult = new RestErrorResult(RestResultEnum.MALFORMED);
			errorResult.setFieldErrors(Arrays.asList(new PropertyError(RestFieldError.MISSING_VALUE, "注册号")));
			return errorResult;
		}
		RestBasicResult result = null;
		EnrollmentDTO dto = enrollmentService.findEnrollment(registerId);
		if (dto == null) {
			result = new RestErrorResult(RestResultEnum.ENROLL_NOT_EXIST);
		}
		result = new RestResult<List<RestFieldValue>>(RestResultEnum.SUCCESS, dto.getRestFieldValueList());
		return result;
	}
	
	/**
	 * Update the register information;
	 * @param request
	 * @param registerId
	 * @return RestBasicResult
	 */
	@RequestMapping(value = "/enrolls/{registerId}", consumes= "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8", method = RequestMethod.PUT)
	public RestBasicResult putEnrollment(@RequestBody RestRequest request, @PathVariable String registerId) {
		if (StringUtils.isNotBlank(registerId)) {
			return enrollmentService.saveRestEnrollment(request, registerId);
		}
		RestErrorResult errorResult = new RestErrorResult(RestResultEnum.MALFORMED);
		errorResult.setFieldErrors(Arrays.asList(new PropertyError(RestFieldError.MISSING_VALUE, "注册号")));
		return errorResult;
	}
	
	/**
	 * Post register information
	 * @param registerId
	 * @param status
	 * @return RestBasicResult
	 */
	@RequestMapping(value = "/enrolls", consumes= "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
	public RestBasicResult postEnrollment(@RequestBody RestRequest request) {
		return enrollmentService.saveRestEnrollment(request);
	}
	
	/**
	 * Partially modify the register information
	 * @param registerId
	 * @param status
	 * @return RestBasicResult
	 */
	@RequestMapping(value = "/enrolls/{registerId}", consumes= "application/json;charset=UTF-8", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
	public RestBasicResult partiallypostEnrollment(@RequestBody RestRequest request) {
		RestBasicResult result = null;
		return result;
	}
}