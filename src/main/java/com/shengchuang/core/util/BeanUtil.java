package com.shengchuang.core.util;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.persistence.Column;

import org.apache.commons.lang.StringUtils;

public interface BeanUtil {

	/**
	 * 通过属性名调用getter
	 * 
	 * @param bean
	 * @param propertyName
	 * @return
	 */
	static <Bean> Object getBeanProperties(Bean bean, String propertyName) {
		Objects.requireNonNull(bean, "bean can not be null");
		Objects.requireNonNull(propertyName, "property name can not be null");
		try {
			PropertyDescriptor p = new PropertyDescriptor(propertyName, bean.getClass());
			Method getter = p.getReadMethod();
			return getter.invoke(bean);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 通过属性名调用setter
	 * 
	 * @param bean
	 * @param propertyName
	 * @param value
	 * @return
	 */
	static <Bean> Boolean setBeanProperties(
											Bean bean,
											String propertyName,
											Object value)
	{
		Objects.requireNonNull(bean, "bean can not be null");
		Objects.requireNonNull(propertyName, "property name can not be null");
		try {
			PropertyDescriptor p = new PropertyDescriptor(propertyName, bean.getClass());
			Method setter = p.getWriteMethod();
			setter.invoke(bean, value);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 合并两个bean,忽略null值,用来更新实体类,两个bean类型必须相同
	 * 
	 * @param src
	 *            要更行的数据(前端传)
	 * @param res
	 *            被更新的数据(数据库查)
	 * @return 更新后的destination
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	static <T> T mergeBean(T src, T res) {
		if (!src.getClass().equals(res.getClass())) {
			throw new RuntimeException("传入两个bean类型必须相同");
		}
		return (T) mergeBean(src, res, res.getClass());
	}

	/**
	 * 根据Class合并两个bean,忽略null值
	 * 
	 * @param in
	 * @param out
	 * @param clazz
	 * @return
	 * @throws Exception
	 */
	static <T> T mergeBean(T in, T out, Class<? extends T> clazz) {
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
			PropertyDescriptor[] descriptors = beanInfo.getPropertyDescriptors();
			for (PropertyDescriptor p : descriptors) {
				Method getter = p.getReadMethod();
				Method setter = p.getWriteMethod();
				if (setter == null || getter == null) {
					continue;
				}
				Object v = getter.invoke(in);
				if (StringUtils.isNotBlank(String.valueOf(v))) {
					setter.invoke(out, v);
				}
			}
			return out;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static Map<String, Object> toMap(Object obj, Map<String, Object> map) {
		return toMap(obj, map, true);
	}

	public static Map<String, Object> toMap(Object obj) {
		return toMap(obj, new HashMap<String, Object>(), true);
	}

	public static Map<String, Object> toMap(Object obj, HashMap<String, Object> map) {
		return toMap(obj, map, true);
	}

	public static <T> T toBean(Map<String, Object> map, Class<T> cls) {
		try {
			return toBean(map, cls.newInstance(), true);
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static <T> T toBean(Map<String, Object> map, T bean) {
		return toBean(map, bean, true);
	}

	public static <T> T toBean(Map<String, Object> map, T bean, boolean annotationValid) {
		Objects.requireNonNull(bean);
		Objects.requireNonNull(map);
		try {
			Class<? extends Object> cls = bean.getClass();
			BeanInfo beanInfo = Introspector.getBeanInfo(cls);
			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();

			for (PropertyDescriptor property : propertyDescriptors) {
				String key = property.getName();
				if ("class".equals(key)) {
					continue;
				}
				if (annotationValid) {
					Field field = cls.getDeclaredField(key);
					Column column = field.getAnnotation(Column.class);
					if (column != null && StringUtils.isNotBlank(column.name())) {
						key = column.name();
					}
				}
				if (map.containsKey(key)) {
					Object value = map.get(key);
					Method setter = property.getWriteMethod();
					setter.invoke(bean, value);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bean;
	}

	public static Map<String, Object> toMap(Object bean, Map<String, Object> map, boolean annotationValid) {
		Objects.requireNonNull(bean);
		Objects.requireNonNull(map);
		Class<?> cls = bean.getClass();
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
			for (PropertyDescriptor property : propertyDescriptors) {
				String key = property.getName();
				if (key.equals("class")) {
					continue;
				}
				if (annotationValid) {
					Field field = cls.getDeclaredField(key);
					Column column = field.getAnnotation(Column.class);
					if (column != null && StringUtils.isNotBlank(column.name())) {
						key = column.name();
					}
				}
				Method getter = property.getReadMethod();
				Object value = getter.invoke(bean);
				map.put(key, value);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

}
