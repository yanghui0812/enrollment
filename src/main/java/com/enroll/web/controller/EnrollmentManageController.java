package com.enroll.web.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.enroll.common.AppConstant;
import com.enroll.common.DateUtils;
import com.enroll.core.dto.EnrollmentDTO;
import com.enroll.core.dto.EnrollmentQuery;
import com.enroll.core.dto.FormMetaDTO;
import com.enroll.core.dto.FormMetaQuery;
import com.enroll.core.dto.SearchResult;
import com.enroll.core.enums.FormStatus;
import com.enroll.core.search.Search;
import com.enroll.core.search.SearchCriteria;
import com.enroll.core.service.EnrollmentService;

/**
 * @ClassName EnrollmentManageController
 * @Description Process all the operation related to enrollment
 * @author Leo.yang
 * @Date Mar 5, 2017 10:06:21 PM
 * @version 1.0.0
 */
@Controller
@RequestMapping("/manage")
public class EnrollmentManageController {
	
	private static final Logger LOGGER = LogManager.getLogger(EnrollmentManageController.class);
	
	private static final String VIEW_TYPE = "manage";

	@Resource(name = "enrollmentService")
	private EnrollmentService enrollmentService;
	
	/**
	 * @Description 
	 * View the detailed enrollment information
	 * @param registerId
	 * @param model
	 * @return String
	 */
	@RequestMapping(value = "/enroll.html", method = RequestMethod.GET)
	public String getEnrollment(String registerId, Model model){
		EnrollmentDTO enroll = enrollmentService.findEnrollment(registerId);
		model.addAttribute("enroll", enroll);
		model.addAttribute("viewType", VIEW_TYPE);
		return "enrollDetail";
	}
	
	/**
	 * @Description 
	 * Prepare the design form information for enrollment
	 * @param formId
	 * @param model
	 * @return String
	 */
	@RequestMapping(value = "/enrollForm.html", method = RequestMethod.GET)
	public String previewDesignForm(Long formId, Model model) {
		FormMetaDTO formMetaDTO = enrollmentService.findFormMetaById(formId);
		model.addAttribute("formMeta", formMetaDTO);
		model.addAttribute("viewType", VIEW_TYPE);
		return "previewEnrollForm";
	}

	/**
	 * @Description Go to all the enrollment information with pagination;
	 * @param query
	 * @param result
	 * @param model
	 * @return String
	 */
	@RequestMapping(value = "/enrolls.html", method = RequestMethod.GET)
	public String enrollmentPage(Model model) {
		FormMetaQuery query = new FormMetaQuery();
		query.setSearchFormStatus(FormStatus.PUBLISH.getType());
		List<FormMetaDTO> list = enrollmentService.findFormMetaList(query);
		model.addAttribute("formMetaList", list);
		return "enrollList";
	}

	/**
	 * @Description Search enrollment by search criteria
	 * @param criteria
	 * @return SearchResult<EnrollmentDTO>
	 */
	@RequestMapping(value = "/enrolls", consumes = "application/json; charset=UTF-8", produces = "application/json; charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public SearchResult<EnrollmentDTO> getEnrollmentPage(@RequestBody SearchCriteria criteria) {
		EnrollmentQuery query = new EnrollmentQuery();
		criteria.searchMapping(query);
		SearchResult<EnrollmentDTO> result = enrollmentService.findEnrollmentPage(query);
		return result;
	}

	/**
	 * Export all the enrollment that meet the search criteria;
	 * 
	 * @param criteria
	 * @param response
	 */
	@RequestMapping(value = "/exportEnrolls", method = RequestMethod.POST)
	public void exportEnrollment(String formId, String begin, String end, String search, HttpServletResponse response) {
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "attachment;fileName=enroll_" + LocalDateTime.now().format(DateUtils.YYYY_MM_DD) + ".xlsx");
		EnrollmentQuery query = new EnrollmentQuery();
		if (StringUtils.isNotBlank(formId)) {
			query.setSearchFormId(Long.valueOf(formId));
		}
		if (StringUtils.isNotBlank(begin)) {
			query.setSearchRegisterDate(begin + AppConstant.CURVE + StringUtils.trimToEmpty(search));
		}
		if (StringUtils.isNotBlank(search)) {
			query.setSearch(new Search(search));
		}
		query.setPageSize(Integer.MAX_VALUE);
		XSSFWorkbook book = enrollmentService.exportEnrollment(query);
		try {
			book.write(response.getOutputStream());
			response.getOutputStream().close();
		} catch (IOException e) {
			LOGGER.error("Error happened while exporting enrollment", e);
		}
	}
}