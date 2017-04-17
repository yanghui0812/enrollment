package com.enroll.web.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.enroll.common.AppConstant;
import com.enroll.common.DateUtils;
import com.enroll.core.dto.AjaxResult;
import com.enroll.core.dto.EnrollmentDTO;
import com.enroll.core.dto.FormFieldMetaDTO;
import com.enroll.core.dto.FormFieldOptionDTO;
import com.enroll.core.dto.FormMetaDTO;
import com.enroll.core.enums.AjaxResultStatus;
import com.enroll.core.enums.EnrollmentStatus;
import com.enroll.core.service.EnrollmentService;

/**
 * @ClassName EnrollmentController
 * @Description
 * Process all the operation related to enrollment
 * @author Leo.yang
 * @Date Mar 5, 2017 10:06:21 PM
 * @version 1.0.0
 */
@Controller
@RequestMapping("/public")
public class EnrollmentController {
	
	@Resource(name = "enrollmentService")
	private EnrollmentService enrollmentService;
	
	/**
	 * @Description 
	 * Prepare the design form information for enrollment
	 * @param formId
	 * @param model
	 * @return String
	 */
	@RequestMapping(value = "/enrollForm.html", method = RequestMethod.GET)
	public String getDesignForm(Long formId, Model model) {
		FormMetaDTO formMetaDTO = enrollmentService.findFormMetaById(formId);
		if (!formMetaDTO.isCanEnroll()) {
			return "error";
		}
		String dateTime = LocalDateTime.now().format(DateUtils.YYYY_MM_DD_HH_MM_CN);
		Optional<FormFieldMetaDTO> fieldOptional = formMetaDTO.getFields().stream().filter(field -> {
			return AppConstant.APPLICANT_TIME.equalsIgnoreCase(field.getName());
		}).findAny();
		
		if (fieldOptional.isPresent()) {
			List<FormFieldOptionDTO> result = fieldOptional.get().getOptions().stream().filter(option -> {
			 	if (StringUtils.isBlank(option.getValue())) {
					return true;
				}
				return dateTime.compareToIgnoreCase(getOptionDate(option.getValue())) <= 0;
			}).collect(Collectors.toList());
			fieldOptional.get().setOptions(result);
		}
		model.addAttribute("formMeta", formMetaDTO);
		return "publicEnrollForm";
	}
	
	private String getOptionDate(String value) {
		String year = StringUtils.substringBefore(value, "年");
		String month = StringUtils.substringBetween(value, "年", "月");
		String day = StringUtils.substringBetween(value, "月", "日");
		StringBuilder sb = new StringBuilder(year);
		if (month.length() < 2) {
			sb.append("0");
		}
		sb.append(month);
		if (day.length() < 2) {
			sb.append("0");
		}
		sb.append(day);
		if (value.contains("上午")) {
			sb.append("AM");
		} else {
			sb.append("PM");
		}
		return sb.toString();
	}
	
	/**
	 * @Description
	 * Save enrollment information
	 * @param enroll
	 * @return String
	 */
	@RequestMapping(value = "/enroll.html", method = RequestMethod.POST)
	public String saveEnrollment(EnrollmentDTO enroll, Model model) {
		boolean available = enrollmentService.isApplicantSlotAvailable(enroll.getFieldValueMap(), enroll.getFormId());
		if (!available) {
			return "notAvailable";
		}
		String registrId = enrollmentService.saveEnrollment(enroll);
		return "redirect:/public/enroll.html?registerId=" + registrId;
	}
	
	/**
	 * Check the availability
	 * 
	 * @param enroll
	 * @param model
	 * @return AjaxResult<Boolean>
	 */
	@RequestMapping(value = "/checkAvailable", produces = "application/json; charset=UTF-8", method = RequestMethod.GET)
	@ResponseBody
	public AjaxResult<Boolean> checkAvailable(long formId, long fieldId, String value) {
		Map<Long, String> map = new HashMap<>();
		map.put(fieldId, value);
		boolean available = enrollmentService.isApplicantSlotAvailable(map, formId);		
		AjaxResult<Boolean> ajaxResult = new AjaxResult<Boolean>(AjaxResultStatus.SUCCESS);
		ajaxResult.setData(available);
		return ajaxResult;
	}
	
	/**
	 * Change the status of enrollment;
	 * @param registerId
	 * @param status
	 * @return String
	 */
	@RequestMapping(value = "/enrolls/{registerId}", method = RequestMethod.POST)
	public String updateEnrollment(@PathVariable String registerId, String status) {
		if (EnrollmentStatus.ENROLL.getType().equals(status)) {
			enrollmentService.confirmEnrollment(registerId);
		} else if (EnrollmentStatus.CANCEL.getType().equals(status)) {
			enrollmentService.cancelEnrollment(registerId);
		}
		return "redirect:/public/enroll.html?registerId=" + registerId;
	}
	
	/**
	 * @Description
	 * Get the enrollment info and form meta for update
	 * @param registerId
	 * @param model
	 * @return String
	 */
	@RequestMapping(value = "/updateEnroll.html", method = RequestMethod.GET)
	public String getEnrollInfo(String registerId, Model model) {
		EnrollmentDTO enroll = enrollmentService.findFormMetaWithInputValue(registerId);
		if (!enroll.isDraft()) {
			return "redirect:/public/enroll.html?registerId=" + registerId;
		}
		model.addAttribute("formMeta", enroll.getFormMeta());
		model.addAttribute("enroll", enroll);
		return "publicEnrollForm";
	}
	
	/**
	 * @Description 
	 * View the detailed enrollment information
	 * @param registerId
	 * @param model
	 * @return String
	 */
	@RequestMapping(value = "/enroll.html", method = RequestMethod.GET)
	public String getEnrollment(String registerId, Model model){
		EnrollmentDTO enroll = enrollmentService.findEnrollment(registerId);
		model.addAttribute("enroll", enroll);
		return "publicEnrollDetail";
	}
	
	/**
	 * Query to get the enrollment with form id, field id and field value;
	 * @param formId
	 * @param fieldId
	 * @param value
	 * @param result
	 * @param model
	 * @return AjaxResult<String>
	 */
	@RequestMapping(value = "/checkUniqueKey", produces = "application/json; charset=UTF-8", method = RequestMethod.GET)
	@ResponseBody
	public AjaxResult<String> findEnrollment(long formId, long fieldId, String value) {
		String registerId = enrollmentService.findRegisterIdByFormIdAndUniqueKey(formId, fieldId, value);
		AjaxResult<String> ajaxResult = new AjaxResult<String>(AjaxResultStatus.SUCCESS);
		ajaxResult.setData(registerId);
		return ajaxResult;
	}
}