package com.enroll.core.service;

import java.util.List;

import com.enroll.core.dto.AjaxResult;
import com.enroll.core.dto.EnrollmentDTO;
import com.enroll.core.dto.EnrollmentQuery;
import com.enroll.core.dto.FormMetaDTO;
import com.enroll.core.dto.FormMetaQuery;
import com.enroll.core.dto.SearchCriteria;
import com.enroll.core.dto.SearchResult;

public interface EnrollmentService {

	/**
	 * Find the design form meta data
	 * @param formId
	 * @return FormMetaDTO
	 */
	public FormMetaDTO findFormMetaById(long formId);
	
	/**
	 * Get design form meta data and input value
	 * @param registerId
	 * @return FormMetaDTO
	 */
	public EnrollmentDTO findFormMetaWithInputValue(String registerId);

	/**
	 * Save form meta data
	 * @param formMetaDTO
	 * @return FormMetaDTO
	 */
	public FormMetaDTO saveFormMeta(FormMetaDTO formMetaDTO);
	
	
	/**
	 * @Description
	 * Publish the form so that it can be used to register;
	 * @param formId
	 * @return AjaxResult<String>
	 */
	public AjaxResult<String> publishForm(Long formId);
	
	/**
	 * @Description
	 * Disable the form;
	 * @param formId
	 * @return AjaxResult<String>
	 */
	public AjaxResult<String> inactiveForm(Long formId);
	
	/**
	 * 根据查询条件返回form的列表
	 * @param FormMetaQuery
	 * @return List<FormMetaDTO>
	 */
	public List<FormMetaDTO> findFormMetaList(FormMetaQuery query);
	
	/**
	 * @param enrollmentDTO
	 * @return String
	 */
	public String saveEnrollment(EnrollmentDTO enrollmentDTO);
	
	/**
	 * Confirm the enrollment so the information will not be changed except cancellation;
	 * 
	 * @param enrollmentDTO
	 * @return AjaxResult<String>
	 */
	public AjaxResult<String> confirmEnrollment(String registerId);
	
	/**
	 * Cancel the enrollment;
	 * 
	 * @param String
	 * @return AjaxResult<String>
	 */
	public AjaxResult<String> cancelEnrollment(String registerId);
	
	/**
	 * @param String
	 * @return EnrollmentDTO
	 */
	public EnrollmentDTO findEnrollment(String registerId);
	
	/**
	 * @param String
	 * @return SearchResult<EnrollmentDTO>
	 */
	public SearchResult<EnrollmentDTO> findEnrollmentPage(SearchCriteria<EnrollmentQuery> criteria);
	
	/**
	 * @param searchCriteria
	 * @return
	 */
	public SearchResult<FormMetaDTO> findFormMetaPage(SearchCriteria<FormMetaQuery> searchCriteria);
}