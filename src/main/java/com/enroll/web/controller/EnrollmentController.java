package com.enroll.web.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.enroll.core.dto.EnrollmentDTO;
import com.enroll.core.dto.FormMetaDTO;
import com.enroll.core.service.EnrollmentService;

@Controller
public class EnrollmentController {
	
	@Resource(name = "enrollmentService")
	private EnrollmentService enrollmentService;
	
	/**
	 * 注册的form生成页面
	 * @return String
	 */
	@RequestMapping(value = "/enrollForm.html", method = RequestMethod.GET)
	public String getDesignForm(Long formId, Model model, BindingResult result) {
		FormMetaDTO formMetaDTO = enrollmentService.findFormMetaById(formId);
		model.addAttribute("formMeta", formMetaDTO);
		return "enrollForm";
	}
	
	/**
	 * 
	 * 保存注册信息
	 * @param enroll
	 * @return String
	 */
	@RequestMapping(value = "/enroll.html", method = RequestMethod.POST)
	public String saveEnrollment(EnrollmentDTO enroll) {
		String registrId = enrollmentService.saveEnrollment(enroll);
		return "redirect:/enroll.html?registrId=" + registrId;
	}
	
	/**
	 * 浏览注册信息
	 * @param registrId
	 * @param model
	 * @return String
	 */
	@RequestMapping(value = "/enroll.html", method = RequestMethod.GET)
	public String getEnrollment(String registrId, Model model){
		EnrollmentDTO enroll = enrollmentService.findEnrollment(registrId);
		model.addAttribute("enroll", enroll);
		return "enrollDetail";
	}
}