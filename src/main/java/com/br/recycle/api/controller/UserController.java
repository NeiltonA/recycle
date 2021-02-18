package com.br.recycle.api.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.br.recycle.api.exception.BusinessException;
import com.br.recycle.api.model.User;
import com.br.recycle.api.payload.PasswordInput;
import com.br.recycle.api.payload.UserProfile;
import com.br.recycle.api.repository.UserRepository;
import com.br.recycle.api.security.CurrentUser;
import com.br.recycle.api.security.MainUser;
import com.br.recycle.api.service.UserService;

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

    @GetMapping("/user/me")
    @PreAuthorize("hasRole('USER')")
    // @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public UserProfile getCurrentUser(@CurrentUser MainUser currentUser) {
        UserProfile userSummary = new UserProfile(currentUser.getId(), currentUser.getUsername(),
                currentUser.getName());
        return userSummary;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<User>> findAll() {
        try {
            List<User> usersList = userRepository.findAll();

            return new ResponseEntity<>(usersList, HttpStatus.OK);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<Optional<User>> findById(@PathVariable("id") Long id) {
        Optional<User> usersList = userRepository.findById(id);
        if (!usersList.isEmpty()) {
            return new ResponseEntity<Optional<User>>(usersList, HttpStatus.OK);
        }
        return new ResponseEntity<Optional<User>>(usersList, HttpStatus.NO_CONTENT);
    }


    @PreAuthorize("hasRole('USER')")
    @GetMapping("/user/checkEmailAvailability")
    public Boolean checkEmailAvailability(@RequestParam(value = "email") String email) {
        Boolean isAvailable = !userRepository.existsByEmail(email);
        return isAvailable;
    }


    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @PutMapping("/{userId}/password")
    @ResponseStatus(HttpStatus.OK)
    public void updatePassword(@PathVariable Long userId, @RequestBody @Valid PasswordInput password) {
        try {
            service.changePassword(userId, password.getCurrentPassword(), password.getNewPassword());
        } catch (Exception e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

    @ApiOperation(value = "Method responsible for changing the user")
    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> update(@PathVariable("id") long id, @RequestBody User user) {
        try {
            Optional<User> us = userRepository.findById(id);
            if (us.isPresent()) {
                user.setId(us.get().getId());
                return new ResponseEntity<>(userRepository.save(user), HttpStatus.OK);
            }
            return ResponseEntity.notFound().build();
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
}
