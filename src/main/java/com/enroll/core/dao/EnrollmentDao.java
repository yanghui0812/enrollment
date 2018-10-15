package com.enroll.core.dao;

import java.util.List;

import com.enroll.core.dto.EnrollmentQuery;
import com.enroll.core.dto.FormMetaQuery;
import com.enroll.core.dto.SearchResult;
import com.enroll.core.entity.Enrollment;
import com.enroll.core.entity.FormFieldValue;
import com.enroll.core.entity.FormMeta;

public interface EnrollmentDao {

	/**
	 * @param object
	 * @return
	 */
	public <T> T saveOrUpdate(T object);

	/**
	 * @param object
	 */
	public void evict(Object object);

	/**
	 * @param object
	 * @return
	 */
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

	/**
	 * @param formId
	 * @param id
	 * @return
	 */
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

	/**
	 * 
	 */
	public void flush();

	/**
	 * 
	 */
	public void clear();

	/**
	 * @param object
	 * @return
	 */
	public boolean sessionContains(Object object);
	
	/**
	 * @param query
	 * @return
	 */
	public List<FormMeta> findFormMetaList(FormMetaQuery query);

	/**
	 * @param criteria
	 * @return
	 */
	public SearchResult<FormMeta> findFormMetaPage(FormMetaQuery query);

	/**
	 * @param criteria
	 * @return
	 */
	public SearchResult<Enrollment> findEnrollmentPage(EnrollmentQuery query);
	
	/**
	 * @param formId
	 * @param fieldId
	 * @param value
	 * @return
	 */
	public List<FormFieldValue> findFormFieldValue(long formId, long fieldId, String value);

}