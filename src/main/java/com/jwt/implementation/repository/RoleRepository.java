package com.jwt.implementation.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jwt.implementation.model.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {
	Role findByRole(String role);

}
