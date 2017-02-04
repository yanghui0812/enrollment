package com.enroll.core.service;

import java.util.List;

import com.enroll.core.dto.EnrollmentDTO;
import com.enroll.core.dto.FormMetaDTO;
import com.enroll.core.dto.FormMetaQuery;

public interface EnrollmentService {

	/**
	 * 根据formId返回定义的form元数据
	 * @param formId form编号
	 * @return FormMetaDTO
	 */
	public FormMetaDTO findFormMetaById(long formId);
	
	/**
	 * 根据formId和注册号返回form元数据和页面输入的数据，返回到页面后组装
	 * @param registerId
	 * @return FormMetaDTO
	 */
	public EnrollmentDTO findFormMetaWithInputValue(String registerId);

	/**
	 * 保存form元数据到数据库
	 * @param formMetaDTO
	 * @return FormMetaDTO
	 */
	public FormMetaDTO saveFormMeta(FormMetaDTO formMetaDTO);
	
	/**
	 * 根据查询条件返回form的列表
	 * @param FormMetaQuery
	 * @return List<FormMetaDTO>
	 */
	public List<FormMetaDTO> findFormMetaList(FormMetaQuery query);
	
	/**
	 * 根据查询条件返回form的page
	 * @param FormMetaQuery
	 * @return FormMetaDTO
	 */
	public FormMetaDTO findFormMetaPage(FormMetaQuery query);
	
	/**
	 * @param enrollmentDTO
	 * @return String
	 */
	public String saveEnrollment(EnrollmentDTO enrollmentDTO);
	
	/**
	 * @param String
	 * @return EnrollmentDTO
	 */
	public EnrollmentDTO findEnrollment(String registerId);

	/**
	 * @param bean
	 * @return
	 */
	public FormMetaDTO update(FormMetaDTO bean);
}