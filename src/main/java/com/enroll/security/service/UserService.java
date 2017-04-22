package com.enroll.security.service;

import com.enroll.core.dto.AjaxResult;
import com.enroll.core.dto.SearchResult;
import com.enroll.security.dto.UserDTO;
import com.enroll.security.dto.UserQuery;
import com.enroll.security.enums.EntityStatus;

public interface UserService {
	
	public UserDTO findUserByName(String userName);

	/**
	 * 保存
	 * @param userDTO
	 * @return
	 */
	public AjaxResult<String> saveUser(UserDTO userDTO);
	
	/**
	 * 分页查询用户
	 * @param searchCriteria
	 * @return
	 */
	public SearchResult<UserDTO> findUserPage(UserQuery query);
	
	/**
	 * 查询用户
	 * @param userId
	 * @return
	 */
	public UserDTO findUser(long id);
	
	/**
	 * @param id
	 * @return
	 */
	public AjaxResult<String> changeUserStatus(long id, EntityStatus status);
	
	/**
	 * @param id
	 * @param password
	 * @return
	 */
	public AjaxResult<String> changeUserPassword(long id, String password);
	
	/**
	 * @param id
	 * @param password
	 * @param oldPassword
	 * @return
	 */
	public AjaxResult<String> changeUserPassword(long id, String password, String oldPassword);
	
	/**
	 * @param id
	 * @param password
	 * @return
	 */
	public AjaxResult<String> verifyPassword(long id, String password);	
}