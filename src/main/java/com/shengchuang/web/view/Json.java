package com.shengchuang.web.view;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.Conventions;
import org.springframework.util.Assert;

import com.shengchuang.service.result.IMessage;

public class Json extends LinkedHashMap<String, Object> implements JsonView {

	private static final long serialVersionUID = 1L;

	private static final String SUCCESS = "success";
	private static final String MSG = "message";

	{
		put(SUCCESS, true);
		put(MSG, "");
	}

	public Json() {
	}

	public Json(String message) {
		put(MSG, message);
	}

	public Json(IMessage message) {
		msg(message);
	}

	public Json msg(IMessage message) {
		success(message.success());
		msg(message.msg());
		return this;
	}

	public Json success(boolean success) {
		put(SUCCESS, success);
		return this;
	}

	public Boolean success() {
		try {
			return (boolean) get(SUCCESS);
		} catch (Exception e) {
			return null;
		}
	}

	public Json msg(String msg) {
		put(MSG, msg);
		return this;
	}

	public String msg() {
		return (String) get(MSG);
	}

	public Json failedMsg(String msg) {
		put(SUCCESS, false);
		put(MSG, msg);
		return this;
	}

	public Json successMsg(String msg) {
		put(SUCCESS, true);
		put(MSG, msg);
		return this;
	}

	public Json add(Object attributeValue) {
		Assert.notNull(attributeValue, "Model object must not be null");
		if (attributeValue instanceof Collection && ((Collection<?>) attributeValue).isEmpty()) {
			return this;
		}
		return add(Conventions.getVariableName(attributeValue), attributeValue);
	}

	public Json add(String key, Object value) {
		put(key, value);
		return this;
	}
	
	public String toJsonString() {
		return JsonView.GSON.toJson(this, Json.class);
	}

	@Override
	public String toString() {
		return toJsonString();
	}

	/* ↓ Implements {@link JsonView} ↓ */

	@Override
	public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		if (model != null) {
			putAll(model);
		}
		response.setContentType(JsonView.JSON_CONTENT_TYPE);
		response.getWriter().print(toJsonString());
	}

	@Override
	public String getContentType() {
		return JsonView.JSON_CONTENT_TYPE;
	}

}
