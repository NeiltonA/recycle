package com.br.recycle.api.validation.validator;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.validation.ConstraintValidatorContext;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

public class PhoneValidatorTest {

	@Autowired
	private ConstraintValidatorContext context;
	
	private PhoneValidator celularValidator;
	@BeforeEach
	public void setUp() {
		celularValidator = new PhoneValidator();
		Mockito.mock(PhoneValidation.class);
	}
	
	@Test
	public void testIsValidCellValidNull() {
		PhoneValidation  celularValidation = Mockito.mock(PhoneValidation.class);
		celularValidator.initialize(celularValidation);
		assertTrue(celularValidator.isValid(null, context));
	}
	
	@Test
	public void testIsValidCellValid1() {
		PhoneValidation  celularValidation = Mockito.mock(PhoneValidation.class);
		celularValidator.initialize(celularValidation);
		assertTrue(celularValidator.isValid("11983550000", context));
	}
	
	@Test
	public void testIsValidCellValid2() {
		PhoneValidation  celularValidation = Mockito.mock(PhoneValidation.class);
		celularValidator.initialize(celularValidation);
		assertTrue(celularValidator.isValid("(11)983550000", context));
	}

	@Test
	public void testIsValidCellValid3() {
		PhoneValidation  celularValidation = Mockito.mock(PhoneValidation.class);
		celularValidator.initialize(celularValidation);
		assertTrue(celularValidator.isValid("(11) 983550000", context));
	}

	@Test
	public void testIsValidCellValid4() {
		PhoneValidation  celularValidation = Mockito.mock(PhoneValidation.class);
		celularValidator.initialize(celularValidation);
		assertTrue(celularValidator.isValid("(11) 98355-0000", context));
	}

	@Test
	public void testIsValidCellValid5() {
		PhoneValidation  celularValidation = Mockito.mock(PhoneValidation.class);
		celularValidator.initialize(celularValidation);
		assertTrue(celularValidator.isValid("11 98355-0000", context));
	}


	@Test
	public void testIsValidCellValid6() {
		PhoneValidation  celularValidation = Mockito.mock(PhoneValidation.class);
		celularValidator.initialize(celularValidation);
		assertTrue(celularValidator.isValid("198355-0000", context));
	}


	@Test
	public void testIsValidCellNoValid2() {
		PhoneValidation  celularValidation = Mockito.mock(PhoneValidation.class);
		celularValidator.initialize(celularValidation);
		assertFalse(celularValidator.isValid("01 98355-0000", context));
	}


	@Test
	public void testIsValidCellNoValid3() {
		PhoneValidation  celularValidation = Mockito.mock(PhoneValidation.class);
		celularValidator.initialize(celularValidation);
		assertFalse(celularValidator.isValid("11 98355000", context));
	}
}
