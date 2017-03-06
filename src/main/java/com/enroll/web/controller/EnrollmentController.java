package com.enroll.web.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.enroll.core.dto.EnrollmentDTO;
import com.enroll.core.dto.EnrollmentQuery;
import com.enroll.core.dto.FormMetaDTO;
import com.enroll.core.dto.FormMetaQuery;
import com.enroll.core.dto.SearchCriteria;
import com.enroll.core.dto.SearchResult;
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
		model.addAttribute("formMeta", formMetaDTO);
		return "enrollForm";
	}
	
	/**
	 * @Description
	 * Save enrollment information
	 * @param enroll
	 * @return String
	 */
	@RequestMapping(value = "/enroll.html", method = RequestMethod.POST)
	public String saveEnrollment(EnrollmentDTO enroll) {
		String registrId = enrollmentService.saveEnrollment(enroll);
		return "redirect:/public/enroll.html?registerId=" + registrId;
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
			return "error";
		}
		model.addAttribute("formMeta", enroll.getFormMeta());
		model.addAttribute("enroll", enroll);
		return "enrollForm";
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
		return "enrollDetail";
	}
	
	/**
	 * @Description
	 * Go to all the enrollment information with pagination;
	 * @param query
	 * @param result
	 * @param model
	 * @return String
	 */
	@RequestMapping(value = "/enrolls.html", method = RequestMethod.GET)
	public String enrollmentPage(FormMetaQuery query, BindingResult result, Model model) {
		return "enrollList";
	}
	
	/**
	 * @Description
	 * Search enrollment by search criteria
	 * @param criteria
	 * @return SearchResult<EnrollmentDTO> 
	 */
	@RequestMapping(value = "/enrolls", produces = "application/json; charset=UTF-8", method = RequestMethod.GET)
	@ResponseBody
	public SearchResult<EnrollmentDTO> getEnrollmentPage(SearchCriteria<EnrollmentQuery> criteria){
		SearchResult<EnrollmentDTO> result = enrollmentService.findEnrollmentPage(criteria);
		return result;
	}
}