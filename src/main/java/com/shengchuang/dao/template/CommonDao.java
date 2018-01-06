package com.shengchuang.dao.template;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class CommonDao {

	@Autowired
	EntityManager entityManager;

	public <T> Conditions<T> createConditions(Class<T> entityClass) {
		return new Conditions<>(entityManager, entityClass);
	}

	public <T> List<T> find(Conditions<T> conditions) {
		return conditions.getResultList();
	}

	public <T> List<T> findAll(Class<T> entityClass) {
		return createConditions(entityClass).getResultList();
	}

	public <T> T findOne(Conditions<T> conditions) {
		List<T> list = conditions.getResultList();
		Assert.isTrue(list.size() <= 1, "select one but find " + list.size());
		return list.isEmpty() ? null : list.get(0);
	}

	public <T> boolean exists(Conditions<T> conditions) {
		return count(conditions) > 0;
	}

	public <T> Object findOneObj(Conditions<T> conditions) {
		return findOne(conditions);
	}

	public Long count(Conditions<?> conditions) {
		return conditions.count();
	}

	public <T> Page<T> getPage(Conditions<T> conditions, Pageable pageable) {
		return conditions.getPage(pageable);
	}

	public <T> Page<T> getPage(Conditions<T> conditions) {
		return conditions.getPage(conditions.pageable);
	}

	public <T> List<?> findObjList(Conditions<T> conditions) {
		return conditions.getObjList();
	}

	public static class Conditions<T> extends PredicateBuilder<T> {

		private EntityManager entityManager;
		private Pageable pageable;

		public CriteriaBuilder cb() {
			return criteriaBuilder;
		}

		public Root<T> root() {
			return root;
		}

		public void setPageable(int page, int size) {
			pageable = PageRequest.of(page - 1, size);
		}

		private Conditions(EntityManager entityManager, Class<T> entityClass) {
			super(null, null, null);
			this.entityManager = entityManager;
			this.criteriaBuilder = entityManager.getCriteriaBuilder();
			query = criteriaBuilder.createQuery();
			root = query.from(entityClass);
		}

		private long count() {
			CriteriaQuery<?> query = initQuery();
			@SuppressWarnings("unchecked")
			CriteriaQuery<Long> countQuery = (CriteriaQuery<Long>) query;
			countQuery.select(criteriaBuilder.count(root));
			return entityManager.createQuery(countQuery).getSingleResult();
		}

		private List<?> getObjList() {
			return entityManager.createQuery(initQuery()).getResultList();
		}

		private TypedQuery<T> getRootQuery() {
			return entityManager.createQuery(initQuery().select(root));
		}

		private List<T> getResultList() {
			return getRootQuery().getResultList();
		}

		private Page<T> getPage(Pageable pageable) {
			long count = count();
			CriteriaQuery<T> entityQuery = initQuery();
			List<Order> pageOrders = new ArrayList<>();
			Sort sort = pageable.getSort();
			if (sort != null) {
				for (org.springframework.data.domain.Sort.Order order : sort) {
					String property = order.getProperty();
					switch (order.getDirection()) {
					case ASC:
						pageOrders.add(criteriaBuilder.asc(root.get(property)));
						break;
					case DESC:
						pageOrders.add(criteriaBuilder.desc(root.get(property)));
						break;
					}
				}
			}
			List<Order> orders = entityQuery.getOrderList();
			pageOrders.addAll(orders);
			entityQuery.orderBy(pageOrders);
			TypedQuery<T> tQuery = getRootQuery();
			tQuery.setFirstResult((int) pageable.getOffset());
			tQuery.setMaxResults(pageable.getPageSize());
			Page<T> page = new PageFix<T>(tQuery.getResultList(), pageable, count);
			entityQuery.orderBy(orders);
			return page;
		}

	}
}