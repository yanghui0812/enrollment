package com.enroll.security.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.enroll.security.enums.EntityStatus;
import com.enroll.security.enums.PermissionType;

public class PermissionDTO implements Serializable {	

	private static final long serialVersionUID = 8916704785145033860L;

	private Long id;

	private String name;

	private String url;

	private String type;

	private String description;

	private Integer displayOrder;

	private String css;

	private String img;

	private Set<RoleDTO> allRoles = new HashSet<RoleDTO>();

	private PermissionDTO parentPermission;
	
	private String active = EntityStatus.ACIVE.getKey();

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

	public PermissionType getType() {
		return PermissionType.getPermissionType(type);
	}

	public void setType(PermissionType type) {
		if (type != null) {
			this.type = type.getKey();
		}
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(Integer displayOrder) {
		this.displayOrder = displayOrder;
	}

	public PermissionDTO getParentPermission() {
		return parentPermission;
	}

	public void setParentPermission(PermissionDTO parentPermission) {
		this.parentPermission = parentPermission;
	}

	public String getCss() {
		return css;
	}

	public void setCss(String css) {
		this.css = css;
	}
	
	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((displayOrder == null) ? 0 : displayOrder.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + ((url == null) ? 0 : url.hashCode());
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
		PermissionDTO other = (PermissionDTO) obj;
		if (displayOrder == null) {
			if (other.displayOrder != null)
				return false;
		} else if (!displayOrder.equals(other.displayOrder))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		return true;
	}
}