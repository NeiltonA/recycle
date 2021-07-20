package com.br.recycle.api.payload;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.br.recycle.api.validation.validator.EmailValidation;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ContactInput{

	@NotBlank
	@NotNull
	private String name;
	
	@NotBlank
	@NotNull
	@EmailValidation
	private String email;
	
	@NotBlank
	@NotNull
	private String message;

}