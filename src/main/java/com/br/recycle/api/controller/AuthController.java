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

import com.br.recycle.api.assembler.AuthAssembler;
import com.br.recycle.api.commons.UriConstants;
import com.br.recycle.api.exception.BadRequestException;
import com.br.recycle.api.exception.InternalServerException;
import com.br.recycle.api.exception.TokenRefreshException;
import com.br.recycle.api.exception.UserInvalidException;
import com.br.recycle.api.model.RefreshToken;
import com.br.recycle.api.payload.ApiResponse;
import com.br.recycle.api.payload.JwtAuthenticationResponse;
import com.br.recycle.api.payload.LogOutRequest;
import com.br.recycle.api.payload.LoginRequest;
import com.br.recycle.api.payload.TokenRefreshRequest;
import com.br.recycle.api.payload.TokenRefreshResponse;
import com.br.recycle.api.security.JwtTokenProvider;
import com.br.recycle.api.security.MainUser;
import com.br.recycle.api.service.RefreshTokenService;
import com.br.recycle.api.validation.AuthValidation;

import io.swagger.annotations.Api;

/**
 * Classe responsável por ser a Contreller e conter o Endpoint de autenticação
 * da aplicação.
 */
@RestController
@RequestMapping(UriConstants.URI_BASE_AUTH)
@Api(value = "Auth", description = "REST API for Auth", tags = { "Auth" })
public class AuthController {

	private AuthenticationManager authenticationManager;
	private RefreshTokenService refreshTokenService;
	private JwtTokenProvider tokenProvider;

	@Autowired
	public AuthController(AuthenticationManager authenticationManager, RefreshTokenService refreshTokenService,
			JwtTokenProvider tokenProvider) {
		this.authenticationManager = authenticationManager;
		this.refreshTokenService = refreshTokenService;
		this.tokenProvider = tokenProvider;
	}

	/**
	 * Método responsável por autenticar os dados de login do usuário para acessar a
	 * aplicação.
	 * 
	 * @param {@code LoginRequest} - loginRequest
	 * @return {@code ResponseEntity<?>} - Uma resposta de saída da aplicação com os
	 *         dados da autenticação do usuário. - Caso tenha algum erro, retorna o
	 *         status <b>403</b> informando que o usuário ou senha está inválido.
	 */
	@PostMapping(UriConstants.URI_AUTH_ACCESS)
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
		try {
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

			SecurityContextHolder.getContext().setAuthentication(authentication);

			MainUser userDetails = (MainUser) authentication.getPrincipal();
			String jwt = tokenProvider.generateJwtToken(userDetails);
			RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());

			JwtAuthenticationResponse jwtAuthenticationResponse = AuthAssembler.toModelResponse(jwt, userDetails,
					refreshToken);

			return ResponseEntity.ok(jwtAuthenticationResponse);

		} catch (Exception exception) {
			throw new UserInvalidException("Usuário ou senha, ínvalido!");
		}
	}

	/**
	 * Método responsável autenticar um <i>refreshToken</i> informado na requisição.
	 * 
	 * @param {@code TokenRefreshRequest} - refreshRequest
	 * @return {@code ResponseEntity<?>} - Uma resposta de saída da aplicação com o
	 *         token e o refreshToken - Caso um dado seja informado inválido,
	 *         retorna o status <b>400</b>, informando onde está o erro na
	 *         requisição e o motivo. - Caso tenha algum erro, retorna o status
	 *         <b>403</b> informando que que o token não foi encontrado. - Caso
	 *         tenha algum erro, retorna o status <b>500</b> informando que houve
	 *         erro na aplicação.
	 */
	@PostMapping(UriConstants.URI_AUTH_REFRESH)
	public ResponseEntity<?> refreshtoken(@RequestBody TokenRefreshRequest refreshRequest) {
		try {

			String requestRefreshToken = AuthValidation.validate(refreshRequest.getRefreshToken());
			boolean refresh = true;
			return refreshTokenService.findByToken(requestRefreshToken).map(refreshTokenService::verifyExpiration)
					.map(RefreshToken::getUser).map(user -> {
						String token = tokenProvider.generateTokenFromUsername(user.getId(), refresh);
						return ResponseEntity.ok(new TokenRefreshResponse(token, requestRefreshToken));
					})
					.orElseThrow(() -> new TokenRefreshException("O token de atualização não está no banco de dados!"));

		} catch (BadRequestException exception) {
			throw new BadRequestException(exception.getMessage(), exception);

		} catch (TokenRefreshException exception) {
			throw new TokenRefreshException(exception.getMessage(), exception);

		} catch (Exception e) {
			throw new InternalServerException(e.getMessage(), e);
		}
	}

	/**
	 * Método responsável realizar o logout da aplicação, com os dados de sair da
	 * aplicação.
	 * 
	 * @param {@code LogOutRequest} - logOutRequest
	 * @return {@code ResponseEntity<?>} - Uma resposta de saída da aplicação no
	 *         qual o logout foi realizado com sucesso
	 */
	@PostMapping(UriConstants.URI_AUTH_LOGOUT)
	public ResponseEntity<?> logoutUser(@Valid @RequestBody LogOutRequest logOutRequest) {
		refreshTokenService.deleteByUserId(logOutRequest.getUserId());
		return ResponseEntity.ok(new ApiResponse(true, "Logout bem-sucedido!"));
	}
}
