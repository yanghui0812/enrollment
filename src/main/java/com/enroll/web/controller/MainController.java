package com.enroll.web.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.enroll.core.dto.FormMetaDTO;
import com.enroll.core.dto.FormMetaQuery;
import com.enroll.core.service.EnrollmentService;

@Controller
public class MainController {
	
	@Resource(name = "enrollmentService")
	private EnrollmentService enrollmentService;
	
	@RequestMapping(value = "/home.html", method = RequestMethod.GET)
	public String home() {
		return "home";
	}
	
	/**
	 * @param criteria
	 * @param model
	 * @return String
	 */
	@RequestMapping(value = "/index.html")
	public String froms(FormMetaQuery criteria, Model model){
		FormMetaQuery query = new FormMetaQuery();
		List<FormMetaDTO> result = enrollmentService.findFormMetaList(query);
		model.addAttribute("forms", result);
		return "index";
	}
	
}