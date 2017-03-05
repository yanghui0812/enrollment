package com.enroll.core.dao.impl;

import java.util.List;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.enroll.core.dao.EnrollmentDao;
import com.enroll.core.dto.EnrollmentQuery;
import com.enroll.core.dto.FormMetaQuery;
import com.enroll.core.dto.SearchCriteria;
import com.enroll.core.dto.SearchResult;
import com.enroll.core.entity.Enrollment;
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
	public <T> T saveOrUpdate(T object) {
		getEntityManager().saveOrUpdate(object);
		return object;
	}

	public void evict(Object object) {
		getEntityManager().evict(object);
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

	@Override
	public Object merge(Object object) {
		return getEntityManager().merge(object);
	}

	@Override
	public SearchResult<FormMeta> findFormMetaPage(SearchCriteria<FormMetaQuery> searchCriteria) {

		CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Long> countCriteria = builder.createQuery(Long.class);
		Root<FormMeta> root = countCriteria.from(FormMeta.class);
		Predicate predicate = builder.like(root.get("formName"), searchCriteria.getLikeSearchValue());
		countCriteria.select(builder.countDistinct(root));
		countCriteria.where(predicate);
		int count = getEntityManager().createQuery(countCriteria).getSingleResult().intValue();

		CriteriaQuery<FormMeta> criteria = builder.createQuery(FormMeta.class);
		criteria.select(criteria.from(FormMeta.class));
		criteria.where(predicate);
		List<FormMeta> formMetasList = getEntityManager().createQuery(criteria)
				.setFirstResult(searchCriteria.getStart()).setMaxResults(searchCriteria.getPageSize()).getResultList();

		SearchResult<FormMeta> searchResult = new SearchResult<FormMeta>();

		searchResult.addAll(formMetasList);
		searchResult.setRecordsTotal(count);
		return searchResult;
	}

	@Override
	public SearchResult<Enrollment> findEnrollmentPage(SearchCriteria<EnrollmentQuery> query) {
		CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Long> countCriteria = builder.createQuery(Long.class);
		Root<Enrollment> root = countCriteria.from(Enrollment.class);
		Predicate predicate = builder.like(root.get("phoneNumber"), query.getLikeSearchValue());
		countCriteria.select(builder.countDistinct(root));
		countCriteria.where(predicate);
		int count = getEntityManager().createQuery(countCriteria).getSingleResult().intValue();

		CriteriaQuery<Enrollment> criteria = builder.createQuery(Enrollment.class);
		criteria.select(criteria.from(Enrollment.class));
		criteria.where(predicate);
		List<Enrollment> formMetasList = getEntityManager().createQuery(criteria).setFirstResult(query.getStart())
				.setMaxResults(query.getPageSize()).getResultList();

		SearchResult<Enrollment> searchResult = new SearchResult<Enrollment>();

		searchResult.addAll(formMetasList);
		searchResult.setRecordsTotal(count);
		return searchResult;
	}
}