package com.shengchuang.web.view;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JsonObject implements JsonView {

	private Object content;

	public JsonObject(Object content) {
		this.content = content;
	}

	@Override
	public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		response.setContentType(JSON_CONTENT_TYPE);
		response.getWriter().print(GSON.toJson(content));
	}

}
