package com.enroll.security.entity;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Version;

import org.hibernate.annotations.BatchSize;

import com.enroll.security.enums.PermissionType;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "TBL_ACCESS_PERMISSION")
public class Permission implements Serializable {
	
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

	@Column(name = "CREATE_USER_ID", nullable = false)
	private Long createuserId;

	@Column(name = "CREATE_USER_NAME")
	private String createUser;

	@Column(name = "MODIFY_USER_ID")
	private Long modifyUserId;// 修改用户编号

	@Column(name = "MODIFY_USER_NAME")
	private String modifyUser;
	
	@Column(name = "ACTIVE")
	private String active;
	
	@Column(name = "CREATE_TIMESTAMP", updatable = false)
	private Date createTimestamp;

	@Version
	@Column(name = "MODIFY_TIMESTAMP")
	private Date modifyTimestamp;	

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

	public Long getCreateuserId() {
		return createuserId;
	}

	public void setCreateuserId(Long createuserId) {
		this.createuserId = createuserId;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Long getModifyUserId() {
		return modifyUserId;
	}

	public void setModifyUserId(Long modifyUserId) {
		this.modifyUserId = modifyUserId;
	}

	public String getModifyUser() {
		return modifyUser;
	}

	public void setModifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
	}

	public Date getCreateTimestamp() {
		return createTimestamp;
	}

	public void setCreateTimestamp(Date createTimestamp) {
		this.createTimestamp = createTimestamp;
	}

	public Date getModifyTimestamp() {
		return modifyTimestamp;
	}

	public void setModifyTimestamp(Date modifyTimestamp) {
		this.modifyTimestamp = modifyTimestamp;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
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
}
