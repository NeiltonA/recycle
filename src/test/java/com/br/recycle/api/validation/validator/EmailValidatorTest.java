package com.br.recycle.api.validation.validator;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.validation.ConstraintValidatorContext;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

public class EmailValidatorTest {

	@Autowired
	private ConstraintValidatorContext context;
	
	private EmailValidator emailValidator;
	@BeforeEach
	public void setUp() {
		emailValidator = new EmailValidator();
		Mockito.mock(EmailValidation.class);
	}
	
	@Test
	public void testIsValidEmailValidNull() {
		EmailValidation emailValidation = Mockito.mock(EmailValidation.class);
		emailValidator.initialize(emailValidation);
		assertTrue(emailValidator.isValid(null, context));
	}
	
	@Test
	public void testIsValidEmailValid1() {
		EmailValidation emailValidation = Mockito.mock(EmailValidation.class);
		emailValidator.initialize(emailValidation);
		assertTrue(emailValidator.isValid("caio.cbastos@hotmail.com", context));
	}
	
	@Test
	public void testIsValidEmailValid2() {
		EmailValidation emailValidation = Mockito.mock(EmailValidation.class);
		emailValidator.initialize(emailValidation);
		assertTrue(emailValidator.isValid("caiobastos@hotmail.com", context));
	}
	
	@Test
	public void testIsValidEmailValid3() {
		EmailValidation emailValidation = Mockito.mock(EmailValidation.class);
		emailValidator.initialize(emailValidation);
		assertTrue(emailValidator.isValid("caiobastos@hotmail.org", context));
	}
	
	@Test
	public void testIsValidEmailValid4() {
		EmailValidation emailValidation = Mockito.mock(EmailValidation.class);
		emailValidator.initialize(emailValidation);
		assertTrue(emailValidator.isValid("caiobastos@hotmail.com.br", context));
	}
	
	@Test
	public void testIsValidEmailNoValid1() {
		EmailValidation emailValidation = Mockito.mock(EmailValidation.class);
		emailValidator.initialize(emailValidation);
		assertFalse(emailValidator.isValid("caiobastoshotmail.com.br", context));
	}
	
	@Test
	public void testIsValidEmailNoValid2() {
		EmailValidation emailValidation = Mockito.mock(EmailValidation.class);
		emailValidator.initialize(emailValidation);
		assertFalse(emailValidator.isValid("caiobastos@hotmail", context));
	}

	@Test
	public void testIsValidEmailNoValid3() {
		EmailValidation emailValidation = Mockito.mock(EmailValidation.class);
		emailValidator.initialize(emailValidation);
		assertFalse(emailValidator.isValid("caiobast&s@hotmail.com", context));
	}
}
