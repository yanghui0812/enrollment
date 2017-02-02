package com.enroll.web.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.enroll.core.dto.FormMetaDTO;
import com.enroll.core.service.EnrollmentService;

@Controller
@RequestMapping("/enroll")
public class EnrollmentController {
	
	@Resource(name = "enrollmentService")
	private EnrollmentService enrollmentService;
	
	@RequestMapping(value = "/designForm.html")
	public String designForm(){
		return "designForm";
	}
	
	@RequestMapping(value = "/saveDesignForm.html")
	public String saveFormDesign(FormMetaDTO formMeta){
		
		formMeta = enrollmentService.saveFormMeta(formMeta);
		
		return "redirect:/registerForm.html?formId=" + formMeta.getFormId();
	}
	
	@RequestMapping(value = "/registerForm.html")
	public String registerForm(int formId){
		return "registerForm";
	}
	
	@RequestMapping(value = "/register.html", method = RequestMethod.GET)
	public String register(int formId, String id){
		return "registerResult";
	}
}