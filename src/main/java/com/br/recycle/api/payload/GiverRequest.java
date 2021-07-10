package com.br.recycle.api.payload;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

/**
 * Classe responsável por mapear os dados de entrada 
 * do Doadoar para cadastro.
 *
 */
@Setter
@Getter
public class GiverRequest {

	@NotNull
	@Valid
	private UserIdInput user;
	
}