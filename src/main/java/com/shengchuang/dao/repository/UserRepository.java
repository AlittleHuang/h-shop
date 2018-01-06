package com.shengchuang.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.shengchuang.entity.User;

public interface UserRepository
		extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User>
{

	User findByUsername(String username);

	boolean existsByUsername(String username);

}