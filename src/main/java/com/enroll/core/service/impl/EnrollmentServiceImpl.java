package com.enroll.core.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.enroll.core.dao.EnrollmentDao;
import com.enroll.core.dto.EnrollmentDTO;
import com.enroll.core.dto.FormFieldMetaDTO;
import com.enroll.core.dto.FormFieldOptionDTO;
import com.enroll.core.dto.FormMetaDTO;
import com.enroll.core.dto.FormMetaQuery;
import com.enroll.core.entity.FormFieldMeta;
import com.enroll.core.entity.FormFieldOption;
import com.enroll.core.entity.FormFieldValue;
import com.enroll.core.entity.FormMeta;
import com.enroll.core.service.EnrollmentService;

@Service("enrollmentService")
@Transactional
public class EnrollmentServiceImpl implements EnrollmentService {

	@Autowired
	private EnrollmentDao enrollmentDao;

	@Override
	public FormMetaDTO findFormMetaById(long id) {
		FormMeta formMeta = enrollmentDao.readGenericEntity(FormMeta.class, id);
		return transformFormMeta(formMeta, null);
	}

	private FormMetaDTO transformFormMeta(FormMeta formMeta, List<FormFieldValue> fieldValueList) {

		// If a list of formFieldValue is also available put them into map with field id as key
		Map<Long, FormFieldValue> map = new HashMap<Long, FormFieldValue>();
		if (!CollectionUtils.isEmpty(fieldValueList)) {
			fieldValueList.forEach(fieldValue -> {
				map.put(fieldValue.getFieldId(), fieldValue);
			});
		}

		FormMetaDTO result = new FormMetaDTO();
		BeanUtils.copyProperties(formMeta, result, "formFieldMetaList");

		// transform form field data
		formMeta.getFormFieldMetaList().forEach(formField -> {
			FormFieldMetaDTO formFieldMeta = new FormFieldMetaDTO();
			BeanUtils.copyProperties(formField, formFieldMeta, "fieldOptionList");
			result.addFormFieldMeta(formFieldMeta);

			// fill in the field value if present
			if (map.containsKey(formField.getFieldId())) {
				formFieldMeta.setFieldValue(map.get(formField.getFieldId()).getFieldValue());
			}

			// transform the options data if present
			if (!CollectionUtils.isEmpty(formField.getFieldOptionList())) {
				formField.getFieldOptionList().forEach(option -> {
					FormFieldOptionDTO fieldOption = new FormFieldOptionDTO();
					BeanUtils.copyProperties(option, fieldOption);
					formFieldMeta.addFieldOption(fieldOption);
				});
			}
		});
		return result;
	}

	@Override
	public FormMetaDTO saveFormMeta(FormMetaDTO formMetaDTO) {
		Objects.requireNonNull(formMetaDTO);
		final FormMeta formMeta = new FormMeta();
		BeanUtils.copyProperties(formMetaDTO, formMeta, "formFieldMetaList");

		// Group form field data
		formMetaDTO.getFormFieldMetaList().forEach(formField -> {
			FormFieldMeta formFieldMeta = new FormFieldMeta();
			BeanUtils.copyProperties(formField, formFieldMeta, "fieldOptionList");
			formMeta.addFormFieldMeta(formFieldMeta);

			// Group the options data if any
			if (!CollectionUtils.isEmpty(formField.getFieldOptionList())) {
				formField.getFieldOptionList().forEach(option -> {
					FormFieldOption fieldOption = new FormFieldOption();
					BeanUtils.copyProperties(option, fieldOption);
					formFieldMeta.addFormFieldOption(fieldOption);
					if (StringUtils.isBlank(option.getValue())) {
						fieldOption.setValue(String.valueOf(formFieldMeta.getSizeOfFieldOptions()));
					}
				});
			}
		});
		enrollmentDao.save(formMeta);
		formMetaDTO.setFormId(formMeta.getFormId());
		return formMetaDTO;
	}
	
	@Override
	public String saveEnrollment(EnrollmentDTO enrollmentDTO) {
		Objects.requireNonNull(enrollmentDTO);
		enrollmentDTO.getFieldValueList().stream().forEach(value -> {
			FormFieldValue fieldValue = new FormFieldValue();
			BeanUtils.copyProperties(value, fieldValue);
			enrollmentDao.save(fieldValue);
		});
		return enrollmentDTO.getRegistrId();
	}

	@Override
	public FormMetaDTO findFormMetaWithInputValue(long formId, String id) {
		FormMeta formMeta = enrollmentDao.readGenericEntity(FormMeta.class, formId);
		List<FormFieldValue> list = enrollmentDao.findFormFieldValueList(formId, id);
		return transformFormMeta(formMeta, list);
	}

	@Override
	public FormMetaDTO update(FormMetaDTO bean) {
		return null;
	}	

	@Override
	public List<FormMetaDTO> findFormMetaList(FormMetaQuery query) {
		Objects.requireNonNull(query);
		List<FormMeta> list = enrollmentDao.findFormMetaList(query);
		List<FormMetaDTO> result = new ArrayList<>();
		list.stream().forEach(formMeta -> {
			FormMetaDTO dto = new FormMetaDTO();
			BeanUtils.copyProperties(formMeta, dto, "formFieldMetaList");
			result.add(dto);
		});
		return result;
	}

	@Override
	public FormMetaDTO findFormMetaPage(FormMetaQuery query) {
		
		return null;
	}

	@Override
	public EnrollmentDTO findEnrollment(String registrId) {
		
		return null;
	}
}