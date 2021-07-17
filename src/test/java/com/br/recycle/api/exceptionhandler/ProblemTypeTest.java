package com.br.recycle.api.exceptionhandler;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class ProblemTypeTest {
	
	@Test
	public void testGetTitle() {
		ProblemType problemType = ProblemType.INVALID_DATA;
		assertEquals("Dados inválidos", problemType.getTitle());
		assertEquals("https://recycle.com.br/dados-invalidos", problemType.getUri());
	}
}