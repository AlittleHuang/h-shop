package com.shengchuang.core.util;

import java.util.Objects;

import javax.servlet.ServletContext;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

public interface ApplicationUtil {

	public static Object getBean(String name) {
		Objects.requireNonNull(name);
		return getWebApplicationContext().getBean(name);
	}

	public static <T> T getBean(Class<T> requiredType) {
		Objects.requireNonNull(requiredType);
		return getWebApplicationContext().getBean(requiredType);
	}

	public static WebApplicationContext getWebApplicationContext() {
		return ContextLoader.getCurrentWebApplicationContext();
	}

	public static ServletContext getServletContext() {
		return getWebApplicationContext().getServletContext();
	}
	
}
