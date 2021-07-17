package com.br.recycle.api.validation.validator;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.validation.ConstraintValidatorContext;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

public class ZipcodeValidatorTest {

	@Autowired
	private ConstraintValidatorContext context;
	
	private ZipcodeValidator zipcodeValidator;
	
	@BeforeEach
	public void setUp() {
		zipcodeValidator = new ZipcodeValidator();
		Mockito.mock(ZipcodeValidation.class);
	}
	
	@Test
	public void testIsValidZipcodeValidNull() {
		ZipcodeValidation zipcodeValidation = Mockito.mock(ZipcodeValidation.class);
		zipcodeValidator.initialize(zipcodeValidation);
		assertTrue(zipcodeValidator.isValid(null, context));
	}
	
	@Test
	public void testIsValidZipcodeValid1() {
		ZipcodeValidation zipcodeValidation = Mockito.mock(ZipcodeValidation.class);
		zipcodeValidator.initialize(zipcodeValidation);
		assertTrue(zipcodeValidator.isValid("06766200", context));
	}
	
	@Test
	public void testIsValidZipcodeValid2() {
		ZipcodeValidation zipcodeValidation = Mockito.mock(ZipcodeValidation.class);
		zipcodeValidator.initialize(zipcodeValidation);
		assertTrue(zipcodeValidator.isValid("06766-200", context));
	}
	
	@Test
	public void testIsValidZipcodeValid3() {
		ZipcodeValidation zipcodeValidation = Mockito.mock(ZipcodeValidation.class);
		zipcodeValidator.initialize(zipcodeValidation);
		assertTrue(zipcodeValidator.isValid("06.766-200", context));
	}
	
	@Test
	public void testIsValidZipcodeNoValid1() {
		ZipcodeValidation zipcodeValidation = Mockito.mock(ZipcodeValidation.class);
		zipcodeValidator.initialize(zipcodeValidation);
		assertFalse(zipcodeValidator.isValid("0666-200", context));
	}
	
	@Test
	public void testIsValidZipcodeNoValid2() {
		ZipcodeValidation zipcodeValidation = Mockito.mock(ZipcodeValidation.class);
		zipcodeValidator.initialize(zipcodeValidation);
		assertFalse(zipcodeValidator.isValid("06.766-2000", context));
	}
	
	@Test
	public void testIsValidZipcodeNoValid3() {
		ZipcodeValidation zipcodeValidation = Mockito.mock(ZipcodeValidation.class);
		zipcodeValidator.initialize(zipcodeValidation);
		assertFalse(zipcodeValidator.isValid("0676620", context));
	}
}
