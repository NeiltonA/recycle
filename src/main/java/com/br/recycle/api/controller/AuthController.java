package com.br.recycle.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.recycle.api.exception.BusinessException;
import com.br.recycle.api.payload.LoginRequest;
import com.br.recycle.api.repository.UserRepository;
import com.br.recycle.api.security.JwtTokenProvider;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/api/v1/auth")
@Api(value = "Auth", description = "REST API for Auth", tags = { "Auth" })
public class AuthController {

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	JwtTokenProvider tokenProvider;

	@PostMapping
	public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
		try {
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

			SecurityContextHolder.getContext().setAuthentication(authentication);

			// String jwt = tokenProvider.generateToken(authentication);
			Object jwt = tokenProvider.generateToken(authentication);

			return ResponseEntity.ok(jwt);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

}
