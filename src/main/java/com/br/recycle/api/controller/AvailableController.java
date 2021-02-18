package com.br.recycle.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/api/v1/available")
@Api(value = "Available", description = "REST API for Available", tags = { "Available" })
public class AvailableController {

	@GetMapping
	public ResponseEntity<String> healthCheck(){
		return ResponseEntity.ok("{\"status\":\"OK\"}");
	}
}
