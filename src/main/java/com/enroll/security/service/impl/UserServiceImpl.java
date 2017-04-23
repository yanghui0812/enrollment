package com.enroll.security.service.impl;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.enroll.core.dto.AjaxResult;
import com.enroll.core.dto.SearchResult;
import com.enroll.core.enums.AjaxResultStatus;
import com.enroll.security.dao.UserDao;
import com.enroll.security.dto.UserDTO;
import com.enroll.security.dto.UserQuery;
import com.enroll.security.entity.User;
import com.enroll.security.enums.EntityStatus;
import com.enroll.security.service.UserService;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {

	@Resource(name = "userDao")
	private UserDao userDao;

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public AjaxResult<String> saveUser(UserDTO userDTO) {
		String message = "";
		boolean result = true;
		User user = userDao.readUserById(userDTO.getId());
		String newPassWord = passwordEncoder.encode(userDTO.getPassword());
		if (newPassWord.equals(user.getPassword())) {
			message = "修改的密码与初始密码相同,请重新输入密码！";
			result = false;
		} else {
			user.setPassword(newPassWord);
			userDao.save(user);
		}
		AjaxResult<String> ajax = new AjaxResult<String>(AjaxResultStatus.SUCCESS); 
		return ajax;
	}

	@Override
	public UserDTO findUser(String id) {
		UserDTO userDTO = new UserDTO();
		User user = userDao.readGenericEntity(User.class, id);
		BeanUtils.copyProperties(user, userDTO);
		return userDTO;
	}

	@Override
	public AjaxResult<String> changeUserStatus(String id, EntityStatus status) {
		User user = userDao.readGenericEntity(User.class, id);
		user.setActive(status.getKey());
		AjaxResult<String> ajaxResult = new AjaxResult<String>(AjaxResultStatus.SUCCESS);
		return ajaxResult;
	}

	@Override
	public SearchResult<UserDTO> findUserPage(UserQuery query) {
		SearchResult<User> searchResult = userDao.findUserPage(query);
		SearchResult<UserDTO> result = new SearchResult<UserDTO>();
		searchResult.getData().stream().forEach(user -> {
			UserDTO dto = new UserDTO();
			BeanUtils.copyProperties(user, dto);
			result.addElement(dto);
		});
		result.setDraw(searchResult.getDraw());
		result.setRecordsFiltered(searchResult.getRecordsFiltered());
		result.setRecordsTotal(searchResult.getRecordsTotal());
		return result;
	}

	@Override
	public AjaxResult<String> changeUserPassword(String id, String password) {
		return changeUserPassword(id, password, null);
	}

	@Override
	public AjaxResult<String> verifyPassword(String id, String password) {
		return null;
	}

	@Override
	public AjaxResult<String> changeUserPassword(String id, String password, String oldPassword) {
		User user = userDao.readUserById(id);
		AjaxResult<String> result = null;
		if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
			result = new AjaxResult<String>(AjaxResultStatus.FAIL);
			result.setMessage("原密码不正确");
			return result;
		}
		user.setPassword(passwordEncoder.encode(password));
		result = new AjaxResult<String>(AjaxResultStatus.SUCCESS);
		result.setMessage("密码修改成功");
		return result;
	}

	@Override
	public UserDTO findUserByName(String userName) {
		User user = userDao.readUserByName(userName);
		if (user == null) {
			return null;
		}
		UserDTO dto = new UserDTO();
		BeanUtils.copyProperties(user, dto);
		return dto;
	}
}