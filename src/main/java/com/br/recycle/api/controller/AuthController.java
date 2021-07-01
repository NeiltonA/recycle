package com.br.recycle.api.controller;

import javax.validation.Valid;

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
import com.br.recycle.api.exception.TokenRefreshException;
import com.br.recycle.api.model.RefreshToken;
import com.br.recycle.api.payload.ApiResponse;
import com.br.recycle.api.payload.JwtAuthenticationResponse;
import com.br.recycle.api.payload.LogOutRequest;
import com.br.recycle.api.payload.LoginRequest;
import com.br.recycle.api.payload.TokenRefreshRequest;
import com.br.recycle.api.payload.TokenRefreshResponse;
import com.br.recycle.api.repository.UserRepository;
import com.br.recycle.api.security.JwtTokenProvider;
import com.br.recycle.api.security.MainUser;
import com.br.recycle.api.service.RefreshTokenService;

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
	RefreshTokenService refreshTokenService;

	@Autowired
	JwtTokenProvider tokenProvider;

	@PostMapping("/access")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
		try {
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

			SecurityContextHolder.getContext().setAuthentication(authentication);

			MainUser userDetails = (MainUser) authentication.getPrincipal();

			String jwt = tokenProvider.generateJwtToken(userDetails);

			// List<String> roles = userDetails.getAuthorities().stream().map(item ->
			// item.getAuthority())
			// .collect(Collectors.toList());

			RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());

			return ResponseEntity
					.ok(new JwtAuthenticationResponse(jwt, userDetails.getFlowIndicator(), userDetails.getActive(),
							userDetails.getAuthorities().toString(), refreshToken.getToken(), userDetails.getId()));
		} catch (Exception e) {
			throw new BusinessException("Usuário ou senha ínvalido!");
		}

	}

	@PostMapping("/refresh")
	public ResponseEntity<?> refreshtoken(@RequestBody TokenRefreshRequest request) {
		try {
			String requestRefreshToken = request.getRefreshToken();

			return refreshTokenService.findByToken(requestRefreshToken).map(refreshTokenService::verifyExpiration)
					.map(RefreshToken::getUser).map(user -> {
						String token = tokenProvider.generateTokenFromUsername(user.getEmail());
						return ResponseEntity.ok(new TokenRefreshResponse(token, requestRefreshToken));
					}).orElseThrow(
							() -> new TokenRefreshException(requestRefreshToken, "O token de atualização não está no banco de dados!"));
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@PostMapping("/logout")
	public ResponseEntity<?> logoutUser(@Valid @RequestBody LogOutRequest logOutRequest) {
		try {
			refreshTokenService.deleteByUserId(logOutRequest.getUserId());
			return ResponseEntity.ok(new ApiResponse(true, "Logout bem-sucedido!"));
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}
}
