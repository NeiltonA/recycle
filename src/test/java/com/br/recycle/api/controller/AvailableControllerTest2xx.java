package com.br.recycle.api.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * Método responsável por validar os testes da classe de 
 * controller do healthCheck
 *
 */
@ExtendWith(SpringExtension.class)
public class AvailableControllerTest2xx {

	@InjectMocks
	private AvailableController availableController = new AvailableController();;
	
	/**
	 * Método responsável por validar o cenário de 200.
	 */
	@Test
	public void testHealthCheckSuccess() {
	
		ResponseEntity<String> healthCheck = availableController.healthCheck();
		assertEquals("{\"status\":\"OK\"}", healthCheck.getBody());
		assertEquals(HttpStatus.OK, healthCheck.getStatusCode());
	}
}
