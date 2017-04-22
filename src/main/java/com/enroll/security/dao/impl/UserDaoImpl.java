package com.enroll.security.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.enroll.core.dto.SearchResult;
import com.enroll.core.search.SearchOrder;
import com.enroll.security.dao.UserDao;
import com.enroll.security.dto.UserQuery;
import com.enroll.security.entity.User;

@Repository("userDao")
public class UserDaoImpl extends GenericEntityDaoImpl implements UserDao {
	
	public User readUserByName(String userName) throws DataAccessException {
		CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<User> criteria = builder.createQuery(User.class);
		Root<User> root = criteria.from(User.class);
		criteria.select(root);
		criteria.where(builder.equal(root.get("name"), userName));
		User user = getEntityManager().createQuery(criteria).getSingleResult();
		return user;
	}	
	
	public List<User> readUser(String username, String status) throws DataAccessException {
		CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<User> criteria = builder.createQuery(User.class);
		Root<User> root = criteria.from(User.class);
		criteria.select(root);
		criteria.where(builder.and(builder.equal(root.get("name"), username), builder.equal(root.get("status"), status.trim())));
		return getEntityManager().createQuery(criteria).getResultList();
	}

	@Override
	public List<User> readAllUsers() throws DataAccessException {
		return null;
	}

	@Override
	public List<User> readUserByEmail(String email) throws DataAccessException {
		return null;
	}

	@Override
	public SearchResult<User> findUserPage(UserQuery query) {
		CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Long> countCriteria = builder.createQuery(Long.class);
		Root<User> root = countCriteria.from(User.class);
		Expression<String> empty = builder.literal(StringUtils.EMPTY);
		Predicate predicate = builder.equal(empty, empty);

		if (query.getName() != null && StringUtils.isNotBlank(query.getName().getData())) {
			predicate = builder.and(predicate, builder.equal(root.get("name"), query.getName().getData()));
		}
		if (query.getStatus() != null && StringUtils.isNotBlank(query.getStatus().getData())) {
			predicate = builder.and(predicate, builder.equal(root.get("status"), query.getStatus().getData()));
		}
		
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("User search parameter ", query);
		}

		countCriteria.select(builder.countDistinct(root));
		countCriteria.where(predicate);
		int count = getEntityManager().createQuery(countCriteria).getSingleResult().intValue();

		CriteriaQuery<User> criteria = builder.createQuery(User.class);
		if (CollectionUtils.isNotEmpty(query.getOrder())) {
			List<Order> orderList = new ArrayList<>();
			for (SearchOrder order : query.getOrder()) {
				if (StringUtils.equalsIgnoreCase(order.getDir(), "desc")) {
					orderList.add(builder.desc(root.get(order.getField().getName())));
				} else if (StringUtils.equalsIgnoreCase(order.getDir(), "asc")) {
					orderList.add(builder.asc(root.get(order.getField().getName())));
				}
			}
			criteria.orderBy(orderList);
		}
		criteria.select(criteria.from(User.class));
		criteria.where(predicate);
		List<User> formMetasList = getEntityManager().createQuery(criteria).setFirstResult(query.getStart()).setMaxResults(query.getPageSize()).getResultList();
		SearchResult<User> searchResult = new SearchResult<User>();
		searchResult.addAll(formMetasList);
		searchResult.setRecordsTotal(count);
		return searchResult;
	}

	@Override
	public User readUserById(long userId) throws DataAccessException {
		return readGenericEntity(User.class, userId);
	}
}