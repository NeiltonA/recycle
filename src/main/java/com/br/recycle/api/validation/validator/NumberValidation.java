package com.br.recycle.api.validation.validator;

import static java.lang.annotation.ElementType.FIELD;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = NumberValidator.class)
@Target({ FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface NumberValidation {
	
	String message() default "O valor do número está inválido, deve ser informado apenas números.";
	
	Class<?>[] groups() default {};
	
	Class<? extends Payload>[] payload() default {};
}
