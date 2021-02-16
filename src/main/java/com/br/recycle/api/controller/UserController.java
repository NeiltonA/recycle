package com.br.recycle.api.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.br.recycle.api.exception.NegocioException;
import com.br.recycle.api.exception.ResourceNotFoundException;
import com.br.recycle.api.model.User;
import com.br.recycle.api.payload.PasswordInput;
import com.br.recycle.api.payload.UserProfile;
import com.br.recycle.api.repository.UserRepository;
import com.br.recycle.api.security.CurrentUser;
import com.br.recycle.api.security.UserPrincipal;
import com.br.recycle.api.service.UserService;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

	@Autowired
	private UserRepository userRepository;
	
	
	@Autowired
	private UserService service;

	@GetMapping("/user/me")
	@PreAuthorize("hasRole('USER')")
	// @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public UserProfile getCurrentUser(@CurrentUser UserPrincipal currentUser) {
		UserProfile userSummary = new UserProfile(currentUser.getId(), currentUser.getUsername(),
				currentUser.getName());
		return userSummary;
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping
	public ResponseEntity<List<User>> findAll() {
		try {
		List<User> todasUsuarios = userRepository.findAll();
		
		return new ResponseEntity<>(todasUsuarios, HttpStatus.OK);
		}catch (Exception e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/{id}")
	public ResponseEntity<Optional<User>> findById(@PathVariable("id") Long id) {

		Optional<User> todasUsuarios = userRepository.findById(id);
		if (!todasUsuarios.isEmpty()) {
			return new ResponseEntity<Optional<User>>(todasUsuarios, HttpStatus.OK);
		}
		return new ResponseEntity<Optional<User>>(todasUsuarios, HttpStatus.NO_CONTENT);

	}

	@PreAuthorize("hasRole('USER')")
	@GetMapping("/user/checkUsernameAvailability")
	public Boolean checkUsernameAvailability(@RequestParam(value = "username") String username) {
		Boolean isAvailable = !userRepository.existsByUsername(username);
		return isAvailable;
	}

	@PreAuthorize("hasRole('USER')")
	@GetMapping("/user/checkEmailAvailability")
	public Boolean checkEmailAvailability(@RequestParam(value = "email") String email) {
		Boolean isAvailable = !userRepository.existsByEmail(email);
		return isAvailable;
	}

	@GetMapping("/users/{username}")
	public UserProfile getUserProfile(@PathVariable(value = "username") String username) {
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new ResourceNotFoundException("User", "username", username));

		UserProfile userProfile = new UserProfile(user.getId(), user.getUsername(), user.getName());

		return userProfile;
	}
	
	
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	@PutMapping("/{userId}/password")
	@ResponseStatus(HttpStatus.OK)
	public void updatePassword(@PathVariable Long userId, @RequestBody @Valid PasswordInput password) {
		try {
		service.alterarSenha(userId, password.getPasswordAtual(), password.getNovoPassword());
	}catch (Exception e) {
		throw new NegocioException(e.getMessage(), e);

	}
	}

}
