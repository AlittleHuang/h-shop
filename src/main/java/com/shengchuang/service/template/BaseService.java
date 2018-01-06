package com.shengchuang.service.template;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Optional;

import javax.persistence.Id;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.shengchuang.dao.template.CommonDao;

public abstract class BaseService<T, ID> extends CommonDao {

	@Autowired(required = false)
	protected JpaRepository<T, ID> jpaRepository;
	@Autowired(required = false)
	protected JpaSpecificationExecutor<T> jpaSpecificationExecutor;

	{
		init();
	}

	public Conditions<T> createConditions() {
		return createConditions(classT);
	}

	public <S extends T> S save(S entity) {
		return jpaRepository.save(entity);
	}

	public <S extends T> Optional<S> findOne(Example<S> example) {
		return jpaRepository.findOne(example);
	}

	public Page<T> findAll(Pageable pageable) {
		return jpaRepository.findAll(pageable);
	}

	public List<T> findAll() {
		return find(createConditions());
	}

	public List<T> findAll(Sort sort) {
		return jpaRepository.findAll(sort);
	}

	public Optional<T> findById(ID id) {
		return jpaRepository.findById(id);
	}

	public List<T> findAllById(Iterable<ID> ids) {
		return jpaRepository.findAllById(ids);
	}

	public <S extends T> List<S> saveAll(Iterable<S> entities) {
		return jpaRepository.saveAll(entities);
	}

	public boolean existsById(ID id) {
		return jpaRepository.existsById(id);
	}

	public void flush() {
		jpaRepository.flush();
	}

	public <S extends T> S saveAndFlush(S entity) {
		return jpaRepository.saveAndFlush(entity);
	}

	public void deleteInBatch(Iterable<T> entities) {
		jpaRepository.deleteInBatch(entities);
	}

	public <S extends T> Page<S> findAll(Example<S> example, Pageable pageable) {
		return jpaRepository.findAll(example, pageable);
	}

	public long count() {
		return jpaRepository.count();
	}

	public void deleteAllInBatch() {
		jpaRepository.deleteAllInBatch();
	}

	public void deleteById(ID id) {
		jpaRepository.deleteById(id);
	}

	public T getOne(ID id) {
		return jpaRepository.getOne(id);
	}

	public void delete(T entity) {
		jpaRepository.delete(entity);
	}

	public <S extends T> long count(Example<S> example) {
		return jpaRepository.count(example);
	}

	public void deleteAll(Iterable<? extends T> entities) {
		jpaRepository.deleteAll(entities);
	}

	public <S extends T> List<S> findAll(Example<S> example) {
		return jpaRepository.findAll(example);
	}

	public <S extends T> boolean exists(Example<S> example) {
		return jpaRepository.exists(example);
	}

	public void deleteAll() {
		jpaRepository.deleteAll();
	}

	public <S extends T> List<S> findAll(Example<S> example, Sort sort) {
		return jpaRepository.findAll(example, sort);
	}

	public Optional<T> findOne(Specification<T> spec) {
		return jpaSpecificationExecutor.findOne(spec);
	}

	public List<T> findAll(Specification<T> spec) {
		return jpaSpecificationExecutor.findAll(spec);
	}

	public Page<T> findAll(Specification<T> spec, Pageable pageable) {
		return jpaSpecificationExecutor.findAll(spec, pageable);
	}

	public List<T> findAll(Specification<T> spec, Sort sort) {
		return jpaSpecificationExecutor.findAll(spec, sort);
	}

	public long count(Specification<T> spec) {
		return jpaSpecificationExecutor.count(spec);
	}

	private Class<T> classT;
	private Method idReadMethod;

	@SuppressWarnings("unchecked")
	private void init() {
		try {
			classT = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
			BeanInfo beanInfo = Introspector.getBeanInfo(classT);
			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
			for (PropertyDescriptor property : propertyDescriptors) {
				String key = property.getName();
				if ("class".equals(key)) {
					continue;
				}
				Field field = classT.getDeclaredField(key);
				if (field.getAnnotation(Id.class) != null) {
					idReadMethod = property.getReadMethod();
					break;
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public <S extends T> S saveSelective(S entity) {
		try {
			@SuppressWarnings("unchecked")
			ID id = (ID) idReadMethod.invoke(entity);

			if (StringUtils.isBlank(String.valueOf(id))) {
				return save(entity);
			}
			Optional<T> one = findById(id);
			one.ifPresent((db) -> {
				fillBean(db, entity);
			});
			return save(entity);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public <S extends T> S saveAndFlushSelective(S entity) {
		S res = saveSelective(entity);
		flush();
		return res;
	}

	private <S extends T> void fillBean(T db, S save) {
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(classT);
			PropertyDescriptor[] descriptors = beanInfo.getPropertyDescriptors();
			for (PropertyDescriptor p : descriptors) {
				Method getter = p.getReadMethod();
				Method setter = p.getWriteMethod();
				if (setter == null || getter == null) {
					continue;
				}
				Object checkEmpty = getter.invoke(save);
				System.err.println(p.getName() + ":" + String.valueOf(checkEmpty) + ";");
				if (checkEmpty == null || StringUtils.isBlank(checkEmpty.toString())) {
					Object v = getter.invoke(db);
					setter.invoke(save, v);
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}