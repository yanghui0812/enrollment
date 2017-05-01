package com.enroll.security.service.impl;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.enroll.security.dao.RoleDao;
import com.enroll.security.dto.RoleDTO;
import com.enroll.security.dto.RoleQuery;
import com.enroll.security.entity.Role;
import com.enroll.security.service.RoleService;

@Service("roleService")
@Transactional
public class RoleServiceImpl implements RoleService {
	
	@Autowired
	private RoleDao roleDao;

	@Override
	public Collection<RoleDTO> findAllRoles(RoleQuery query) {
		Collection<Role> list = roleDao.findRoleList(query);
		Collection<RoleDTO> result = new ArrayList<>();
		list.stream().forEach(role ->{
			RoleDTO dto = new RoleDTO();
			BeanUtils.copyProperties(role, dto, "users", "permissions");
			result.add(dto);
		});
		return result;
	}

	@Override
	public RoleDTO findRoleDTO(String id) {
		// TODO Auto-generated method stub
		return null;
	}
}
