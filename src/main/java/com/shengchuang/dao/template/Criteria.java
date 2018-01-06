package com.shengchuang.dao.template;

import java.util.Collection;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaBuilder.In;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Predicate.BooleanOperator;
import javax.persistence.criteria.Root;

public class Criteria<T> {

	protected BooleanOperator operator = BooleanOperator.AND;
	protected Predicate predicate;
	protected CriteriaBuilder cb;
	protected Root<T> root;

	public Criteria(CriteriaBuilder cb, Root<T> root) {
		this.cb = cb;
		this.root = root;
	}

	public void setOperator(BooleanOperator operator) {
		if (operator != null)
			this.operator = operator;
	}

	protected Criteria<T> or() {
		this.operator = BooleanOperator.OR;
		return this;
	}

	protected Criteria<T> or(Predicate restriction) {
		if (predicate != null)
			predicate = cb.or(predicate, restriction);
		else
			predicate = cb.or(restriction);
		return this;
	}

	protected Criteria<T> orNot(Predicate restriction) {
		if (predicate != null)
			predicate = cb.or(predicate, restriction.not());
		else
			predicate = cb.or(restriction.not());
		return this;
	}

	protected Criteria<T> and(Predicate restriction) {
		if (predicate != null)
			predicate = cb.and(predicate, restriction);
		else
			predicate = cb.and(restriction);
		return this;
	}

	protected Criteria<T> andNot(Predicate restriction) {
		if (predicate != null)
			predicate = cb.and(predicate, restriction.not());
		else
			predicate = cb.and(restriction).not();
		return this;
	}

	public Criteria<T> andEqual(String name, Object value) {
		return and(cb.equal(root.get(name), value));
	}

	public Criteria<T> orEqual(String name, Object value) {
		return or(cb.equal(root.get(name), value));
	}

	public Criteria<T> andNotEqual(String name, Object value) {
		return andNot(cb.equal(root.get(name), value));
	}

	public Criteria<T> orNotNotEqual(String name, Object value) {
		return orNot(cb.equal(root.get(name), value));
	}

	public Criteria<T> andGe(String name, Number value) {
		return and(cb.ge(root.get(name).as(Number.class), value));
	}

	public Criteria<T> orGe(String name, Number value) {
		return or(cb.ge(root.get(name).as(Number.class), value));
	}

	public Criteria<T> andLe(String name, Number value) {
		return and(cb.le(root.get(name).as(Number.class), value));
	}

	public Criteria<T> orLe(String name, Number value) {
		return or(cb.le(root.get(name).as(Number.class), value));
	}

	public Criteria<T> andGt(String name, Number value) {
		return and(cb.gt(root.get(name).as(Number.class), value));
	}

	public Criteria<T> orGt(String name, Number value) {
		return or(cb.gt(root.get(name).as(Number.class), value));
	}

	public Criteria<T> andLt(String name, Number value) {
		return and(cb.lt(root.get(name).as(Number.class), value));
	}

	public Criteria<T> orLt(String name, Number value) {
		return or(cb.lt(root.get(name).as(Number.class), value));
	}

	public <Y extends Comparable<? super Y>> Criteria<T> andGe(
																String name,
																Y value,
																Class<Y> type)
	{
		return and(cb.greaterThanOrEqualTo(root.get(name).as(type), value));
	}

	public <Y extends Comparable<? super Y>> Criteria<T> orGe(
																String name,
																Y value,
																Class<Y> type)
	{
		return or(cb.greaterThanOrEqualTo(root.get(name).as(type), value));
	}

	public <Y extends Comparable<? super Y>> Criteria<T> andGt(
																String name,
																Y value,
																Class<Y> type)
	{
		return and(cb.greaterThan(root.get(name).as(type), value));
	}

	public <Y extends Comparable<? super Y>> Criteria<T> orGt(
																String name,
																Y value,
																Class<Y> type)
	{
		return or(cb.greaterThan(root.get(name).as(type), value));
	}

	public <Y extends Comparable<? super Y>> Criteria<T> andLe(
																String name,
																Y value,
																Class<Y> type)
	{
		return and(cb.lessThanOrEqualTo(root.get(name).as(type), value));
	}

	public <Y extends Comparable<? super Y>> Criteria<T> orLe(
																String name,
																Y value,
																Class<Y> type)
	{
		return or(cb.lessThanOrEqualTo(root.get(name).as(type), value));
	}

	public <Y extends Comparable<? super Y>> Criteria<T> andLt(
																String name,
																Y value,
																Class<Y> type)
	{
		return and(cb.lessThan(root.get(name).as(type), value));
	}

	public <Y extends Comparable<? super Y>> Criteria<T> orLt(
																String name,
																Y value,
																Class<Y> type)
	{
		return or(cb.lessThan(root.get(name).as(type), value));
	}

	public <Y extends Comparable<? super Y>> Criteria<T> andBetween(
																	String name,
																	Y value0,
																	Y value1,
																	Class<Y> type)
	{
		return and(cb.between(root.get(name).as(type), value0, value1));
	}

	public <Y extends Comparable<? super Y>> Criteria<T> orBetween(
																	String name,
																	Y value0,
																	Y value1,
																	Class<Y> type)
	{
		return or(cb.between(root.get(name).as(type), value0, value1));
	}

	public <Y extends Comparable<? super Y>> Criteria<T> andNotBetween(
																		String name,
																		Y value0,
																		Y value1,
																		Class<Y> type)
	{
		return and(cb.between(root.get(name).as(type), value0, value1).not());
	}

	public <Y extends Comparable<? super Y>> Criteria<T> orNotBetween(
																		String name,
																		Y value0,
																		Y value1,
																		Class<Y> type)
	{
		return or(cb.between(root.get(name).as(type), value0, value1).not());
	}

	public <Y extends Comparable<? super Y>> Criteria<T> andBetween(
																	String name,
																	Y value0,
																	Y value1)
	{
		return and(cb.between(root.get(name), value0, value1));
	}

	public <Y extends Comparable<? super Y>> Criteria<T> orBetween(
																	String name,
																	Y value0,
																	Y value1)
	{
		return or(cb.between(root.get(name), value0, value1));
	}

	public <Y extends Comparable<? super Y>> Criteria<T> andNotBetween(
																		String name,
																		Y value0,
																		Y value1)
	{
		return and(cb.between(root.get(name), value0, value1).not());
	}

	public <Y extends Comparable<? super Y>> Criteria<T> orNotBetween(
																		String name,
																		Y value0,
																		Y value1)
	{
		return or(cb.between(root.get(name), value0, value1).not());
	}

	public Criteria<T> andIsNull(String name) {
		return and(cb.isNull(root.get(name)));
	}

	public Criteria<T> andIsNotNull(String name) {
		return and(cb.isNotNull(root.get(name)));
	}

	public Criteria<T> andLike(String name, String value) {
		return and(cb.like(root.get(name).as(String.class), value));
	}

	public Criteria<T> andNotLike(String name, String value) {
		return and(cb.like(root.get(name).as(String.class), value).not());
	}

	public Criteria<T> orLike(String name, String value) {
		return or(cb.like(root.get(name).as(String.class), value));
	}

	public Criteria<T> orNotLike(String name, String value) {
		return or(cb.like(root.get(name).as(String.class), value).not());
	}

	public <X> Criteria<T> andIn(String name, Collection<X> value) {
		In<Object> in = cb.in(root.get(name));
		for (X x : value) {
			in.value(x);
		}
		return and(in);
	}

	public <X> Criteria<T> andIn(String name, X[] value) {
		In<Object> in = cb.in(root.get(name));
		for (X x : value) {
			in.value(x);
		}
		return and(in);
	}

	public <X> Criteria<T> andNotIn(String name, Collection<X> value) {
		In<Object> in = cb.in(root.get(name));
		for (X x : value) {
			in.value(x);
		}
		return and(in.not());
	}

	public <X> Criteria<T> andNotIn(String name, X[] value) {
		In<Object> in = cb.in(root.get(name));
		for (X x : value) {
			in.value(x);
		}
		return and(in.not());
	}

	public <X> Criteria<T> orIn(String name, Collection<X> value) {
		In<Object> in = cb.in(root.get(name));
		for (X x : value) {
			in.value(x);
		}
		return or(in);
	}

	public <X> Criteria<T> orIn(String name, X[] value) {
		In<Object> in = cb.in(root.get(name));
		for (X x : value) {
			in.value(x);
		}
		return or(in);
	}

	public <X> Criteria<T> orNotIn(String name, Collection<X> value) {
		In<Object> in = cb.in(root.get(name));
		for (X x : value) {
			in.value(x);
		}
		return or(in.not());
	}

	public <X> Criteria<T> orNotIn(String name, X[] value) {
		In<Object> in = cb.in(root.get(name));
		for (X x : value) {
			in.value(x);
		}
		return or(in.not());
	}

	protected Predicate toPredicate() {
		return predicate;
	}

}
