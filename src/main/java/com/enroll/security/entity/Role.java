package com.enroll.security.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.BatchSize;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "TBL_ROLE_INFO")
public class Role extends AbstractEntity implements Serializable {

	private static final long serialVersionUID = 4506540802219160589L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ROLE_ID")
	private Long id;

	@Column(name = "ROLE_NAME", nullable = false)
	private String name;

	@Column(name = "DESCRIPTION", nullable = false)
	private String description;

	@ManyToMany(fetch = FetchType.LAZY, targetEntity = User.class)
	@JoinTable(name = "TBL_USER_ROLE_XREF", joinColumns = @JoinColumn(name = "ROLE_ID", referencedColumnName = "ROLE_ID") , inverseJoinColumns = @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID") )
	@BatchSize(size = 50)
	private Set<User> allUsers = new HashSet<User>();

	
	@ManyToMany(fetch = FetchType.LAZY, targetEntity = Permission.class)
	@JoinTable(name = "TBL_ROLE_PERMISSION_XREF", joinColumns = @JoinColumn(name = "ROLE_ID", referencedColumnName = "ROLE_ID") , inverseJoinColumns = @JoinColumn(name = "PERMISSION_ID", referencedColumnName = "PERMISSION_ID") )
	@BatchSize(size = 50)
	@OrderBy("displayOrder asc")
	private Set<Permission> allPermissions = new HashSet<Permission>();

	public Set<Permission> getAllPermissions() {
		return allPermissions;
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

	public Set<User> getAllUsers() {
		return allUsers;
	}
	
	public void addUser(User user) {
		allUsers.add(user);
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

	public void setAllPermissions(Set<Permission> allPermissions) {
		this.allPermissions = allPermissions;
	}

	public String getMainEntityName() {
		return getName();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
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
		Role other = (Role) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

}