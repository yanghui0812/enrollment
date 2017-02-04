package com.enroll.core.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.enroll.common.AppConstant;
import com.enroll.common.DateUtils;
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
public class EnrollmentServiceImpl implements EnrollmentService, AppConstant {
	
	private static final Logger LOGGER = LogManager.getLogger(EnrollmentService.class);

	@Autowired
	private EnrollmentDao enrollmentDao;

	@Override
	public FormMetaDTO findFormMetaById(long id) {
		FormMeta formMeta = enrollmentDao.readGenericEntity(FormMeta.class, id);
		return transformFormMeta(formMeta, null);
	}

	private EnrollmentDTO transformEnrollmentAndFormMeta(FormMeta formMeta, Enrollment enrollment) {
		FormMetaDTO result = transformFormMeta(formMeta, enrollment);
		EnrollmentDTO enrollmentDTO = new EnrollmentDTO();
		enrollmentDTO.setFormMeta(result);
		BeanUtils.copyProperties(enrollment, enrollmentDTO, "fieldValueList");
		return enrollmentDTO;
	}
	
	private FormMetaDTO transformFormMeta(FormMeta formMeta, Enrollment enrollment) {

		//Put formFieldValue into map with field id as key
		Map<Long, FormFieldValue> map = new HashMap<Long, FormFieldValue>();
		if (enrollment != null) {
			enrollment.getFieldValueList().stream().forEach(fieldValue -> {
				map.put(fieldValue.getFieldId(), fieldValue);
			});
		}
		
		FormMetaDTO result = new FormMetaDTO();
		BeanUtils.copyProperties(formMeta, result, "formFieldMetaList");

		//transform form field data
		formMeta.getFormFieldMetaList().forEach(formField -> {
			FormFieldMetaDTO formFieldMeta = new FormFieldMetaDTO();
			BeanUtils.copyProperties(formField, formFieldMeta, "fieldOptionList");
			result.addFormFieldMeta(formFieldMeta);

			//Fill in the field value if present
			if (map.containsKey(formField.getFieldId())) {
				formFieldMeta.setInputFieldValue(map.get(formField.getFieldId()).getFieldValue());
			}

			//Transform the options data if present
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
		
		Enrollment existing = null;
		if (StringUtils.isBlank(enrollmentDTO.getRegisterId())) {
			enrollmentDTO.setRegisterId(getRegisterId());
			enrollment.setRegisterDate(LocalDateTime.now());
		} else {
			existing = enrollmentDao.readGenericEntity(Enrollment.class, enrollmentDTO.getRegisterId());
			enrollment.setRegisterDate(existing.getRegisterDate());
			enrollment.setModifiedDate(existing.getModifiedDate());
			enrollmentDao.evict(existing);
		}
		enrollment.setRegisterId(enrollmentDTO.getRegisterId());
		
		//Prepare the data of all the fields that could have options
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
				fieldValue.setFieldDisplay(getFieldDisplay(value.getFieldId(), value.getFieldValue(), fieldkeyValueMap));
			}
			fieldValue.setFieldtype(formFieldMap.get(value.getFieldId()).getFieldType());
			enrollment.addFieldValue(fieldValue);
		});
		enrollmentDao.saveOrUpdate(enrollment);
		return enrollmentDTO.getRegisterId();
	}
	
	/**
	 * Group the display of field.
	 * 
	 * @param fieldId
	 * @param value
	 * @param fieldMetakeyValueMap
	 * @return String
	 */
	private String getFieldDisplay(long fieldId, String value, Map<Long, Map<String, String>> fieldMetakeyValueMap) {
		if (!fieldMetakeyValueMap.containsKey(fieldId)) {
			return StringUtils.EMPTY;
		}
		
		if (!StringUtils.contains(value, COMMA)) {
			return fieldMetakeyValueMap.get(fieldId).get(value);
		}
		
		Map<String, String> map = fieldMetakeyValueMap.get(fieldId);
		return map.entrySet().stream().filter(entry -> StringUtils.contains(value, entry.getKey())).map(entry -> entry.getValue()).collect(Collectors.joining(COMMA));
	}

	@Override
	public EnrollmentDTO findFormMetaWithInputValue(String registerId) {
		Enrollment enrollment = enrollmentDao.readGenericEntity(Enrollment.class, registerId);
		FormMeta formMeta = enrollmentDao.readGenericEntity(FormMeta.class, enrollment.getFormId());
		return transformEnrollmentAndFormMeta(formMeta, enrollment);
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
	
	private String getRegisterId() {
		return LocalDateTime.now().format(DateUtils.YYYYMMDDHHMMSSSSS) + ThreadLocalRandom.current().nextLong(1000000);
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