package com.br.recycle.api.validation.validator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = CelularValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CelularValidation {

	String message() default "O número de celular está inválido. E deve ser informado um DDD válido.";
	
	Class<?>[] groups() default {};
	
	Class<? extends Payload>[] payload() default {};
}
