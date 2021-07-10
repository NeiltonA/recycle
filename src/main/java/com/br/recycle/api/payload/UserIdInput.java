package com.br.recycle.api.payload;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

/**
 * Classe responsável por mapear os dados de entrada 
 * do Doadoar com o ID para cadastro.
 *
 */
@Setter
@Getter
public class UserIdInput {

	@NotNull
	private Long id;
	
}
