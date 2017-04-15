package com.enroll.web.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.enroll.core.dto.AjaxResult;
import com.enroll.core.dto.FormFieldOptionDTO;
import com.enroll.core.dto.FormMetaDTO;
import com.enroll.core.dto.FormMetaQuery;
import com.enroll.core.dto.SearchResult;
import com.enroll.core.enums.AjaxResultStatus;
import com.enroll.core.enums.FormStatus;
import com.enroll.core.search.SearchCriteria;
import com.enroll.core.service.EnrollmentService;

/**
 * @ClassName FormDesignController
 * @Description Form design
 * @author Leo.yang
 * @Date Mar 5, 2017 10:00:36 PM
 * @version 1.0.0
 */
@Controller
public class FormDesignController {
	
	@Resource(name = "enrollmentService")
	private EnrollmentService enrollmentService;
	
	/**
	 * @Description 
	 * Go to form design page and there is an initialized page with some default fields;
	 * @param formId
	 * @param model
	 * @return String
	 */
	@RequestMapping(value = "/forms/{formId}", method = RequestMethod.GET)
	public String designForm(@PathVariable Long formId, Model model){
		if (formId != null) {
			FormMetaDTO formMeta = enrollmentService.findFormMetaById(formId);
			if (formMeta != null) {
				model.addAttribute("formMeta", formMeta);
				model.addAttribute("rawJson", formMeta.getRawJson());	
			}
		}
		model.addAttribute("active", "designForm");
		return "designForm";
	}
	
	/**
	 * @Description Query design form by some search words in data table page;
	 * @param criteria
	 * @return SearchResult<FormMetaDTO>
	 */
	@RequestMapping(value = "/queryForms", consumes = "application/json; charset=UTF-8", produces = "application/json; charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public SearchResult<FormMetaDTO> designFormPage(@RequestBody SearchCriteria criteria){
		FormMetaQuery query = new FormMetaQuery();
		criteria.searchMapping(query);
		SearchResult<FormMetaDTO> result = enrollmentService.findFormMetaPage(query);
		return result;
	}
	
	/**
	 * @Description
	 * Save all the fields on design form and the default status is draft. It has to be published if 
	 * it wants to be used for enrollment.
	 * @param formMeta
	 * @param result
	 * @param model
	 * @return AjaxResult<String>
	 */
	@RequestMapping(value = "/forms/{formId}", produces = "application/json; charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult<String> updateFormStatus(@PathVariable Long formId, String status){
		if (FormStatus.PUBLISH.getType().equals(status)) {
			return enrollmentService.publishForm(formId);
		}
		if (FormStatus.INACTIVE.getType().equals(status)) {
			return enrollmentService.inactiveForm(formId);
		}
		AjaxResult<String> ajaxResult = new AjaxResult<String>(AjaxResultStatus.FAIL);
		ajaxResult.setMessage("不支持当前的操作");
		return ajaxResult;
	}
	
	@RequestMapping(value = "/copyForm", produces = "application/json; charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult<String> duplicateForm(Long formId){
		return enrollmentService.duplicateForm(formId);
	}
	
	/**
	 * @Description
	 * Save all the fields of design form and the default status is draft. It has to be published if 
	 * it wants to be used for enrollment.
	 * @param formMeta
	 * @param result
	 * @param model
	 * @return AjaxResult<String>
	 */
	@RequestMapping(value = "/forms", produces = "application/json; charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult<Long> saveForm(FormMetaDTO formMeta, BindingResult result, Model model){
		formMeta.setStatus(FormStatus.DRAFT.getType());
		formMeta.getFields().stream().forEach(formFieldMetaDTO -> {
			formFieldMetaDTO.getOptions().addAll(formFieldMetaDTO.getOptionsMap().values());
		});
		formMeta = enrollmentService.saveFormMeta(formMeta);
		AjaxResult<Long> ajaxResult = new AjaxResult<Long>(AjaxResultStatus.SUCCESS);
		ajaxResult.setData(formMeta.getFormId());
		return ajaxResult;
	}
	
	/**
	 * @Description 
	 * Go to design form list page with pagination;
	 * @param query
	 * @param result
	 * @param model
	 * @return String
	 */
	@RequestMapping(value = "/forms.html", method = RequestMethod.GET)
	public String getDesignForms(FormMetaQuery query, BindingResult result, Model model) {
		model.addAttribute("active", "forms");
		return "formList";
	}
}