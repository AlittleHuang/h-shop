package com.shengchuang.dao.template;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;

public class PredicateBuilder<T> {

	protected final List<Selection<?>> selections = new ArrayList<>();
	protected final List<Expression<?>> grouping = new ArrayList<>();
	protected final List<Order> orders = new ArrayList<>();
	protected CriteriaBuilder criteriaBuilder;
	protected Root<T> root;

	protected CriteriaQuery<?> query;
	private final List<Criteria<T>> criterias = new ArrayList<>();

	public PredicateBuilder(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
		this.criteriaBuilder = criteriaBuilder;
		this.root = root;
		this.query = query;
	}

	public Criteria<T> createCriteria() {
		Criteria<T> criteria = new Criteria<T>(criteriaBuilder, root);
		criterias.add(criteria);
		return criteria;
	}

	public Criteria<T> or() {
		return createCriteria().or();
	}

	public Criteria<T> and() {
		return createCriteria();
	}

	public void addSelect(String property) {
		Path<Object> path = root.get(property);
		Class<? extends Object> type = path.getJavaType();
		selections.add(path.as(type));
	}

	public <N extends Number> Selection<N> addSelectMin(String property) {
		Path<Object> path = root.get(property);
		@SuppressWarnings("unchecked")
		Class<N> type = (Class<N>) path.getJavaType();
		return criteriaBuilder.min(path.as(type));
	}

	public <N extends Number> void addSelectMax(String property) {
		Path<Object> path = root.get(property);
		@SuppressWarnings("unchecked")
		Class<N> type = (Class<N>) path.getJavaType();
		selections.add(criteriaBuilder.max(path.as(type)));
	}

	public <N extends Number> void addSelectSum(String property) {
		Path<Object> path = root.get(property);
		@SuppressWarnings("unchecked")
		Class<N> type = (Class<N>) path.getJavaType();
		selections.add(criteriaBuilder.sum(path.as(type)));
	}

	public void addOrderByDesc(String... attributeNames) {
		for (String attributeName : attributeNames) {
			orders.add(criteriaBuilder.desc(root.get(attributeName)));
		}
	}

	public void addOrderByAsc(String... attributeNames) {
		for (String attributeName : attributeNames) {
			orders.add(criteriaBuilder.asc(root.get(attributeName)));
		}
	}

	public void groupBy(String... attributes) {
		for (String g : attributes) {
			grouping.add(root.get(g));
		}
	}

	public Predicate toPredicate() {
		Predicate predicate = createPredicate();
		initQuery(query, predicate);
		return predicate;
	}

	protected Predicate createPredicate() {
		Criteria<T> criteria = new Criteria<T>(criteriaBuilder, root);
		for (Criteria<T> c : criterias) {
			Predicate predicate = c.toPredicate();
			if (predicate == null)
				continue;
			switch (c.operator) {
			case AND:
				c.and(predicate);
				break;
			case OR:
				c.or(predicate);
				break;
			default:
			}
		}
		return criteria.toPredicate();
	}

	@SuppressWarnings("unchecked")
	private void initQuery(CriteriaQuery<?> query, Predicate predicate) {
		if (query == null)
			return;
		if (!orders.isEmpty())
			query.orderBy(orders);
		if (!grouping.isEmpty())
			query.groupBy(grouping);
		if (selections.isEmpty()) {
			((CriteriaQuery<T>) query).select(root);
		} else
			query.multiselect(selections);
		if (predicate != null)
			query.where(predicate);
	}

	@SuppressWarnings("unchecked")
	protected CriteriaQuery<T> initQuery() {
		initQuery(query, createPredicate());
		return (CriteriaQuery<T>) query;
	}

	@SuppressWarnings("unchecked")
	protected <X> CriteriaQuery<X> initQuery(Class<X> clazz) {
		return (CriteriaQuery<X>) initQuery();
	}

}
