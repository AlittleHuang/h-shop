package com.shengchuang.service;

import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shengchuang.dao.repository.RoleRepository;
import com.shengchuang.dao.repository.UserRepository;
import com.shengchuang.entity.User;
import com.shengchuang.service.result.Message;
import com.shengchuang.service.template.BaseService;
import com.shengchuang.shiro.PasswordUtil;

@Service
public class UserService extends BaseService<User, Integer> {

	@Autowired
	RoleRepository roleDAO;
	@Autowired
	UserRepository userDao;

	public User loadRoles(final User user) {
		user.setRoles(roleDAO.findByUsername(user.getUsername()));
		return user;
	}

	public Message register(User user) {
		Message message = new Message();
		if (userDao.existsByUsername(user.getUsername())) {
			return message.failMsg("用户已存在");
		}
		encodePassword(user);
		save(user);
		return message.failMsg("注册成功");
	}

	private void encodePassword(User user) {
		String password = user.getPassword();
		ByteSource solt = PasswordUtil.getSolt(user);
		password = PasswordUtil.encodePassword(password, solt);
		user.setPassword(password);
	}

}
