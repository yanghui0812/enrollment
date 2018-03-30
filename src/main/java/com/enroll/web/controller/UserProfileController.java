package com.enroll.web.controller;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.enroll.core.dto.AjaxResult;
import com.enroll.core.enums.AjaxResultStatus;
import com.enroll.security.dto.UserDTO;
import com.enroll.security.service.UserService;
import com.enroll.security.utils.SessionContextHolder;

/**
 * @ClassName UserProfileController
 * @Description
 * @author Leo.yang
 * @Date April 25, 2017 10:06:21 PM
 * @version 1.0.0
 */
@Controller
@RequestMapping("/profiles")
public class UserProfileController {
	
	@Resource(name = "userService")
	private UserService userService;	
	
	@RequestMapping(value = "/myProfile.html", method = RequestMethod.GET)
	public String myProfile(Model model) {
		String userId = SessionContextHolder.getCurrentUserId();
		UserDTO user = userService.findUser(userId);
		model.addAttribute("user", user);
		return "myProfile";
	}	
	
	/**
	 * @param userId
	 * @param password
	 * @param oldPassword
	 * @return AjaxResult
	 */
	@RequestMapping(value = "/changePassword.html", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult<String> changePassword(@NotNull String currentPassword, @NotNull String password, @NotNull String confirmPassword) {
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
	public AjaxResult<String> verifyPassword(@NotNull String userId, @NotNull String password) {
		AjaxResult<String> result = userService.verifyPassword(userId, password);
		return result;
	}
}