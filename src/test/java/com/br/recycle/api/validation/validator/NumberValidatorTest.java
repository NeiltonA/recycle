package com.br.recycle.api.validation.validator;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.validation.ConstraintValidatorContext;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

public class NumberValidatorTest {

	@Autowired
	private ConstraintValidatorContext context;
	
	private NumberValidator numberValidator;
	
	@BeforeEach
	public void setUp() {
		numberValidator = new NumberValidator();
		Mockito.mock(NumberValidation.class);
	}
	
	@Test
	public void testIsValidNUmberValidNull() {
		NumberValidation numberValidation = Mockito.mock(NumberValidation.class);
		numberValidator.initialize(numberValidation);
		assertTrue(numberValidator.isValid(null, context));
	}
	
	@Test
	public void testIsValidNUmberValid1() {
		NumberValidation numberValidation = Mockito.mock(NumberValidation.class);
		numberValidator.initialize(numberValidation);
		assertTrue(numberValidator.isValid("1", context));
	}
	
	@Test
	public void testIsValidNUmberValid2() {
		NumberValidation numberValidation = Mockito.mock(NumberValidation.class);
		numberValidator.initialize(numberValidation);
		assertTrue(numberValidator.isValid(String.valueOf(1), context));
	}
	
	@Test
	public void testIsValidNUmberInvalid1() {
		NumberValidation numberValidation = Mockito.mock(NumberValidation.class);
		numberValidator.initialize(numberValidation);
		assertFalse(numberValidator.isValid("A", context));
	}
	
	@Test
	public void testIsValidNUmberInvalid2() {
		NumberValidation numberValidation = Mockito.mock(NumberValidation.class);
		numberValidator.initialize(numberValidation);
		assertFalse(numberValidator.isValid("1A", context));
	}
}
