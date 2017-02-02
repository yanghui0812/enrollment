package com.enroll.core.dao;

import java.util.List;

import com.enroll.core.entity.FormFieldValue;

public interface EnrollmentDao {
	
	/**
	 * Finds a generic entity by a classname and id
	 * 
	 * @param className
	 * @param id
	 * @return the entity
	 */
	public <T> T readGenericEntity(Class<T> clazz, Long id);

	/**
	 * Saves a generic entity
	 * 
	 * @param object
	 * @return the persisted version of the entity
	 */
	public <T> T save(T object);
	
	
	public List<FormFieldValue> findFormFieldValueList(long formId, String id);

	/**
	 * Persist the new entity
	 *
	 * @param object
	 */
	void persist(Object object);

	/**
	 * Remove the entity
	 *
	 * @param object
	 */
	void remove(Object object);

	void flush();

	void clear();

	boolean sessionContains(Object object);

}