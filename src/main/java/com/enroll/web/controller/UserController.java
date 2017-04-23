package com.enroll.web.controller;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.enroll.common.AppConstant;
import com.enroll.core.dto.AjaxResult;
import com.enroll.core.dto.SearchResult;
import com.enroll.core.enums.AjaxResultStatus;
import com.enroll.core.search.SearchCriteria;
import com.enroll.security.dto.UserDTO;
import com.enroll.security.dto.UserQuery;
import com.enroll.security.enums.EntityStatus;
import com.enroll.security.service.UserService;
import com.enroll.security.utils.SessionContextHolder;

/**
 * @ClassName UserController
 * @Description
 * @author Leo.yang
 * @Date Mar 5, 2017 10:06:21 PM
 * @version 1.0.0
 */
@Controller
@RequestMapping("/manage")
public class UserController {
	
	@Resource(name = "userService")
	private UserService userService;
	
	/**
	 * @param userId
	 * @param model
	 * @return String
	 */
	@RequestMapping(value = "/userDetail.html", method = RequestMethod.GET)
	public String userDetailInfo(String userId, Model model) {
		UserDTO user = userService.findUser(userId);
		model.addAttribute("user", user);
		return "userDetail";
	}
	
	@RequestMapping(value = "/myProfile.html", method = RequestMethod.GET)
	public String myProfile(Model model) {
		String userId = SessionContextHolder.getCurrentUserId();
		model.addAttribute("viewType", "myProfile");
		return userDetailInfo(userId, model);
	}
	
	/**
	 * @return String
	 */
	@RequestMapping(value = "/userForm.html", method = RequestMethod.GET)
	public String createUser() {
		return "userForm";
	}
	
	/**
	 * @param user
	 * @param model
	 * @return String
	 */
	@RequestMapping(value = "/user.html", method = RequestMethod.POST)
	public String saveUserInfo(UserDTO user, Model model) {
		userService.saveUser(user);
		return "redirect:/manage/userDetail.html?userId=" + user.getId();
	}
	
	/**
	 * @param userId
	 * @param status
	 * @return AjaxResult
	 */
	@RequestMapping(value = "/disableUser.html", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult<String> disableUser(String userId) {
		return userService.changeUserStatus(userId, EntityStatus.INACTIVE);
	}
	
	/**
	 * @param userId
	 * @param status
	 * @return AjaxResult
	 */
	@RequestMapping(value = "/enableUser.html", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult<String> enableUser(String userId) {
		return userService.changeUserStatus(userId, EntityStatus.ACIVE);
	}
	
	/**
	 * @param userId
	 * @return AjaxResult
	 */
	@RequestMapping(value = "/resetPassword.html", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult<String> resetPassword(String userId) {
		AjaxResult<String> result = userService.changeUserPassword(userId, AppConstant.TRUE);
		return result;
	}
	
	/**
	 * @param userId
	 * @param password
	 * @param oldPassword
	 * @return AjaxResult
	 */
	@RequestMapping(value = "/changePassword.html", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult<String> changePassword(String currentPassword, String password, String confirmPassword) {
		String userId = SessionContextHolder.getCurrentUserId();
		AjaxResult<String> result = null;
		if (!StringUtils.equals(password, confirmPassword)) {
			result = new AjaxResult<String>(AjaxResultStatus.FAIL);
			result.setMessage("两次输入的新密码不一致");
			return result;
		}
		result = userService.changeUserPassword(userId, password, currentPassword);
		return result;
	}
	
	/**
	 * @param userId
	 * @param password
	 * @return AjaxResult
	 */
	@RequestMapping(value = "/verifyPassword.html", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult<String> verifyPassword(String userId, String password) {
		AjaxResult<String> result = userService.verifyPassword(userId, password);
		return result;
	}
	
	/**
	 * @param userId
	 * @param password
	 * @return AjaxResult
	 */
	@RequestMapping(value = "/verifyUsername.html", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult<String> verifyUsername(String username) {
		UserDTO user = userService.findUserByName(username);
		if (user == null) {
			return new AjaxResult<String>(AjaxResultStatus.SUCCESS);
		}
		return new AjaxResult<String>(AjaxResultStatus.FAIL);
	}

	/**
	 * @param model
	 * @return String
	 */
	@RequestMapping(value = "/users.html", method = RequestMethod.GET)
	public String userPage(Model model) {
		return "userList";
	}

	/**
	 * @param criteria
	 * @return SearchResult<UserDTO>
	 */
	@RequestMapping(value = "/users", consumes = "application/json; charset=UTF-8", produces = "application/json; charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public SearchResult<UserDTO> getUserPage(@RequestBody SearchCriteria criteria) {
		UserQuery query = new UserQuery();
		SearchResult<UserDTO> list = userService.findUserPage(query);
		return list;
	}
}