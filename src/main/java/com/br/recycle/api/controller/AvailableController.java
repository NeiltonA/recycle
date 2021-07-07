package com.br.recycle.api.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.recycle.api.commons.UriConstants;

import io.swagger.annotations.Api;

/**
 * Classe de controller para mapear a saúde da aplicação e verificar
 * se está retornando 200.
 *
 */
@RestController
@RequestMapping(UriConstants.URI_BASE_AVAILABLE)
@Api(value = "Available", description = "REST API for Available", tags = { "Available" })
public class AvailableController {

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> healthCheck(){
		return ResponseEntity.ok("{\"status\":\"OK\"}");
	}
}
