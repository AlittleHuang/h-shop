package com.shengchuang.service.result;

import java.util.HashMap;

public class ResultMap<T> extends HashMap<String, T> implements IMessage {

	private static final long serialVersionUID = 2478946701809169472L;

	protected boolean success = true;
	protected String message;

	public boolean success() {
		return this.success;
	}

	public ResultMap<T> success(boolean success) {
		this.success = success;
		return this;
	}

	public String msg() {
		return message;
	}

	public ResultMap<T> msg(String message) {
		this.message = message;
		return this;
	}

	public ResultMap<T> failMsg(String message) {
		this.success = false;
		this.message = message;
		return this;
	}

	public ResultMap<T> add(String key, T value) {
		put(key, value);
		return this;
	}

}
