package com.shengchuang.dao.template;

import java.util.List;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

/**
 * 分页查询结果,修改原API getNumber为从1开始
 * 
 * @author HuangChengwei
 *
 * @param <T>
 */
public class PageFix<T> extends PageImpl<T> {

	private static final long serialVersionUID = 194058436520683030L;

	public PageFix(List<T> content, Pageable pageable, long total) {
		super(content, pageable, total);
	}

	public PageFix(List<T> content) {
		super(content);
	}

	public PageFix(org.springframework.data.domain.Page<T> page) {
		super(page.getContent(), page.getPageable(), page.getTotalElements());
	}

	/**
	 * 页码从1开始
	 */
	@Override
	public int getNumber() {
		return super.getNumber() + 1;
	}

}
