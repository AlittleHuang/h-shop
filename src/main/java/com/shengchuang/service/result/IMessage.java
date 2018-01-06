package com.shengchuang.service.result;

import java.io.Serializable;

import com.shengchuang.core.util.VoidFunction;

public interface IMessage extends Serializable {

	boolean success();

	String msg();

	default void ifSuccess(VoidFunction fun) {
		fun.accept();
	}

}