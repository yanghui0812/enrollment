package com.enroll.security.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


public class RoleDTO implements Serializable {
	
	private static final long serialVersionUID = -3207559762533276744L;

	private Long id;

	private String name;

	private String description;
	
	private Set<UserDTO> users = new HashSet<UserDTO>();
	
	private Set<PermissionDTO> permissions = new HashSet<PermissionDTO>();

	public RoleDTO() {
		super();
	}

	public RoleDTO(Long id) {
		super();
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getMainEntityName() {
		return getName();
	}
	
	public void addUser(UserDTO user) {
		users.add(user);
	}	

	public Set<UserDTO> getUsers() {
		return users;
	}

	public Set<PermissionDTO> getPermissions() {
		return permissions;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RoleDTO other = (RoleDTO) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}