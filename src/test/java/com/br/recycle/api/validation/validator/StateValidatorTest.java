package com.br.recycle.api.validation.validator;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.validation.ConstraintValidatorContext;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

public class StateValidatorTest {

	@Autowired
	private ConstraintValidatorContext context;
	
	private StateValidator stateValidator;
	
	@BeforeEach
	public void setUp() {
		stateValidator = new StateValidator();
		Mockito.mock(StateValidation.class);
	}
	
	@Test
	public void testIsValidStateValidNull() {
		StateValidation stateValidation = Mockito.mock(StateValidation.class);
		stateValidator.initialize(stateValidation);
		assertTrue(stateValidator.isValid(null, context));
	}
	
	@Test
	public void testIsValidStateValid1() {
		StateValidation stateValidation = Mockito.mock(StateValidation.class);
		stateValidator.initialize(stateValidation);
		assertTrue(stateValidator.isValid("SP", context));
	}
	
	@Test
	public void testIsValidStateInvalid1() {
		StateValidation stateValidation = Mockito.mock(StateValidation.class);
		stateValidator.initialize(stateValidation);
		assertFalse(stateValidator.isValid("S", context));
	}
	
	@Test
	public void testIsValidStateInvalid2() {
		StateValidation stateValidation = Mockito.mock(StateValidation.class);
		stateValidator.initialize(stateValidation);
		assertFalse(stateValidator.isValid("SSP", context));
	}
	
	@Test
	public void testIsValidStateInvalid3() {
		StateValidation stateValidation = Mockito.mock(StateValidation.class);
		stateValidator.initialize(stateValidation);
		assertFalse(stateValidator.isValid("S2", context));
	}
}
