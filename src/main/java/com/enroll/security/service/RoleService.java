package com.enroll.security.service;

import java.util.Collection;

import com.enroll.security.dto.RoleDTO;
import com.enroll.security.dto.RoleQuery;

public interface RoleService {
	
	public Collection<RoleDTO> findAllRoles(RoleQuery query);
	
	public RoleDTO findRoleDTO(String id);
}