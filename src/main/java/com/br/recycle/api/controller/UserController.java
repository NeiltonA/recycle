package com.br.recycle.api.controller;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.br.recycle.api.assembler.UserDtoAssembler;
import com.br.recycle.api.exception.AppException;
import com.br.recycle.api.exception.BusinessException;
import com.br.recycle.api.exception.UserNotFoundException;
import com.br.recycle.api.model.Role;
import com.br.recycle.api.model.User;
import com.br.recycle.api.payload.ApiResponse;
import com.br.recycle.api.payload.PasswordInput;
import com.br.recycle.api.payload.UserDtoIn;
import com.br.recycle.api.payload.UserDtoOut;
import com.br.recycle.api.repository.RoleRepository;
import com.br.recycle.api.repository.UserRepository;
import com.br.recycle.api.service.PwService;
import com.br.recycle.api.service.UserService;
import com.br.recycle.api.service.email.SendEmail;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/v1/user")
@Api(value = "User", description = "REST API for User", tags = { "User" })
public class UserController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserService service;

	@Autowired
	private PwService pwService;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	SendEmail sendEmail;

	@Autowired
	private UserDtoAssembler userDtoAssembler;

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public List<UserDtoOut> findAll() {
		try {
			List<User> users = userRepository.findAll();

			return userDtoAssembler.toCollectionModel(users);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public UserDtoOut findById(@PathVariable("id") Long id) {
		User usersList = service.fetchOrFail(id);
		return userDtoAssembler.toModel(usersList);

	}

	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public Object save(@RequestBody @Valid User user) {
		try {

			if (userRepository.existsByEmail(user.getEmail())) {
				return new ResponseEntity<Object>(new ApiResponse(false, "Email Address already in use!"),
						HttpStatus.BAD_REQUEST);
			}
			user.setPassword(passwordEncoder.encode(user.getPassword()));

			Role userRole = roleRepository.findByName(user.getRole().name())
					.orElseThrow(() -> new AppException("User Role not set."));

			user.setRoles(Collections.singleton(userRole));

			user = userRepository.save(user);
			return userDtoAssembler.toModel(user);
		} catch (UserNotFoundException e) {
			throw new BusinessException(e.getMessage(), e);

		}
	}

	@PutMapping(value = "/{id}/password", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public void updatePassword(@PathVariable Long id, @RequestBody @Valid PasswordInput password) {
		try {
			service.changePassword(id, password.getCurrentPassword(), password.getNewPassword());
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@ApiOperation(value = "Method responsible for changing the user")
	@PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public Object update(@PathVariable("id") Long id, @RequestBody @Valid UserDtoIn userdtoIn) {
		try {
			User user = service.fetchOrFail(id);
			userDtoAssembler.copyToDomainObject(userdtoIn, user);
			user = service.save(user);
			return userDtoAssembler.toModel(user);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}

	}

	@ApiOperation(value = "Method responsible for removing the user")
	@DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
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
	@PostMapping(value = "/forgot-password", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> forgotPassword(@RequestParam String email) {
		try {
			String response = pwService.forgotPassword(email);

			if (!response.startsWith("Invalid")) {
				   String link = ServletUriComponentsBuilder
			                .fromCurrentContextPath().path("/reset-password?token=" + response)
			                .buildAndExpand().toUriString();
				   
				   sendEmail.sendDespatchEmail(email, link);
			}
			return new ResponseEntity<Object>(new ApiResponse(true, "Email sent successfully to update password."),
					HttpStatus.OK);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@ApiOperation(value = "Method responsible for reset-password the user")
	@PutMapping(value = "/reset-password", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> resetPassword(HttpServletRequest request, @RequestParam String password) {
		String token = request.getHeader("Authorization");
		try {
			pwService.resetPassword(token, password);
			return new ResponseEntity<Object>(new ApiResponse(true, "Your password successfully updated."),
					HttpStatus.OK);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}
}
