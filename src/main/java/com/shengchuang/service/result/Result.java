package com.shengchuang.service.result;

import java.util.function.Consumer;

public class Result<T> implements IMessage {

	private static final long serialVersionUID = 1453494518692009758L;

	protected String message;

	private T value;

	public Result() {
	}

	public Result(T value) {
		this.value = value;
	}

	public T get() {
		return value;
	}

	public Result<T> set(T value) {
		this.value = value;
		return this;
	}

	public boolean isPresent() {
		return value != null;
	}

	public void ifPresent(Consumer<? super T> consumer) {
		if (value != null)
			consumer.accept(value);
	}

	public boolean success() {
		return this.isPresent();
	}

	public String msg() {
		return message;
	}

	public Result<T> msg(String message) {
		this.message = message;
		return this;
	}

}
