package com.enroll.core.dao;

import java.util.List;

import com.enroll.core.dto.FormMetaQuery;
import com.enroll.core.entity.FormFieldValue;
import com.enroll.core.entity.FormMeta;

public interface EnrollmentDao {
	
	public <T> T saveOrUpdate(T object);
	
	public void evict(Object object);
	
	public Object merge(Object object);
	
	/**
	 * Finds a generic entity by a class name and id
	 * 
	 * @param className
	 * @param id
	 * @return the entity
	 */
	public <T> T readGenericEntity(Class<T> clazz, Object id);

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
	public void persist(Object object);

	/**
	 * Remove the entity
	 *
	 * @param object
	 */
	public void remove(Object object);

	public void flush();

	public void clear();

	public boolean sessionContains(Object object);
	
	public List<FormMeta> findFormMetaList(FormMetaQuery query);

}