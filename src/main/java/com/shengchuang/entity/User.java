package com.shengchuang.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity(name = "user")
public class User implements Serializable {

	private static final long serialVersionUID = 7101432330732086919L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 主键自增
	private Integer id;// 主键
	private String username;
	private String password;

	// @OneToMany(fetch = FetchType.EAGER)
	// @JoinColumn(name = "username", referencedColumnName = "username")
	@Transient
	private List<Role> roles;
	
	public User() {
	}

	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}



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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + "]";
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

}
