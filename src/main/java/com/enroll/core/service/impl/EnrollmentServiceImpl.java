package com.enroll.core.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import org.apache.commons.collections4.MapUtils;
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
import com.enroll.core.dto.AjaxResult;
import com.enroll.core.dto.EnrollmentDTO;
import com.enroll.core.dto.EnrollmentQuery;
import com.enroll.core.dto.FormFieldMetaDTO;
import com.enroll.core.dto.FormFieldOptionDTO;
import com.enroll.core.dto.FormFieldValueDTO;
import com.enroll.core.dto.FormMetaDTO;
import com.enroll.core.dto.FormMetaQuery;
import com.enroll.core.dto.SearchCriteria;
import com.enroll.core.dto.SearchResult;
import com.enroll.core.entity.Enrollment;
import com.enroll.core.entity.FormFieldMeta;
import com.enroll.core.entity.FormFieldOption;
import com.enroll.core.entity.FormFieldValue;
import com.enroll.core.entity.FormMeta;
import com.enroll.core.enums.AjaxResultStatus;
import com.enroll.core.enums.EnrollStatus;
import com.enroll.core.enums.FormFieldType;
import com.enroll.core.enums.FormStatus;
import com.enroll.core.service.EnrollmentService;
import com.enroll.rest.dto.PropertyError;
import com.enroll.rest.dto.RestBasicResult;
import com.enroll.rest.dto.RestErrorResult;
import com.enroll.rest.dto.RestRequest;
import com.enroll.rest.dto.RestResult;
import com.enroll.rest.enums.RestFieldError;
import com.enroll.rest.enums.RestResultEnum;

@Service("enrollmentService")
@Transactional
public class EnrollmentServiceImpl implements EnrollmentService, AppConstant {

	private static final Logger LOGGER = LogManager.getLogger(EnrollmentService.class);
	
	private static final String PHONE_NUMBER = "phoneNumber";
	
	private static final String APPLICANT_NAME = "applicantName";
	
	private static final String STATUS = "status";

	@Autowired
	private EnrollmentDao enrollmentDao;

	@Override
	public FormMetaDTO findFormMetaById(long id) {
		FormMeta formMeta = enrollmentDao.readGenericEntity(FormMeta.class, id);
		if (formMeta == null) {
			return null;
		}
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

		// Put formFieldValue into map with field id as key
		Map<Long, FormFieldValue> map = new HashMap<Long, FormFieldValue>();
		if (enrollment != null) {
			enrollment.getFieldValueList().stream().forEach(fieldValue -> {
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

			// Fill in the field value if present
			if (map.containsKey(formField.getFieldId())) {
				formFieldMeta.setInputFieldValue(map.get(formField.getFieldId()).getFieldValue());
			}

			// Transform the options data if present
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
		FormMeta formMeta = null;
		LocalDateTime date = LocalDateTime.now();
		@SuppressWarnings("unchecked")
		Map<String, FormFieldMeta> formFieldMap = MapUtils.EMPTY_SORTED_MAP;
		if (formMetaDTO.getFormId() > 0) {
			formMeta = enrollmentDao.readGenericEntity(FormMeta.class, formMetaDTO.getFormId());
			formFieldMap = formMeta.getFormFieldMetaMap();
			formMeta.clearAllFormFieldMeta();
			LOGGER.info("Remove all the form field metadata from {}", formMetaDTO.getFormId());
		} else {
			formMeta = new FormMeta();
			formMeta.setCreatedDate(date);
		}
		BeanUtils.copyProperties(formMetaDTO, formMeta, "formFieldMetaList", "createdDate");

		// Group form field data
		for (FormFieldMetaDTO formField : formMetaDTO.getFields()) {
			String fieldName = StringUtils.trim(formField.getName());
			FormFieldMeta formFieldMeta = null;
			if (formFieldMap.containsKey(fieldName)) {
				formFieldMeta = formFieldMap.get(fieldName);
			} else {
				formFieldMeta = new FormFieldMeta();
			}
			BeanUtils.copyProperties(formField, formFieldMeta, "options", "fieldId");
			formMeta.addFormFieldMeta(formFieldMeta);

			// Group the options data if any
			if (!CollectionUtils.isEmpty(formField.getOptions())) {
				for (FormFieldOptionDTO option : formField.getOptions()) {
					FormFieldOption fieldOption = new FormFieldOption();
					BeanUtils.copyProperties(option, fieldOption);
					formFieldMeta.addFormFieldOption(fieldOption);
					if (StringUtils.isBlank(option.getValue())) {
						fieldOption.setValue(String.valueOf(formFieldMeta.getSizeOfFieldOptions()));
					}
				}
			}
			if (LOGGER.isDebugEnabled()) {
				LOGGER.info("Form {} has field metadata {}", formMetaDTO.getFormId(), formFieldMeta.toString());
			}
		}
		enrollmentDao.saveOrUpdate(formMeta);
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

		// Prepare the data of all the fields that could have options
		FormMeta formMeta = enrollmentDao.readGenericEntity(FormMeta.class, enrollmentDTO.getFormId());
		Map<Long, Map<String, String>> fieldkeyValueMap = new HashMap<>();
		Map<Long, FormFieldMeta> formFieldMap = new HashMap<>();
		formMeta.getFormFieldMetaList().stream().forEach(field -> {
			if (FormFieldType.hasOption(field.getType())) {
				fieldkeyValueMap.put(field.getFieldId(), field.getFieldOptionMap());
			}
			formFieldMap.put(field.getFieldId(), field);
		});

		// Loop to copy and save field value
		enrollmentDTO.getFieldValueList().stream().forEach(value -> {
			FormFieldValue fieldValue = new FormFieldValue();
			if (value.getFieldId() != 0) {
				BeanUtils.copyProperties(value, fieldValue);
				if (fieldkeyValueMap.containsKey(value.getFieldId())) {
					fieldValue.setFieldDisplay(getFieldDisplay(value.getFieldId(), value.getFieldValue(), fieldkeyValueMap));
				}
				fieldValue.setFieldtype(formFieldMap.get(value.getFieldId()).getType());
				enrollment.addFieldValue(fieldValue);
			}
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
		return map.entrySet().stream().filter(entry -> StringUtils.contains(value, entry.getKey()))
				.map(entry -> entry.getValue()).collect(Collectors.joining(COMMA));
	}

	@Override
	public EnrollmentDTO findFormMetaWithInputValue(String registerId) {
		Enrollment enrollment = enrollmentDao.readGenericEntity(Enrollment.class, registerId);
		FormMeta formMeta = enrollmentDao.readGenericEntity(FormMeta.class, enrollment.getFormId());
		return transformEnrollmentAndFormMeta(formMeta, enrollment);
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

	@Override
	public SearchResult<FormMetaDTO> findFormMetaPage(SearchCriteria<FormMetaQuery> searchCriteria) {
		SearchResult<FormMeta> searchResult = enrollmentDao.findFormMetaPage(searchCriteria);
		SearchResult<FormMetaDTO> result = new SearchResult<FormMetaDTO>();

		BeanUtils.copyProperties(searchResult, result, "data");

		searchResult.getData().stream().forEach(formMeta -> {
			FormMetaDTO dto = new FormMetaDTO();
			BeanUtils.copyProperties(formMeta, dto, "rawJson");
			result.addElement(dto);
		});

		result.setDraw(searchResult.getDraw());
		result.setRecordsFiltered(searchResult.getRecordsFiltered());
		result.setRecordsTotal(searchResult.getRecordsTotal());
		return result;
	}

	@Override
	public SearchResult<EnrollmentDTO> findEnrollmentPage(SearchCriteria<EnrollmentQuery> criteria) {
		SearchResult<Enrollment> searchResult = enrollmentDao.findEnrollmentPage(criteria);
		SearchResult<EnrollmentDTO> result = new SearchResult<EnrollmentDTO>();
		BeanUtils.copyProperties(searchResult, result, "data");
		searchResult.getData().stream().forEach(enrollment -> {
			EnrollmentDTO dto = new EnrollmentDTO();
			BeanUtils.copyProperties(enrollment, dto, "fieldValueList");
			result.addElement(dto);
		});

		result.setDraw(searchResult.getDraw());
		result.setRecordsFiltered(searchResult.getRecordsFiltered());
		result.setRecordsTotal(searchResult.getRecordsTotal());
		return result;
	}

	@Override
	public AjaxResult<String> publishForm(Long formId) {
		AjaxResult<String> result = new AjaxResult<String>(AjaxResultStatus.SUCCESS);
		FormMeta formMeta = enrollmentDao.readGenericEntity(FormMeta.class, formId);
		if (!FormStatus.DRAFT.getType().equals(formMeta.getStatus())) {
			result.setAjaxResultStatus(AjaxResultStatus.FAIL);
			result.setMessage("表单当前状态不支持发布操作");
			return result;
		}
		formMeta.setStatus(FormStatus.PUBLISH.getType());
		return result;
	}

	@Override
	public AjaxResult<String> inactiveForm(Long formId) {
		AjaxResult<String> result = new AjaxResult<String>(AjaxResultStatus.SUCCESS);
		FormMeta formMeta = enrollmentDao.readGenericEntity(FormMeta.class, formId);
		formMeta.setStatus(FormStatus.INACTIVE.getType());
		return result;
	}

	@Override
	public AjaxResult<String> confirmEnrollment(String registerId) {
		AjaxResult<String> result = new AjaxResult<String>(AjaxResultStatus.SUCCESS);
		Enrollment enrollment = enrollmentDao.readGenericEntity(Enrollment.class, registerId);
		if (EnrollStatus.DRAFT.getType().equals(enrollment.getStatus())) {
			enrollment.setStatus(EnrollStatus.ENROLL.getType());
		} else {
			result.setAjaxResultStatus(AjaxResultStatus.FAIL);
			result.setMessage("当前状态不能取消");
		}
		return result;
	}

	@Override
	public AjaxResult<String> cancelEnrollment(String registerId) {
		AjaxResult<String> result = new AjaxResult<String>(AjaxResultStatus.SUCCESS);
		Enrollment enrollment = enrollmentDao.readGenericEntity(Enrollment.class, registerId);
		if (EnrollStatus.DRAFT.getType().equals(enrollment.getStatus()) || EnrollStatus.ENROLL.getType().equals(enrollment.getStatus())) {
			enrollment.setStatus(EnrollStatus.CANCEL.getType());
		} else {
			result.setAjaxResultStatus(AjaxResultStatus.FAIL);
			result.setMessage("当前状态不能取消");
		}
		return result;
	}

	@Override
	public RestBasicResult saveRestEnrollment(RestRequest request, String registerId) {
		FormMeta formMeta = enrollmentDao.readGenericEntity(FormMeta.class, request.getFormId());
		if (formMeta == null) {
			RestErrorResult errorResult = new RestErrorResult(RestResultEnum.MALFORMED);
			errorResult.setFieldErrors(Arrays.asList(new PropertyError(RestFieldError.MISSING_VALUE, "formId")));
			return errorResult;
		}
		List<PropertyError> fieldErrors = new ArrayList<>();
		Map<String, FormFieldMeta> formFieldMap = formMeta.getFormFieldMetaMap();
		EnrollmentDTO dto = new EnrollmentDTO();
		dto.setFormId(request.getFormId());
		request.getData().stream().forEach(restFieldValue -> {
			String fieldName = StringUtils.trim(restFieldValue.getName());
			if (!(formFieldMap.containsKey(fieldName) || PHONE_NUMBER.equals(fieldName) || APPLICANT_NAME.equals(fieldName)  || STATUS.equals(fieldName))) {
				fieldErrors.add(new PropertyError(RestFieldError.MISSING_FIELD, fieldName));
				return;
			}
			if (PHONE_NUMBER.equals(fieldName)) {
				dto.setPhoneNumber(restFieldValue.getValue());
				return;
			}
			if (APPLICANT_NAME.equals(fieldName)) {
				dto.setApplicantName(restFieldValue.getValue());
				return;
			}
			if (STATUS.equals(fieldName)) {
				dto.setStatus(restFieldValue.getValue());
				return;
			}
			FormFieldMeta fieldMeta = formFieldMap.get(restFieldValue.getName());
			FormFieldValueDTO fieldValue = new FormFieldValueDTO(request.getFormId(), fieldMeta.getFieldId(), registerId, restFieldValue.getValue());
			dto.addFieldValue(fieldValue);
		});
		
		if (!fieldErrors.isEmpty()) {
			RestErrorResult errorResult = new RestErrorResult(RestResultEnum.MALFORMED);
			errorResult.setFieldErrors(fieldErrors);
			return errorResult;
		}
		
		String id = saveEnrollment(dto);
		RestResult<String> restResult = new RestResult<String>(RestResultEnum.SUCCESS, AppConstant.APP_API_ENROLL_PREFIX + id);
		return restResult;
	}

	@Override
	public RestBasicResult saveRestEnrollment(RestRequest request) {
		return saveRestEnrollment(request, null);
	}
}