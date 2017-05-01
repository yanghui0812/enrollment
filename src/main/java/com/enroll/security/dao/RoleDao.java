package com.enroll.security.dao;

import java.util.Collection;

import com.enroll.core.dto.SearchResult;
import com.enroll.security.dto.RoleQuery;
import com.enroll.security.entity.Role;

public interface RoleDao extends GenericEntityDao {
	
	public SearchResult<Role> findRolePage(RoleQuery query);
	
	public Collection<Role> findRoleList(RoleQuery query);

}
