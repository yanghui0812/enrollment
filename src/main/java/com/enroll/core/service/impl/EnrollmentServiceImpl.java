package com.enroll.core.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
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
import com.enroll.core.dto.SearchResult;
import com.enroll.core.entity.Enrollment;
import com.enroll.core.entity.FormFieldMeta;
import com.enroll.core.entity.FormFieldOption;
import com.enroll.core.entity.FormFieldValue;
import com.enroll.core.entity.FormMeta;
import com.enroll.core.enums.AjaxResultStatus;
import com.enroll.core.enums.EnrollmentStatus;
import com.enroll.core.enums.FormFieldType;
import com.enroll.core.enums.FormStatus;
import com.enroll.core.service.EnrollmentService;
import com.enroll.rest.dto.PropertyError;
import com.enroll.rest.dto.RestBasicResult;
import com.enroll.rest.dto.RestErrorResult;
import com.enroll.rest.dto.RestFieldValue;
import com.enroll.rest.dto.RestRequest;
import com.enroll.rest.dto.RestResult;
import com.enroll.rest.enums.RestFieldError;
import com.enroll.rest.enums.RestResultEnum;
import com.enroll.rest.utils.NonceUtils;

@Service("enrollmentService")
@Transactional
public class EnrollmentServiceImpl implements EnrollmentService, AppConstant {

	private static final Logger LOGGER = LogManager.getLogger(EnrollmentService.class);

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
			formFieldMeta.setUniqueKey(formField.getUniqueKey());
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
			formFieldMeta.setRequired(formField.getRequired());
			formMeta.addFormFieldMeta(formFieldMeta);
			
			boolean isApplicantSlot = formField.isSelect() && formField.hasApplicantSlot();
			
			// Group the options data if any
			if (!CollectionUtils.isEmpty(formField.getOptions())) {
				Map<String, FormFieldOption> optionMap = formFieldMeta.getFieldOptionMetaMap();
				formFieldMeta.clearFieldOption();
				for (FormFieldOptionDTO option : formField.getOptions()) {
					FormFieldOption fieldOption = null;
					if (optionMap.containsKey(option.getLabel())) {
						fieldOption = optionMap.get(option.getLabel());
					} else {
						fieldOption = new FormFieldOption();
					}
					
					BeanUtils.copyProperties(option, fieldOption);
					formFieldMeta.addFormFieldOption(fieldOption);
					if (isApplicantSlot) {
						fieldOption.setSlot(option.getValue());
						fieldOption.setValue(fieldOption.getLabel());
						LOGGER.info("The number of applicant for form {} is limited to {} at {}", formMeta.getFormName(), option.getValue(), option.getLabel());
					}
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
		
		StringBuilder sb = new StringBuilder(enrollment.toString());

		// Loop to copy and save field value
		enrollmentDTO.getFieldValueList().stream().forEach(value -> {
			FormFieldValue fieldValue = new FormFieldValue();
			FormFieldMeta fieldMeta = formFieldMap.get(value.getFieldId());
			if (value.getFieldId() != 0) {
				BeanUtils.copyProperties(value, fieldValue);
				if (fieldkeyValueMap.containsKey(value.getFieldId())) {
					fieldValue.setFieldDisplay(getFieldDisplay(value.getFieldId(), value.getFieldValue(), fieldkeyValueMap));
				}
				sb.append(value.getFieldValue());
				sb.append(AppConstant.BLANK);
				fieldValue.setFieldtype(fieldMeta.getType());
				fieldValue.setLabel(fieldMeta.getLabel());
				enrollment.addFieldValue(fieldValue);
			}
		});
		enrollment.setSearch(sb.toString());
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
	public SearchResult<FormMetaDTO> findFormMetaPage(FormMetaQuery query) {
		SearchResult<FormMeta> searchResult = enrollmentDao.findFormMetaPage(query);
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
	public SearchResult<EnrollmentDTO> findEnrollmentPage(EnrollmentQuery query) {
		SearchResult<Enrollment> searchResult = enrollmentDao.findEnrollmentPage(query);
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
		if (EnrollmentStatus.DRAFT.getType().equals(enrollment.getStatus())) {
			enrollment.setStatus(EnrollmentStatus.ENROLL.getType());
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
		if (EnrollmentStatus.DRAFT.getType().equals(enrollment.getStatus()) || EnrollmentStatus.ENROLL.getType().equals(enrollment.getStatus())) {
			enrollment.setStatus(EnrollmentStatus.CANCEL.getType());
		} else {
			result.setAjaxResultStatus(AjaxResultStatus.FAIL);
			result.setMessage("当前状态不能取消");
		}
		return result;
	}

	@Override
	public RestBasicResult saveRestEnrollment(RestRequest request, String registerId) {
		EnrollmentDTO dto = new EnrollmentDTO();
		dto.setFormId(Long.valueOf(request.getFormId()));
		FormMeta formMeta = enrollmentDao.readGenericEntity(FormMeta.class, dto.getFormId());
		if (formMeta == null) {
			RestErrorResult errorResult = new RestErrorResult(RestResultEnum.MALFORMED, NonceUtils.getNonceString());
			errorResult.setFieldErrors(Arrays.asList(new PropertyError(RestFieldError.MISSING_VALUE, "formId")));
			return errorResult;
		}
		List<PropertyError> fieldErrors = new ArrayList<>();
		Map<String, FormFieldMeta> formFieldMap = formMeta.getFormFieldMetaMap();
		
		String slotField = StringUtils.EMPTY;
		for (RestFieldValue restFieldValue : request.getData()) {
		
			String fieldName = StringUtils.trim(restFieldValue.getName());
			if (!(formFieldMap.containsKey(fieldName) || PHONE_NUMBER.equals(fieldName) || APPLICANT_NAME.equals(fieldName)  || STATUS.equals(fieldName))) {
				fieldErrors.add(new PropertyError(RestFieldError.MISSING_FIELD, fieldName));
				break;
			}
			if (PHONE_NUMBER.equals(fieldName)) {
				dto.setPhoneNumber(restFieldValue.getValue());
				continue;
			}
			if (APPLICANT_NAME.equals(fieldName)) {
				dto.setApplicantName(restFieldValue.getValue());
				continue;
			}
			if (STATUS.equals(fieldName)) {
				dto.setStatus(restFieldValue.getValue());
				continue;
			}
			FormFieldMeta fieldMeta = formFieldMap.get(restFieldValue.getName());
			
			if (fieldMeta.hasApplicantSlot()) {
				slotField = restFieldValue.getValue();
			}
			FormFieldValueDTO fieldValue = new FormFieldValueDTO(dto.getFormId(), fieldMeta.getFieldId(), registerId, restFieldValue.getValue(), fieldMeta.getName());
			dto.addFieldValue(fieldValue);
		}
		
		if (!fieldErrors.isEmpty()) {
			RestErrorResult errorResult = new RestErrorResult(RestResultEnum.MALFORMED, NonceUtils.getNonceString());
			errorResult.setFieldErrors(fieldErrors);
			return errorResult;
		}
		dto.setRegisterId(registerId);
		
		//Make sure that the applicant slot is still available;
		if (!isApplicantSlotAvailable(Long.valueOf(request.getFormId()), slotField)) {
			return new RestErrorResult(RestResultEnum.NO_SLOT_AVAILABLE, NonceUtils.getNonceString());
		}
		
		String id = saveEnrollment(dto);
		List<RestFieldValue> list = Arrays.asList(new RestFieldValue(AppConstant.ENROLL_URL, AppConstant.APP_API_ENROLL_PREFIX + id));
		//Create new enrollment
		if (StringUtils.isBlank(registerId)) {
			return new RestResult<List<RestFieldValue>>(RestResultEnum.CREATE_SUCCESS, NonceUtils.getNonceString(), list);
		}
		//Update existing enrollment
		return new RestResult<List<RestFieldValue>>(RestResultEnum.SUCCESS, NonceUtils.getNonceString(), list);
	}

	@Override
	public RestBasicResult saveRestEnrollment(RestRequest request) {
		return saveRestEnrollment(request, null);
	}

	@Override
	public RestBasicResult findApplicantSlot(Long formId) {
		Map<String, String> slotMap = findApplicantSlotMap(formId);
		if (slotMap.isEmpty()) {
			RestErrorResult errorResult = new RestErrorResult(RestResultEnum.MALFORMED, NonceUtils.getNonceString());
			errorResult.setFieldErrors(Arrays.asList(new PropertyError(RestFieldError.INVALID_VALUE, "formId")));
			return errorResult;
		}
		List<RestFieldValue> result = new ArrayList<>();
		slotMap.entrySet().stream().forEach(entry -> {
			result.add(new RestFieldValue(entry.getKey(), entry.getValue()));
		});
		return new RestResult<List<RestFieldValue>>(RestResultEnum.SUCCESS, NonceUtils.getNonceString(), result);
	}
	
	private boolean isApplicantSlotAvailable(Long formId, String fieldValue) {
		if (StringUtils.isBlank(fieldValue) || formId == null) {
			return false;
		}
		Map<String, String> map = findApplicantSlotMap(formId);
		String[] array = map.get(fieldValue).split(COLON);
		return Integer.valueOf(array[0]) > Integer.valueOf(array[1]);
	}
	
	private Map<String, String> findApplicantSlotMap(Long formId) {
		Map<String, String> resultMap = new HashMap<>();
		FormMeta formMeta = enrollmentDao.readGenericEntity(FormMeta.class, formId);
		if (formMeta == null) {
			return resultMap;
		}
				
		//Get the enrollment with enroll status
		SearchResult<Enrollment> searchResult = getAllApplicantsByFormId(formId);
		Map<String, List<FormFieldValue>> map = null;
		
		//Find the number of applicant
		for (FormFieldMeta fieldMeta : formMeta.getFormFieldMetaList()) {
			if (fieldMeta.hasApplicantSlot()) {
				if (map == null) {
					map = searchResult.getData().stream().flatMap(enrollment -> enrollment.getFieldValueList().stream())
							.filter(value -> fieldMeta.getFieldId() == value.getFieldId())
							.collect(Collectors.groupingBy(FormFieldValue::getFieldValue));
				}
				for (FormFieldOption option : fieldMeta.getFieldOptionList()) {
					List<FormFieldValue> fieldValueList = map.get(option.getValue());
					if (CollectionUtils.isEmpty(fieldValueList)) {
						resultMap.put(option.getValue(), option.getSlot() + COLON + 0);
					} else{
						resultMap.put(option.getValue(), option.getSlot() + COLON + fieldValueList.size());
					}
				}
				break;
			}
		}
		return resultMap;
	}
	
	public String findRegisterIdByFormIdAndUniqueKey(long formId, long fieldId, String value) {
		Objects.requireNonNull(value);
		FormFieldValue formFieldValue = enrollmentDao.findFormFieldValue(formId, fieldId, value);
		if (formFieldValue == null) {
			return StringUtils.EMPTY;
		}	
		return formFieldValue.getEnrollment().getRegisterId();
	}
	
	private SearchResult<Enrollment> getAllApplicantsByFormId(long formId) {
		EnrollmentQuery query = new EnrollmentQuery(Integer.MAX_VALUE, 0);
		query.setSearchFormId(formId);
		query.setSearchStatus(EnrollmentStatus.ENROLL.getType());
		return enrollmentDao.findEnrollmentPage(query);
		
	}
	
	public boolean isApplicantSlotAvailable(Map<Long, String> map, long formId) {
		FormMeta formMeta = enrollmentDao.readGenericEntity(FormMeta.class, formId);
		Optional<FormFieldOption> option = formMeta.getFormFieldMetaList().stream().filter(FormFieldMeta::hasApplicantSlot).map(fieldMeta -> {
			Map<String, FormFieldOption> optionMap = fieldMeta.getFieldOptionMetaMap();
			return optionMap.get(map.get(fieldMeta.getFieldId()));
		}).findAny();
		
		if (!option.isPresent()) {
			return true;
		}
		long fieldId = option.get().getFormField().getFieldId();
		String value = map.get(fieldId);
		
		//Get the enrollment with enroll status
		SearchResult<Enrollment> searchResult = getAllApplicantsByFormId(formId);
		long count = searchResult.getData().stream().flatMap(enrollment -> enrollment.getFieldValueList()
				.stream()).filter(fieldValue -> fieldId == fieldValue.getFieldId() && StringUtils.equals(fieldValue.getFieldValue(), value)).count();
		return Long.valueOf(option.get().getSlot()) > count;
	}

	@Override
	public AjaxResult<String> duplicateForm(Long formId) {
		AjaxResult<String> result = new AjaxResult<String>(AjaxResultStatus.SUCCESS);
		FormMeta formMeta = enrollmentDao.readGenericEntity(FormMeta.class, formId);
		if (formMeta == null) {
			result.setAjaxResultStatus(AjaxResultStatus.FAIL);
			result.setMessage("表单不存在");
			return result;
		}
		
		FormMeta newFormMeta = new FormMeta();
		BeanUtils.copyProperties(formMeta, newFormMeta, "formFieldMetaList", "formId", "modifiedDate");
		String formName = LocalDateTime.now().format(DateUtils.YYYYMMDDHHMMSSSSS) + "_" + formMeta.getFormName();
		if (formName.length() > 50) {
			formName = StringUtils.substring(LocalDateTime.now().format(DateUtils.YYYYMMDDHHMMSSSSS) + "_" + formMeta.getFormName(), 0, 50);
		}
		newFormMeta.setFormName(formName);
		newFormMeta.setStatus(FormStatus.DRAFT.getType());
		
		List<FormFieldMeta> newFieldMetaList = formMeta.getFormFieldMetaList().stream().map(fieldMeta -> {
			FormFieldMeta newFieldMeta = new FormFieldMeta();
			BeanUtils.copyProperties(fieldMeta, newFieldMeta, "fieldOptionList", "fieldId");
			fieldMeta.getFieldOptionList().stream().forEach(option -> {
				FormFieldOption newOption = new FormFieldOption();
				BeanUtils.copyProperties(option, newOption);
				newFieldMeta.addFormFieldOption(newOption);
			});
			newFieldMeta.setFormMeta(newFormMeta);
			return newFieldMeta;
		}).collect(Collectors.toList());
		newFormMeta.setFormFieldMetaList(newFieldMetaList);
		enrollmentDao.save(newFormMeta);
		return result;
	}
}