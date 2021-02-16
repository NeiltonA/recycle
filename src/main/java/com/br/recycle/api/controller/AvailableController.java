package com.br.recycle.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/available")
public class AvailableController {

	@GetMapping
	public ResponseEntity<String> healthCheck(){
		return ResponseEntity.ok("{\"status\":\"OK\"}");
	}
}
