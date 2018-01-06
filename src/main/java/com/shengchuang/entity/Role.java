package com.shengchuang.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "role")
public class Role implements Serializable {

	private static final long serialVersionUID = -1611048726347840182L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 主键自增
	Integer id;

	String username;
	String role;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "Role [id=" + id + ", username=" + username + ", role=" + role + "]";
	}

}
