package com.br.recycle.api.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.br.recycle.api.assembler.UserDtoAssembler;
import com.br.recycle.api.commons.UriConstants;
import com.br.recycle.api.exception.BusinessException;
import com.br.recycle.api.model.User;
import com.br.recycle.api.payload.ApiResponse;
import com.br.recycle.api.payload.PasswordInput;
import com.br.recycle.api.payload.UserDtoIn;
import com.br.recycle.api.payload.UserDtoOut;
import com.br.recycle.api.payload.UserInput;
import com.br.recycle.api.repository.UserRepository;
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
@RestController
@RequestMapping(UriConstants.URI_BASE_USER)
@Api(value = "User", description = "REST API for User", tags = { "User" })
@Log4j2
public class UserController {

	private UserRepository userRepository;
	private UserService userService;
	private PwService pwService;
	private SendEmail sendEmail;
	private UserDtoAssembler userDtoAssembler;
	
	@Autowired
	public UserController(UserRepository userRepository, UserService userService, PwService pwService,
			SendEmail sendEmail, UserDtoAssembler userDtoAssembler) {
		this.userRepository = userRepository;
		this.userService = userService;
		this.pwService = pwService;
		this.sendEmail = sendEmail;
		this.userDtoAssembler = userDtoAssembler;
	}

	/**
	 * Método responsável por conter o endpoint que busca todos os usuários na base
	 * de dados.
	 * @return {@code List<UserDtoOut} - Retorna uma lista de usuários.
	 */
	//@PreAuthorize("hasRole('USER')")
	@ApiOperation(value = "Method responsible for find all users")
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public List<UserDtoOut> findAll() {
		List<User> users = userService.findAll();
		return userDtoAssembler.toCollectionModel(users);
	}

	/**
	 * Método responsável por conter o endpoint que busca o usuário por ID na base
	 * de dados.
	 * @param {@codeLong} - id
	 * @return {@code UserDtoOut} - Retorna os dados do usuário por ID
	 */
	@ApiOperation(value = "Method responsible for find user by id")
	@GetMapping(value = UriConstants.URI_USER_ID, produces = MediaType.APPLICATION_JSON_VALUE)
	public UserDtoOut findById(@PathVariable("id") Long id) {
		User usersList = userService.fetchOrFail(id);
		return userDtoAssembler.toModel(usersList);
	}

	/**
	 * Método responsável por conter o endpoint que cadastra o usuário de acordo
	 * com os dados informados na entrada.
	 * @param {@code UserInput} - userInput
	 * @return {@code ResponseEntity<ApiResponse>} - Uma entidade de API de sucesso
	 * 		do cadastro de sucesso do usuário.
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

	@ApiOperation(value = "Method responsible for  data update user")
	@PatchMapping(value = UriConstants.URI_USER_ID, produces = MediaType.APPLICATION_JSON_VALUE)
	public Object update(@PathVariable("id") Long id, @RequestBody @Valid UserDtoIn userDtoIn) {
		try {

			User user = userDtoAssembler.toDomainObject(userDtoIn);
			Optional<User> userOptional = userRepository.findById(id);
			if (userOptional.isPresent()) {
				user.setId(userOptional.get().getId());
				user.setPassword(userOptional.get().getPassword());
				user = userService.update(user);
				return ResponseEntity.ok(new ApiResponse(true, "Usuário alterado com sucesso!"));
			}
			return ResponseEntity.notFound().build();
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@ResponseStatus(HttpStatus.OK)
	@PatchMapping(value = UriConstants.URI_USER_UPDATE_PASSWORD, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> updatePassword(@PathVariable Long id, @RequestBody @Valid PasswordInput password) {
		try {
			userService.changePassword(id, password.getCurrentPassword(), password.getNewPassword());
			return new ResponseEntity<Object>(new ApiResponse(true, "Sua senha foi alterada com sucesso."),
					HttpStatus.OK);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@ApiOperation(value = "Method responsible for removing the user")
	@DeleteMapping(value = UriConstants.URI_USER_ID, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<HttpStatus> delete(@PathVariable("id") long id) {
		try {
			Optional<User> user = userRepository.findById(id);
			if (user.isPresent()) {
				userRepository.deleteById(id);
				return new ResponseEntity<>(HttpStatus.OK);
			}
			return ResponseEntity.notFound().build();
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@ApiOperation(value = "Method responsible for forgot-password the user")
	@PostMapping(value = UriConstants.URI_USER_FORGOT_PASSWORD, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> forgotPassword(@RequestParam String email) {
		try {
			String response = pwService.forgotPassword(email);

			if (!response.startsWith("Invalid")) {
				String link = ServletUriComponentsBuilder.fromCurrentContextPath()
						.path("/reset-password?token=" + response).buildAndExpand().toUriString();

				sendEmail.sendDespatchEmail(email, link);
			}
			log.error("Email enviado com sucesso para atualização de senha.");
			return new ResponseEntity<Object>(
					new ApiResponse(true, "E-mail enviado com sucesso para atualização de senha."), HttpStatus.OK);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@ApiOperation(value = "Method responsible for reset-password the user")
	@PatchMapping(value = UriConstants.URI_USER_RESET_PASSWORD, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> resetPassword(HttpServletRequest request, @RequestParam String password) {
		String token = request.getHeader("Authorization");
		try {
			pwService.resetPassword(token, password);
			log.error("Sua senha foi atualizada com sucesso.");
			return new ResponseEntity<Object>(new ApiResponse(true, "Sua senha foi atualizada com sucesso."),
					HttpStatus.OK);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}
}
