package com.br.recycle.api.payload;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.br.recycle.api.validation.validator.EmailValidation;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PwEmailInput {

	@NotNull
	@NotBlank
	@EmailValidation
	private String email;
	
}
