package com.br.recycle.api.controller;

import java.net.URI;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.br.recycle.api.assembler.UserDtoAssembler;
import com.br.recycle.api.commons.UriConstants;
import com.br.recycle.api.exception.BusinessException;
import com.br.recycle.api.model.User;
import com.br.recycle.api.payload.ApiResponse;
import com.br.recycle.api.payload.PasswordInput;
import com.br.recycle.api.payload.PwEmailInput;
import com.br.recycle.api.payload.PwInput;
import com.br.recycle.api.payload.UserDtoIn;
import com.br.recycle.api.payload.UserDtoOut;
import com.br.recycle.api.payload.UserInput;
import com.br.recycle.api.service.PwService;
import com.br.recycle.api.service.UserService;
import com.br.recycle.api.service.email.SendEmail;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;

/**
 * Classe responsável por ser a Contreller e conter o Endpoint de usuário da
 * aplicação.
 */
@Log4j2
@RestController
@RequestMapping(UriConstants.URI_BASE_USER)
@Api(value = "User", description = "REST API for User", tags = { "User" })
public class UserController {

	@Value("${url.front.reset.password}")
	protected String urlfront;
	private UserService userService;
	private PwService pwService;
	private SendEmail sendEmail;
	private UserDtoAssembler userDtoAssembler;

	@Autowired
	public UserController(UserService userService, PwService pwService, SendEmail sendEmail, 
			UserDtoAssembler userDtoAssembler) {
		this.userService = userService;
		this.pwService = pwService;
		this.sendEmail = sendEmail;
		this.userDtoAssembler = userDtoAssembler;
	}

	/**
	 * Método responsável por conter o endpoint que busca todos os usuários na base
	 * de dados.
	 * 
	 * @return {@code List<UserDtoOut} - Retorna uma lista de usuários.
	 */
	// @PreAuthorize("hasRole('USER')")
	@ApiOperation(value = "Method responsible for find all users")
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public List<UserDtoOut> findAll() {
		
		List<User> users = userService.findAll();

		return userDtoAssembler.toCollectionModel(users);
	}

	/**
	 * Método responsável por conter o endpoint que busca o usuário por ID na base
	 * de dados.
	 * 
	 * @param {@codeLong} - id
	 * @return {@code UserDtoOut} - Retorna os dados do usuário por ID
	 */
	@ApiOperation(value = "Method responsible for find user by id")
	@GetMapping(value = UriConstants.URI_USER_ID, produces = MediaType.APPLICATION_JSON_VALUE)
	public UserDtoOut findById(@PathVariable("id") Long id) {
		
		User usersList = userService.findById(id);

		return userDtoAssembler.toModel(usersList);
	}

	/**
	 * Método responsável por conter o endpoint que cadastra o usuário de acordo com
	 * os dados informados na entrada.
	 * 
	 * @param {@code UserInput} - userInput
	 * @return {@code ResponseEntity<ApiResponse>} - Uma entidade de API de sucesso
	 *         do cadastro de sucesso do usuário.
	 */
	@ApiOperation(value = "Method responsible for user's a create")
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ApiResponse> save(@RequestBody @Valid UserInput userInput) {
		
		User user = userDtoAssembler.toDomainObject(userInput);
		userService.save(user);

		log.info("Registered successfully -> []");
		return ResponseEntity.created(URI.create("")).body(new ApiResponse(true, "Usuário registrado com sucesso!"));
	}

	/**
	 * Método responsável por conter o endpoint que atualizar parcialmente o usuário
	 * de acordo com os dados informados na entrada e o id do usuário.
	 * 
	 * @param {@code Long} - id
	 * @param {@code Long} - userDtoIn
	 * @return {@code ResponseEntity<ApiResponse>} - Uma entidade de API de sucesso
	 *         de atualização de sucesso do usuário.
	 */
	@ApiOperation(value = "Method responsible for updating data the user")
	@PatchMapping(value = UriConstants.URI_USER_ID, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ApiResponse> updatPatch(@PathVariable("id") Long id, @RequestBody @Valid UserDtoIn userDtoIn) {
		
		User user = userDtoAssembler.toDomainObject(userDtoIn);
		user = userService.updatPatch(user, id);

		return ResponseEntity.ok(new ApiResponse(true, "Usuário alterado com sucesso!"));
	}

	/**
	 * Método responsável por conter o endpoint que atualizar a senha do usuário de
	 * acordo com os dados informados na entrada e o id do usuário.
	 * 
	 * @param {@code Long} - id
	 * @param {@code PasswordInput} - passwordInput
	 * @return {@code ResponseEntity<ApiResponse>} - Que a senha foi alterada com
	 *         sucesso.
	 */
	@ApiOperation(value = "Method responsible for updating user's password")
	@PatchMapping(value = UriConstants.URI_USER_UPDATE_PASSWORD, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ApiResponse> updatePassword(@PathVariable Long id,
			@RequestBody @Valid PasswordInput passwordInput) {
		
		userService.changePassword(id, passwordInput.getCurrentPassword(), passwordInput.getNewPassword());

		return ResponseEntity.ok(new ApiResponse(true, "Sua senha foi alterada com sucesso."));
	}

	/**
	 * Método responsável por conter o endpoint que deletar um usuário da base de dados de
	 * acordo com o id do usuário informado na entrada.
	 * 
	 * @param {@code Long} - id
	 * @return {@code ResponseEntity<Void>} - Caso a deleção ocorra com sucesso, retorna um
	 * 	corpo vazio, com HttpStatus 204
	 */
	@ApiOperation(value = "Method responsible for removing the user")
	@DeleteMapping(value = UriConstants.URI_USER_ID, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
		
		userService.deleteById(id);
		
		return ResponseEntity.noContent().build();
	}

	@ApiOperation(value = "Method responsible for forgot-password the user")
	@PostMapping(value = UriConstants.URI_USER_FORGOT_PASSWORD, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> forgotPassword(@RequestBody @Valid  PwEmailInput pwEmailInput) {
		try {
			String response = pwService.forgotPassword(pwEmailInput.getEmail());

			if (!response.startsWith("Invalid")) {
				String link = (this.urlfront + response);
				
				sendEmail.sendDespatchEmail(pwEmailInput.getEmail(), link);
			}
			log.info("Email enviado com sucesso para atualização de senha.");
			return new ResponseEntity<Object>(
					new ApiResponse(true, "E-mail enviado com sucesso para atualização de senha."), HttpStatus.OK);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@ApiOperation(value = "Method responsible for reset-password the user")
	@PatchMapping(value = UriConstants.URI_USER_RESET_PASSWORD, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> resetPassword(HttpServletRequest request, @RequestBody @Valid  PwInput pwInput) {
		String token = request.getHeader("Authorization");
		try {
			pwService.resetPassword(token, pwInput.getPassword());
			log.info("Sua senha foi atualizada com sucesso.");
			return new ResponseEntity<Object>(new ApiResponse(true, "Sua senha foi atualizada com sucesso."),
					HttpStatus.OK);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}
}
