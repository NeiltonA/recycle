package com.br.recycle.api.payload;

import org.hibernate.validator.constraints.br.CPF;

import com.br.recycle.api.validation.validator.CelularValidation;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class UserDtoIn{

	@CPF(message = "CPF inválido")
	private String individualRegistration;
	
	private String name;
	private String email;
	
	@CelularValidation
	private String cellPhone;	
	private String flowIndicator;
	private Boolean active = Boolean.TRUE;
}