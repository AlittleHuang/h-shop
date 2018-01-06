package com.shengchuang.web.controller;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.shengchuang.entity.Role;
import com.shengchuang.entity.User;
import com.shengchuang.service.UserService;
import com.shengchuang.service.result.IMessage;
import com.shengchuang.shiro.UserToken;
import com.shengchuang.web.view.Json;

@Controller
public class UserController extends BaseController {

	@Autowired
	UserService userService;

	@PostMapping("/login")
	public View login(String username, String password) {
		Json json = new Json();
		if (StringUtils.isBlank(username)) {
			return json.failedMsg("请输入用户名");
		}
		if (StringUtils.isBlank(password)) {
			return json.failedMsg("请输入密码");
		}
		Subject subject = SecurityUtils.getSubject();
		subject.logout();
		try {
			UserToken token = new UserToken(username, password);
			token.setRememberMe(true);
			subject.login(token);
		} catch (AuthenticationException e) {
			return json.failedMsg("用户名或密码错误");
		}
		return json.msg("登陆成功");
	}

	@RequestMapping("/logout")
	public String loginout() {
		SecurityUtils.getSubject().logout();
		return "/";
	}

	@PostMapping("/register")
	public View register(String username, String password) {
		Json json = new Json();
		if (StringUtils.isBlank(username)) {
			return json.failedMsg("请输入用户名");
		}
		if (StringUtils.isBlank(password)) {
			return json.failedMsg("请输入密码");
		}
		User user = new User(username, password);
		IMessage message = userService.register(user);
		return json.msg(message);
	}

	@RequiresRoles("admin")
	@RequestMapping("/check/admin")
	public Json admin(String username, String password) {
		return new Json().msg("admin");
	}

	@RequiresPermissions("admin:test")
	@RequestMapping("/check/admin/test")
	public Json adminTest() {
		return new Json().msg("admin");
	}

	@RequiresPermissions("admin:update")
	@RequestMapping("/check/admin/update")
	public Json adminUpdate() {
		return new Json().msg("admin");
	}

	@RequestMapping("/user")
	public Json getUser() {
		User user = getLoginUser();
		userService.loadRoles(user);
		List<Role> roles = user.getRoles();
		return new Json().add("user", roles);
	}

}
