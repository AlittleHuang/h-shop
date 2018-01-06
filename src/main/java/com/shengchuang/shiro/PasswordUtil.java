package com.shengchuang.shiro;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

import com.shengchuang.entity.User;

public interface PasswordUtil {

	static ByteSource getSolt(User user) {
		return ByteSource.Util.bytes(user.getUsername());
	}

	static String encodePassword(Object source, Object salt) {
		SimpleHash password = new SimpleHash(UserRealm.ALGORITHM_NAME, source, salt, UserRealm.HASH_ITERATIONS);
		return password.toString();
	}

}