package com.shengchuang.web.view;

import org.springframework.web.servlet.View;

import com.google.gson.Gson;

public interface JsonView extends View {

	final Gson GSON = new Gson();
	final String JSON_CONTENT_TYPE = "application/json;charset=UTF-8";

	@Override
	default String getContentType() {
		return JSON_CONTENT_TYPE;
	}

}
