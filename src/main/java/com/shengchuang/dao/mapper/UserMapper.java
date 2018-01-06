package com.shengchuang.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.shengchuang.entity.User;

public interface UserMapper {

	@Select("select * from user")
	List<User> findAll();

}
