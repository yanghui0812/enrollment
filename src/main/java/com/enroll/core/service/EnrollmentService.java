package com.enroll.core.service;

import java.util.List;

import com.enroll.core.dto.FormFieldValueDTO;
import com.enroll.core.dto.FormMetaDTO;

public interface EnrollmentService {

	/**
	 * 根据formId返回定义的form元数据
	 * @param formId form编号
	 * @return FormMetaDTO
	 */
	public FormMetaDTO findFormMetaById(long formId);
	
	/**
	 * 根据formId和身份证号返回form元数据和页面输入的数据，返回到页面后组装
	 * @param formId form编号
	 * @param id 身份证号
	 * @return FormMetaDTO
	 */
	public FormMetaDTO findFormMetaWithInputValue(long formId, String id);

	/**
	 * 保存form元数据到数据库
	 * @param formMetaDTO
	 * @return FormMetaDTO
	 */
	public FormMetaDTO saveFormMeta(FormMetaDTO formMetaDTO);
	
	/**
	 * @param fieldvalueList
	 * @return int
	 */
	public int saveFormInputFieldvalues(List<FormFieldValueDTO> fieldvalueList);

	/**
	 * @param bean
	 * @return
	 */
	public FormMetaDTO update(FormMetaDTO bean);
}