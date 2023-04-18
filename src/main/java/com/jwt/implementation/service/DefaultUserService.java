package com.jwt.implementation.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.jwt.implementation.model.User;
import com.jwt.implementation.model.UserDTO;

public interface DefaultUserService extends UserDetailsService{
	User save(UserDTO userRegisteredDTO);

}
