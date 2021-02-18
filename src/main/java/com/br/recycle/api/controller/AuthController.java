package com.br.recycle.api.controller;

import java.util.Collections;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.recycle.api.exception.AppException;
import com.br.recycle.api.exception.BusinessException;
import com.br.recycle.api.exception.UserNotFoundException;
import com.br.recycle.api.model.Role;
import com.br.recycle.api.model.User;
import com.br.recycle.api.payload.ApiResponse;
import com.br.recycle.api.payload.LoginRequest;
import com.br.recycle.api.repository.RoleRepository;
import com.br.recycle.api.repository.UserRepository;
import com.br.recycle.api.security.JwtTokenProvider;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider tokenProvider;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginRequest.getEmail(), loginRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            //String jwt = tokenProvider.generateToken(authentication);
            Object jwt = tokenProvider.generateToken(authentication);

            return ResponseEntity.ok(jwt);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody @Valid User user) {
        try {
//			if (userRepository.existsByUsername(user.getUsername())) {
//				return new ResponseEntity<Object>(new ApiResponse(false, "Username is already taken!"),
//						HttpStatus.BAD_REQUEST);
//			}

            if (userRepository.existsByEmail(user.getEmail())) {
                return new ResponseEntity<Object>(new ApiResponse(false, "Email Address already in use!"),
                        HttpStatus.BAD_REQUEST);
            }

            // Creating user's account
            // User user = new User();

            user.setPassword(passwordEncoder.encode(user.getPassword()));

            Role userRole = roleRepository.findByName(user.getRole().name())
                    .orElseThrow(() -> new AppException("User Role not set."));

            user.setRoles(Collections.singleton(userRole));

            // User result = userRepository.save(user);

//        URI location = ServletUriComponentsBuilder
//                .fromCurrentContextPath().path("/users/{username}")
//                .buildAndExpand(result.getUsername()).toUri();
            return new ResponseEntity<>(userRepository.save(user), HttpStatus.OK);
        } catch (UserNotFoundException e) {
            throw new BusinessException(e.getMessage(), e);

        }
    }
}
