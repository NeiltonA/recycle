package com.br.recycle.api.validation.validator;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.validation.ConstraintValidatorContext;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

public class CelularValidatorTest {

	@Autowired
	private ConstraintValidatorContext context;
	
	private CelularValidator celularValidator;
	@BeforeEach
	public void setUp() {
		celularValidator = new CelularValidator();
		Mockito.mock(CelularValidation.class);
	}
	
	@Test
	public void testIsValidCellValidNull() {
		CelularValidation  celularValidation = Mockito.mock(CelularValidation.class);
		celularValidator.initialize(celularValidation);
		assertTrue(celularValidator.isValid(null, context));
	}
	
	@Test
	public void testIsValidCellValid1() {
		CelularValidation  celularValidation = Mockito.mock(CelularValidation.class);
		celularValidator.initialize(celularValidation);
		assertTrue(celularValidator.isValid("11983550000", context));
	}
	
	@Test
	public void testIsValidCellValid2() {
		CelularValidation  celularValidation = Mockito.mock(CelularValidation.class);
		celularValidator.initialize(celularValidation);
		assertTrue(celularValidator.isValid("(11)983550000", context));
	}

	@Test
	public void testIsValidCellValid3() {
		CelularValidation  celularValidation = Mockito.mock(CelularValidation.class);
		celularValidator.initialize(celularValidation);
		assertTrue(celularValidator.isValid("(11) 983550000", context));
	}

	@Test
	public void testIsValidCellValid4() {
		CelularValidation  celularValidation = Mockito.mock(CelularValidation.class);
		celularValidator.initialize(celularValidation);
		assertTrue(celularValidator.isValid("(11) 98355-0000", context));
	}

	@Test
	public void testIsValidCellValid5() {
		CelularValidation  celularValidation = Mockito.mock(CelularValidation.class);
		celularValidator.initialize(celularValidation);
		assertTrue(celularValidator.isValid("11 98355-0000", context));
	}


	@Test
	public void testIsValidCellValid6() {
		CelularValidation  celularValidation = Mockito.mock(CelularValidation.class);
		celularValidator.initialize(celularValidation);
		assertTrue(celularValidator.isValid("198355-0000", context));
	}


	@Test
	public void testIsValidCellNoValid2() {
		CelularValidation  celularValidation = Mockito.mock(CelularValidation.class);
		celularValidator.initialize(celularValidation);
		assertFalse(celularValidator.isValid("01 98355-0000", context));
	}


	@Test
	public void testIsValidCellNoValid3() {
		CelularValidation  celularValidation = Mockito.mock(CelularValidation.class);
		celularValidator.initialize(celularValidation);
		assertFalse(celularValidator.isValid("11 98355000", context));
	}
}
