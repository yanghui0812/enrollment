package com.enroll.security.dao.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.enroll.core.dto.SearchResult;
import com.enroll.core.search.SearchOrder;
import com.enroll.security.dao.RoleDao;
import com.enroll.security.dto.RoleQuery;
import com.enroll.security.entity.Role;

@Repository
public class RoleDaoImpl extends GenericEntityDaoImpl implements RoleDao {

	@Override
	public SearchResult<Role> findRolePage(RoleQuery query) {
		CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Long> countCriteria = builder.createQuery(Long.class);
		Root<Role> root = countCriteria.from(Role.class);
		
		Predicate predicate = buildPredicate(query, builder, root);
		countCriteria.select(builder.countDistinct(root));
		countCriteria.where(predicate);
		int count = getEntityManager().createQuery(countCriteria).getSingleResult().intValue();

		CriteriaQuery<Role> criteria = builder.createQuery(Role.class);
		criteria.select(criteria.from(Role.class));
		criteria.where(predicate);
		appendOrder(query, builder, criteria, root);
		List<Role> formMetasList = getEntityManager().createQuery(criteria).setFirstResult(query.getStart()).setMaxResults(query.getPageSize()).getResultList();
		SearchResult<Role> searchResult = new SearchResult<Role>();
		searchResult.addAll(formMetasList);
		searchResult.setRecordsTotal(count);
		return searchResult;
	}
	
	private Predicate buildPredicate(RoleQuery query, CriteriaBuilder builder, Root<Role> root){
		Expression<String> empty = builder.literal(StringUtils.EMPTY);
		Predicate predicate = builder.equal(empty, empty);
		if (query == null) {
			return predicate;
		}
		if (query.getStatus() != null && StringUtils.isNotBlank(query.getStatus().getSearch().getValue())) {
			predicate = builder.and(predicate, builder.equal(root.get("status"), query.getStatus().getSearch().getValue()));
		}
		return predicate;
	}
	
	private void appendOrder(RoleQuery query, CriteriaBuilder builder, CriteriaQuery<Role> criteria, Root<Role> root){
		if (query == null ||CollectionUtils.isEmpty(query.getOrder())) {
			return;
		}
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

	@Override
	public Collection<Role> findRoleList(RoleQuery query) {
		CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();		
		CriteriaQuery<Role> criteria = builder.createQuery(Role.class);
		Root<Role> root = criteria.from(Role.class);
		Predicate predicate = buildPredicate(query, builder, root);
		criteria.select(root);
		criteria.where(predicate);
		appendOrder(query, builder, criteria, root);
		return getEntityManager().createQuery(criteria).getResultList();
	}
}