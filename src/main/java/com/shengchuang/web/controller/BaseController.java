package com.shengchuang.web.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.shengchuang.core.util.TimeUtil;
import com.shengchuang.entity.User;

@Controller
public abstract class BaseController {

	protected final Log logger = LogFactory.getLog(getClass());
	
	private static final ThreadLocal<HttpServletRequest> REQUEST = new ThreadLocal<HttpServletRequest>() {
		protected HttpServletRequest initialValue() {
			return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		};
	};

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
	}

	protected HttpServletRequest request() {
		return REQUEST.get();
	}

	protected HttpSession session() {
		return request().getSession();
	}

	protected ServletContext servletContext() {
		return request().getServletContext();
	}

	public void setRequestAttributes(Map<String, ?> m) {
		if (m == null)
			return;
		for (Entry<String, ?> e : m.entrySet()) {
			if (e.getKey() == null || e.getValue() == null)
				continue;
			request().setAttribute(e.getKey(), e.getValue());
		}
	}

	public int intParameter(String name) {
		return Integer.valueOf(request().getParameter(name));
	}

	public double doubleParameter(String name) {
		return Double.valueOf(request().getParameter(name));
	}

	public Date dateParameter(String name) {
		try {
			return TimeUtil.parseDate(request().getParameter(name));
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	protected User getLoginUser() {
		return (User) SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
	}

	/**
	 * 协议+域名+端口号+工程名
	 * 
	 * @return
	 */
	public static String baseUrl() {
		HttpServletRequest request = REQUEST.get();
		String path = request.getContextPath();
		String tglink = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
		String basepath = tglink + path + "/";
		return basepath;
	}


}
