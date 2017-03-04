package com.enroll.web.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.enroll.core.dto.AjaxResult;
import com.enroll.core.dto.FormMetaDTO;
import com.enroll.core.dto.FormMetaQuery;
import com.enroll.core.dto.SearchCriteria;
import com.enroll.core.dto.SearchResult;
import com.enroll.core.service.EnrollmentService;

@Controller
public class FormDesignController {
	
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
			if (formMeta != null) {
				model.addAttribute("formMeta", formMeta);
				model.addAttribute("rawJson", formMeta.getRawJson());	
			}
		}
		return "designForm";
	}
	
	/**
	 * 查询表单设计列表
	 * @return String
	 */
	@RequestMapping(value = "/designforms")
	@ResponseBody
	public SearchResult<FormMetaDTO> designFormPage(SearchCriteria<FormMetaQuery> criteria){
		SearchResult<FormMetaDTO> result = enrollmentService.findFormMetaPage(criteria);
		return result;
	}
	
	/**
	 * 保存表单的元数据
	 * @param formMeta
	 * @return String
	 */
	@RequestMapping(value = "/saveForm.html")
	@ResponseBody
	public AjaxResult<String> saveFormDesign(FormMetaDTO formMeta, BindingResult result, Model model){
		formMeta.setStatus("1");
		formMeta = enrollmentService.saveFormMeta(formMeta);
		AjaxResult<String> ajaxResult = new AjaxResult<String>();
		ajaxResult.setStatus("200");
		return ajaxResult;
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
		return "formList";
	}
	
	@RequestMapping(value = "/register.html", method = RequestMethod.GET)
	public String register(int formId, String id, BindingResult result, Model model){
		return "registerResult";
	}
}