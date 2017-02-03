package com.enroll.core.dao.impl;

import java.util.List;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.enroll.core.dao.EnrollmentDao;
import com.enroll.core.dto.FormMetaQuery;
import com.enroll.core.entity.FormFieldValue;
import com.enroll.core.entity.FormMeta;

@Repository
public class EnrollmentDaoImpl implements EnrollmentDao {

	private static final Logger LOGGER = LogManager.getLogger(EnrollmentDao.class);

	@Resource(name = "sessionFactory")
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public Session getEntityManager() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public <T> T save(T object) {
		getEntityManager().persist(object);
		return object;
	}

	@Override
	public void persist(Object object) {
		getEntityManager().persist(object);
	}

	@Override
	public void remove(Object object) {
		getEntityManager().remove(object);
	}
	
	@Override
	public List<FormMeta> findFormMetaList(FormMetaQuery query) {
		EntityManager entityManager = getEntityManager();
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<FormMeta> criteria = builder.createQuery(FormMeta.class);
		Root<FormMeta> root = criteria.from(FormMeta.class);
		criteria.select(root);
		List<FormMeta> list = entityManager.createQuery(criteria).getResultList();
		return list;
	}

	@Override
	public List<FormFieldValue> findFormFieldValueList(long formId, String id) {
		EntityManager entityManager = getEntityManager();
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<FormFieldValue> criteria = builder.createQuery(FormFieldValue.class);
		Root<FormFieldValue> root = criteria.from(FormFieldValue.class);
		criteria.select(root);
		criteria.where(builder.and(builder.equal(root.get("formId"), formId), builder.equal(root.get("idNumber"), id)));
		List<FormFieldValue> list = entityManager.createQuery(criteria).getResultList();
		return list;
	}

	@Override
	public void flush() {
		getEntityManager().flush();
	}

	@Override
	public void clear() {
		getEntityManager().clear();
	}

	@Override
	public boolean sessionContains(Object object) {
		return getEntityManager().contains(object);
	}

	@Override
	public <T> T readGenericEntity(Class<T> clazz, Object id) {
		return getEntityManager().find(clazz, id);
	}
}