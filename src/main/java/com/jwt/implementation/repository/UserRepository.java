package com.jwt.implementation.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jwt.implementation.model.User;

public interface UserRepository extends JpaRepository<User, Integer>{
	User findByUserName(String username);
}
