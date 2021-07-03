package com.br.recycle.api.exceptionhandler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;

import com.br.recycle.api.exception.BadRequestException;
import com.br.recycle.api.exception.TokenRefreshException;
import com.br.recycle.api.exception.UserNotFoundException;

/**
 * Classe responsável por validar os cenários de testes
 * para as exceçoes lançadas na aplicação e tratado
 * com o ControllerAdvice.
 * 
 * @author Caio Henrique do Carmo Bastos
 * @since 03/07/2021
 *
 */
public class ApiExceptionHandlerTest {

	private ApiExceptionHandler apiExceptionHandler;
	
	@MockBean
	private WebRequest webRequest;
	
	@BeforeEach
	public void setUp() {
		apiExceptionHandler = new ApiExceptionHandler();
	}
	
	/**
	 * Método responsável por validar o cenário de exceção quando é lançada
	 * uma exception <b>BadRequest</b> e o ControllerAdvice trata.
	 */
	@Test
	public void testHandleBadRequestSuccess() {
		BadRequestException badRequestException = new BadRequestException("Erro na validação dos campos");
		
		ResponseEntity<Object> handleBadRequest = apiExceptionHandler.handleBadRequest(badRequestException, webRequest);
		assertNotNull(handleBadRequest);
		assertEquals(HttpStatus.BAD_REQUEST, handleBadRequest.getStatusCode());
	}
	
	/**
	 * Método responsável por validar o cenário de exceção quando é lançada
	 * uma exception <b>Forbidden</b> e o ControllerAdvice trata.
	 */
	@Test
	public void testHandleForbiddenSuccess() {
		TokenRefreshException tokenRefreshException = new TokenRefreshException("Acesso negado na aplicação");
		
		ResponseEntity<Object> handleForbidden= apiExceptionHandler.handleForbidden(tokenRefreshException, webRequest);
		assertNotNull(handleForbidden);
		assertEquals(HttpStatus.FORBIDDEN, handleForbidden.getStatusCode());
	}
	
	
	/**
	 * Método responsável por validar o cenário de exceção quando é lançada
	 * uma exception <b>NotFound</b> e o ControllerAdvice trata.
	 */
	@Test
	public void testHandleEntityNotFoundExceptionSuccess() {
		UserNotFoundException userNotFoundException = new UserNotFoundException(1L);
		
		ResponseEntity<?> handleForbidden= apiExceptionHandler.handleEntityNotFoundException(userNotFoundException, webRequest);
		assertNotNull(handleForbidden);
		assertEquals(HttpStatus.NOT_FOUND, handleForbidden.getStatusCode());
	}
}
