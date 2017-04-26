package com.enroll.security.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.BatchSize;

import com.enroll.security.enums.EntityStatus;
import com.enroll.security.enums.PermissionType;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "TBL_ACCESS_PERMISSION")
public class Permission extends AbstractEntity implements Serializable {
	
	private static final long serialVersionUID = -8143612159499208555L;

	@Id
	@Column(name = "PERMISSION_ID")
	private Long id;

	@Column(name = "NAME", nullable = false)
	private String name;

	@Column(name = "URL", nullable = true)
	private String url;

	@Column(name = "PERMISSION_TYPE", nullable = false)
	private String type;

	@Column(name = "DESCRIPTION", nullable = false)
	private String description;

	@Column(name = "DISPLAY_ORDER", nullable = true)
	private Integer displayOrder;

	@Column(name = "MENU_CSS", nullable = true)
	private String css;

	@Column(name = "MENU_IMG", nullable = true)
	private String img;

	@ManyToMany(fetch = FetchType.EAGER, targetEntity = Role.class)
	@JoinTable(name = "TBL_ROLE_PERMISSION", joinColumns = @JoinColumn(name = "PERMISSION_ID", referencedColumnName = "PERMISSION_ID") , inverseJoinColumns = @JoinColumn(name = "ROLE_ID", referencedColumnName = "ROLE_ID") )
	@BatchSize(size = 50)
	private Set<Role> allRoles = new HashSet<Role>();

	@ManyToOne(fetch = FetchType.EAGER, targetEntity = Permission.class)
	@JoinColumns({ @JoinColumn(name = "PARENT_PERMISION_ID") })
	@BatchSize(size = 50)
	private Permission parentPermission;
	
	@Column(name = "ACTIVE")
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

	public Set<Role> getAllRoles() {
		return allRoles;
	}

	public void setAllRoles(Set<Role> allRoles) {
		this.allRoles = allRoles;
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

	public Permission getParentPermission() {
		return parentPermission;
	}

	public void setParentPermission(Permission parentPermission) {
		this.parentPermission = parentPermission;
	}

	public String getCss() {
		return css;
	}

	public void setCss(String css) {
		this.css = css;
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
		Permission other = (Permission) obj;
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

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public LocalDateTime getCreatedTimestamp() {
		return createdTimestamp;
	}

	public void setCreatedTimestamp(LocalDateTime createdTimestamp) {
		this.createdTimestamp = createdTimestamp;
	}

	public LocalDateTime getModifiedTimestamp() {
		return modifiedTimestamp;
	}

	public void setModifiedTimestamp(LocalDateTime modifiedTimestamp) {
		this.modifiedTimestamp = modifiedTimestamp;
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
}
