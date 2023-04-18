package com.jwt.implementation.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.jwt.implementation.model.Role;
import com.jwt.implementation.model.User;
import com.jwt.implementation.service.DefaultUserService;

import io.jsonwebtoken.Claims;

@Component
public class JwtFilter extends OncePerRequestFilter {

	@Autowired
	DefaultUserService defaultUserService;

	@Autowired
	JwtGeneratorValidator jwtgenVal;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String authorizationHeader = request.getHeader("Authorization");

		String token = null;
		String userName = null;
		
		if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
			token = authorizationHeader.substring(7);
			userName = jwtgenVal.extractUsername(token);
		}

		if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {

			UserDetails userDetails = defaultUserService.loadUserByUsername(userName);

			if (jwtgenVal.validateToken(token, userDetails)) {

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = jwtgenVal.getAuthenticationToken(token, SecurityContextHolder.getContext().getAuthentication(), userDetails);
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}
		}
		filterChain.doFilter(request, response);
	}
	
	
}
