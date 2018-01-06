package com.shengchuang.shiro;

import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.util.ByteSource;

import com.shengchuang.core.util.ApplicationUtil;
import com.shengchuang.dao.repository.UserRepository;
import com.shengchuang.entity.User;

public class UserToken extends UsernamePasswordToken {

	private final UserRepository userRepository = ApplicationUtil.getBean(UserRepository.class);
	private static final long serialVersionUID = 328172916269233346L;

	private static final User EMPTY = new User();

	private User user;

	private User user() {
		if (user == null) {
			user = userRepository.findByUsername(getUsername());
			if (user == null) {
				user = EMPTY;
			}
		}
		return user;
	}

	@Override
	public Object getPrincipal() {
		return user();
	}

	public UserToken(String username, String passsword) {
		super(username, passsword);
	}

	public String getDbPassword() {
		return user().getPassword();
	}

	public ByteSource getSalt() {
		return PasswordUtil.getSolt(user());
	}
}