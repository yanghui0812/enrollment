package com.enroll.core.service.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

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
import com.enroll.core.dto.FormFieldValueDTO;
import com.enroll.core.dto.FormMetaDTO;
import com.enroll.core.dto.FormMetaQuery;
import com.enroll.core.entity.Enrollment;
import com.enroll.core.entity.FormFieldMeta;
import com.enroll.core.entity.FormFieldOption;
import com.enroll.core.entity.FormFieldValue;
import com.enroll.core.entity.FormMeta;
import com.enroll.core.enums.FormFieldType;
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
			fieldValueList.stream().forEach(fieldValue -> {
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
		FormMeta formMeta = new FormMeta();
		BeanUtils.copyProperties(formMetaDTO, formMeta, "formFieldMetaList");
		
		LocalDateTime date = LocalDateTime.now(); 
		formMeta.setCreatedDate(date);
		formMeta.setModifiedDate(date);
		
		// Group form field data
		formMetaDTO.getFormFieldMetaList().stream().forEach(formField -> {
			FormFieldMeta formFieldMeta = new FormFieldMeta();
			BeanUtils.copyProperties(formField, formFieldMeta, "fieldOptionList");
			formMeta.addFormFieldMeta(formFieldMeta);

			// Group the options data if any
			if (!CollectionUtils.isEmpty(formField.getFieldOptionList())) {
				formField.getFieldOptionList().stream().forEach(option -> {
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
		Enrollment enrollment = new Enrollment();
		BeanUtils.copyProperties(enrollmentDTO, enrollment, "fieldValueList");
		LocalDateTime date = LocalDateTime.now(); 
		enrollment.setRegisterId(getRegisterId());
		enrollment.setRegisterDate(date);
		enrollment.setModifiedDate(date);
		
		//Group all the fields that could have options
		FormMeta formMeta = enrollmentDao.readGenericEntity(FormMeta.class, enrollmentDTO.getFormId());
		Map<Long, Map<String, String>> fieldkeyValueMap = new HashMap<>();
		Map<Long, FormFieldMeta> formFieldMap = new HashMap<>();
		formMeta.getFormFieldMetaList().stream().forEach(field -> {
			if (FormFieldType.hasOption(field.getFieldType())) {				
				fieldkeyValueMap.put(field.getFieldId(), field.getFieldOptionMap());
			}
			formFieldMap.put(field.getFieldId(), field);
		});
		
		//Loop to copy and save field value
		enrollmentDTO.getFieldValueList().stream().forEach(value -> {
			FormFieldValue fieldValue = new FormFieldValue();
			BeanUtils.copyProperties(value, fieldValue);
			if (fieldkeyValueMap.containsKey(value.getFieldId())) {
				fieldValue.setFieldDisplay(fieldkeyValueMap.get(value.getFieldId()).get(value.getFieldValue()));
			}
			fieldValue.setFieldtype(formFieldMap.get(value.getFieldId()).getFieldType());
			enrollment.addFieldValue(fieldValue);
		});
		enrollmentDao.save(enrollment);
		enrollmentDTO.setRegisterId(enrollment.getRegisterId());
		return enrollmentDTO.getRegisterId();
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
	
	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
	
	private String getRegisterId() {
		return LocalDateTime.now().format(FORMATTER) + ThreadLocalRandom.current().nextLong(1000000);
	}

	@Override
	public FormMetaDTO findFormMetaPage(FormMetaQuery query) {
		return null;
	}

	@Override
	public EnrollmentDTO findEnrollment(String registrId) {
		Objects.requireNonNull(registrId);
		Enrollment enrollment = enrollmentDao.readGenericEntity(Enrollment.class, registrId);
		EnrollmentDTO enrollmentDTO = new EnrollmentDTO();
		BeanUtils.copyProperties(enrollment, enrollmentDTO, "fieldValueList");
		enrollment.getFieldValueList().stream().forEach(fieldValue -> {
			FormFieldValueDTO fieldValueDTO = new FormFieldValueDTO();
			BeanUtils.copyProperties(fieldValue, fieldValueDTO);
			enrollmentDTO.addFieldValue(fieldValueDTO);
		});
		
		
		return enrollmentDTO;
	}
}