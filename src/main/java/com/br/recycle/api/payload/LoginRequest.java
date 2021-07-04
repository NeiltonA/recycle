package com.br.recycle.api.payload;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

/**
 * Classe responsável por armazer os dados de entrada do Login da requisição.
 */
@Getter
@Setter
public class LoginRequest {

	@NotBlank
	private String email;

	@NotBlank
	private String password;
}