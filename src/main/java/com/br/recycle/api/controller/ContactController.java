package com.br.recycle.api.controller;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.recycle.api.commons.UriConstants;
import com.br.recycle.api.payload.ApiResponse;
import com.br.recycle.api.payload.ContactInput;
import com.br.recycle.api.service.ContactService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * Classe responsável por ser a Contreller e conter o Endpoint de endereços da
 * aplicação.
 */
@RestController
@RequestMapping(UriConstants.URI_CONTACT)
@Api(value = "Contact", description = "REST API for Contact", tags = { "Contact" })
public class ContactController {

	private ContactService service;

	@Autowired
	public ContactController(ContactService service) {
		this.service = service;

	}

	@ApiOperation(value = "Method responsible for contact email")
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ApiResponse> send(@Valid @RequestBody ContactInput contact) {

		service.send(contact);

		return ResponseEntity.ok(new ApiResponse(true, "E-mail enviado com sucesso!"));
	}
	


}
