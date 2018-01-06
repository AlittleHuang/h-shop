package com.shengchuang.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.shengchuang.entity.Role;

public interface RoleRepository
		extends JpaRepository<Role, Integer>, JpaSpecificationExecutor<Role>
{

	List<Role> findByUsername(String username);

}