package com.shengchuang.core.pageable;

import java.util.Map;
import java.util.Map.Entry;

import org.springframework.data.domain.Pageable;

/**
 * 分页查询条件
 */
public class PageRequest extends ParametersMap {

	private static final int DEFAULT_PAGE = 1;
	private static final int DEFAULT_SIZE = 20;

	public static final String KEY_SIZE = "size";
	public static final String KEY_PAGE = "page";

	private String page;
	private String size;

	public PageRequest() {
		super();
		page = String.valueOf(request.getAttribute(KEY_PAGE));
		size = String.valueOf(request.getAttribute(KEY_SIZE));
	}

	public void parameter2Attribute() {
		Map<String, String[]> m = request.getParameterMap();
		for (Entry<String, String[]> e : m.entrySet()) {
			if (e.getValue().length == 1)
				request.setAttribute(e.getKey(), e.getValue()[0]);
			else
				request.setAttribute(e.getKey(), e.getValue());
		}
	}

	public int getPage() {
		try {
			return Integer.valueOf(page);
		} catch (Exception e) {
			return DEFAULT_PAGE;
		}
	}

	public int getSize() {
		try {
			return Integer.valueOf(size);
		} catch (Exception e) {
			return DEFAULT_SIZE;
		}
	}

	public void setPage(String page) {
		this.page = page;
	}

	public PageRequest page(int page) {
		this.page = String.valueOf(page);
		return this;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public PageRequest size(int size) {
		this.size = String.valueOf(size);
		return this;
	}

	public Pageable toPageable() {
		return org.springframework.data.domain.PageRequest.of(getPage(), getSize());
	}
}
