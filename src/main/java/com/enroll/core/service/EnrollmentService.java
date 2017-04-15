package com.enroll.core.service;

import java.util.List;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.enroll.core.dto.AjaxResult;
import com.enroll.core.dto.EnrollmentDTO;
import com.enroll.core.dto.EnrollmentQuery;
import com.enroll.core.dto.FormFieldMetaDTO;
import com.enroll.core.dto.FormMetaDTO;
import com.enroll.core.dto.FormMetaQuery;
import com.enroll.core.dto.SearchResult;
import com.enroll.rest.dto.RestBasicResult;
import com.enroll.rest.dto.RestRequest;

public interface EnrollmentService {

	/**
	 * Find the design form meta data
	 * @param formId
	 * @return FormMetaDTO
	 */
	public FormMetaDTO findFormMetaById(long formId);
	
	/**
	 * @param slotMap
	 * @param fieldId
	 * @param formId
	 */
	public void changeFormSlot(FormFieldMetaDTO slotMap, long formId);
	
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
	 * Copy the current and save as a new form
	 * 
	 * @param formId
	 * @return AjaxResult<String>
	 */
	public AjaxResult<String> duplicateForm(Long formId);
	
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
	 * Save rest request enrollment information and then return register Id;
	 * 
	 * @param request
	 * @param registerId
	 * @return RestBasicResult
	 */
	public RestBasicResult saveRestEnrollment(RestRequest request, String registerId);
	
	/**
	 * Save rest request enrollment information and then return register Id;
	 * 
	 * @param request
	 * @return RestBasicResult
	 */
	public RestBasicResult saveRestEnrollment(RestRequest request);
	
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
	 * Find the applicant slot
	 * 
	 * @param formId
	 * @param option
	 * @return RestBasicResult
	 */
	public RestBasicResult findApplicantSlot(Long formId);
	
	/**
	 * @param String
	 * @return SearchResult<EnrollmentDTO>
	 */
	public SearchResult<EnrollmentDTO> findEnrollmentPage(EnrollmentQuery criteria);
	
	/**
	 * @param searchCriteria
	 * @return
	 */
	public SearchResult<FormMetaDTO> findFormMetaPage(FormMetaQuery searchCriteria);
	
	/**
	 * Check if applicant slot is still available;
	 * 
	 * @param map
	 * @param formId
	 * @return boolean
	 */
	public boolean isApplicantSlotAvailable(Map<Long, String> map, long formId);
	
	/**
	 * 
	 * Find the enrollment with form id, field id and field value in order to avoid
	 * someone applying more than one times
	 * 
	 * @param formId
	 * @param fieldId
	 * @param value
	 * @return String
	 */
	public String findRegisterIdByFormIdAndUniqueKey(long formId, long fieldId, String value);
	
	/**
	 * @param formId
	 * @return
	 */
	public Map<String, String> findApplicantSlotMap(Long formId);
	
	/**
	 * Export enrollments according to the query criteria;
	 * @param query
	 * @return XSSFWorkbook
	 */
	public XSSFWorkbook exportEnrollment(EnrollmentQuery query);
}