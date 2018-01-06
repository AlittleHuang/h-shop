package com.shengchuang;

import java.lang.reflect.Method;
import java.util.Objects;

public class Main {

	public static void main(String[] args) {

	}

	public static void testMethod(Object test) {
		Objects.requireNonNull(test);
		Method[] methods = test.getClass().getMethods();
		for (Method method : methods) {
			try {
				System.out.print(method.getName() + ":");
				System.out.println(method.invoke(test));
			} catch (Exception e) {
				System.err.println(e.getLocalizedMessage());
			}
		}
	}

}
