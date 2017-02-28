package com.enroll.web.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.enroll.core.dto.FormMetaDTO;
import com.enroll.core.dto.FormMetaQuery;
import com.enroll.core.dto.PageForm;
import com.enroll.core.service.EnrollmentService;
import com.google.gson.Gson;

@Controller
public class FormDesignController {
	
	private Gson gson = new Gson();
	
	@Resource(name = "enrollmentService")
	private EnrollmentService enrollmentService;
	
	/**
	 * 进入到表单设计主页
	 * @return String
	 */
	@RequestMapping(value = "/designform.html")
	public String designForm(Long formId, Model model){
		if (formId != null) {
			FormMetaDTO formMeta = enrollmentService.findFormMetaById(formId);
			PageForm pageForm = new PageForm(formMeta);
			model.addAttribute("pageForm", gson.toJson(pageForm));
			model.addAttribute("formMeta", formMeta);
		}
		return "designForm";
	}
	
	/**
	 * 保存表单的元数据
	 * @param formMeta
	 * @return String
	 */
	@RequestMapping(value = "/saveForm.html")
	public String saveFormDesign(FormMetaDTO formMeta, BindingResult result, Model model){
		formMeta.setStatus("1");
		formMeta = enrollmentService.saveFormMeta(formMeta);
		return "redirect:/form.html?formId=" + formMeta.getFormId();
	}
	
	/**
	 * 表单设计的详细信息
	 * @param formId
	 * @return String
	 */
	@RequestMapping(value = "/form.html")
	public String registerForm(int formId){
		return "registerForm";
	}
	
	/**
	 * 表单的管理页面
	 * @return String
	 */
	@RequestMapping(value = "/forms.html", method = RequestMethod.GET)
	public String getDesignForms(FormMetaQuery query, BindingResult result, Model model) {
		List<FormMetaDTO> list = enrollmentService.findFormMetaList(query);
		model.addAttribute("formList", list);
		return "formList";
	}
	
	@RequestMapping(value = "/register.html", method = RequestMethod.GET)
	public String register(int formId, String id, BindingResult result, Model model){
		return "registerResult";
	}
}