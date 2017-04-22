package com.enroll.security.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.enroll.core.dto.SearchResult;
import com.enroll.security.dto.UserQuery;
import com.enroll.security.entity.User;

public interface UserDao extends GenericEntityDao {

	public User readUserByName(String userName) throws DataAccessException;

	public User readUserById(long userId) throws DataAccessException;

	public List<User> readUserByEmail(String email) throws DataAccessException;

	public List<User> readUser(String username, String status) throws DataAccessException;

	public List<User> readAllUsers() throws DataAccessException;
	
	public SearchResult<User> findUserPage(UserQuery userQuery);

}
