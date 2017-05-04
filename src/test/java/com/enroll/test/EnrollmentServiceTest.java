
package com.enroll.test;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.enroll.core.dto.FormFieldMetaDTO;
import com.enroll.core.dto.FormFieldOptionDTO;
import com.enroll.core.dto.FormMetaDTO;
import com.enroll.core.enums.FormFieldType;
import com.enroll.core.service.EnrollmentService;

import config.ApplicationContext;

@WebAppConfiguration
@ContextConfiguration(classes = ApplicationContext.class)
@Test(singleThreaded = true)
public class EnrollmentServiceTest extends AbstractTestNGSpringContextTests {

	@Autowired
	private EnrollmentService enrollmentService;

	private FormMetaDTO formMeta;
	
	private String id = System.currentTimeMillis() + StringUtils.EMPTY;

	@Test(priority = 1)
	public void testSaveFormMeta() {
		formMeta = new FormMetaDTO();
		long currentTime = System.currentTimeMillis();
		formMeta.setFormName("setFormName" + currentTime);
		formMeta.setFormDescription("setFormDescription" + currentTime);

		// Add a text input
		FormFieldMetaDTO textFieldMeta = new FormFieldMetaDTO();
		formMeta.addFormFieldMeta(textFieldMeta);
		textFieldMeta.setName("text " + currentTime);
		textFieldMeta.setPosition(1);
		textFieldMeta.setType(FormFieldType.TEXT.getType());
		//textFieldMeta.setFieldConstraint("textConstraint" + currentTime);
		textFieldMeta.setFieldDefaultValue("1000" + currentTime);
		textFieldMeta.setClassName("textStyle" + currentTime);

		// Add a select
		FormFieldMetaDTO selectField = new FormFieldMetaDTO();
		formMeta.addFormFieldMeta(selectField);
		selectField.setName("fieldName" + currentTime);
		selectField.setPosition(2);
		selectField.setType(FormFieldType.SELECT.getType());
		//selectField.setFieldConstraint("fieldConstraint" + currentTime);
		selectField.setFieldDefaultValue("1000" + currentTime);
		selectField.setClassName("fieldStyle" + currentTime);

		FormFieldOptionDTO optionOne = new FormFieldOptionDTO();
		selectField.addFieldOption(optionOne);
		optionOne.setPosition(1);
		optionOne.setValue("option 1");
		optionOne.setDisplay("display 1");
		optionOne.setDescription("option description 1");

		FormFieldOptionDTO optionTwo = new FormFieldOptionDTO();
		selectField.addFieldOption(optionTwo);
		optionTwo.setPosition(2);
		optionTwo.setValue("option 2");
		optionTwo.setDisplay("display 2");
		optionTwo.setDescription("option description 2");

		FormFieldOptionDTO optionThree = new FormFieldOptionDTO();
		selectField.addFieldOption(optionThree);
		optionThree.setPosition(3);
		optionThree.setValue("option 3");
		optionThree.setDisplay("display 3");
		optionThree.setDescription("option description 3");

		formMeta = enrollmentService.saveFormMeta(formMeta);
		Assert.assertTrue(formMeta.getFormId() > 0);
	}

	@Test(priority = 2)
	public void testFindFormMeta() {
		FormMetaDTO dto = enrollmentService.findFormMetaById(formMeta.getFormId());
		Assert.assertTrue(dto.getFields().size() == 2); 
		Assert.assertTrue(dto.equals(formMeta));
		formMeta = dto;
	}
	
	/*@Test(priority = 3)
	public void testSaveFormInputValue() {
		List<FormFieldValueDTO> list = new ArrayList<FormFieldValueDTO>();
		formMeta.getFormFieldMetaList().forEach(field -> {
			if (FormFieldType.TEXT.getType().equals(field.getFieldType())) {
				list.add(new FormFieldValueDTO(formMeta.getFormId(), field.getFieldId(), id, field.getFieldId() + " fieldvalue"));
			} else if (FormFieldType.SELECT.getType().equals(field.getFieldType())) {
				list.add(new FormFieldValueDTO(formMeta.getFormId(), field.getFieldId(), id, field.getFieldOptionList().get(1).getValue()));
			} else if (FormFieldType.CHECKBOX.getType().equals(field.getFieldType())) {
				list.add(new FormFieldValueDTO(formMeta.getFormId(), field.getFieldId(), id, field.getFieldOptionList().get(0).getValue()));
			}
		});
		
		int count = enrollmentService.saveFormInputFieldvalues(list);
		Assert.assertTrue(count > 0);
	}*/
	
	@Test(priority = 4)
	public void testFindFormMetaWithInputvalue() {
		/*FormMetaDTO dto = enrollmentService.findFormMetaWithInputValue(formMeta.getFormId(), id);
		Assert.assertTrue(dto.getFormFieldMetaList().size() == 2); 
		dto.getFormFieldMetaList().forEach(field -> {
			if (FormFieldType.TEXT.getType().equals(field.getFieldType())) {
				Assert.assertEquals(field.getFieldValue(), field.getFieldId() + " fieldvalue");
			} else if (FormFieldType.SELECT.getType().equals(field.getFieldType())) {
				Assert.assertEquals(field.getFieldValue(), field.getFieldOptionList().get(1).getValue());
			} else if (FormFieldType.CHECKBOX.getType().equals(field.getFieldType())) {
				Assert.assertEquals(field.getFieldValue(), field.getFieldOptionList().get(0).getValue());
			}
		});*/
	}
}