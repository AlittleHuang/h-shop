package com.shengchuang.core.pageable;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.shengchuang.core.util.TimeUtil;

/**
 * 分页查询条件
 */
public class ParametersMap {

	protected HttpServletRequest request;

	public ParametersMap() {
		request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
	}

	public ParametersMap(HttpServletRequest request) {
		this.request = request;
	}

	public String get(String key) {
		return request.getParameter(key);
	}

	public String[] getArray(String key) {
		return request.getParameterMap().get(key);
	}

	public String getStringValue(String key) {
		return this.get(key);
	}

	public Integer getIntValue(String key) {
		try {
			return Integer.valueOf(this.get(key));
		} catch (Exception e) {
			return null;
		}
	}

	public Long getLongValue(String key) {
		try {
			return Long.valueOf(this.get(key));
		} catch (Exception e) {
			return null;
		}
	}

	public Double getDoubleValue(String key) {
		try {
			return Double.valueOf(this.get(key));
		} catch (Exception e) {
			return null;
		}
	}

	public Date getDateValue(String key) {
		try {
			return TimeUtil.parseDate(get(key));
		} catch (Exception e) {
			return null;
		}
	}

}
