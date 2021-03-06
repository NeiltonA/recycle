package com.br.recycle.api.payload;

import java.util.Set;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Classe responsável por carregar os dados de saída da aplicação 
 * com relação ao usuaŕio.
 */
@Getter
@Setter
@ToString
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class UserDtoOut {

	private Long id;
	private String name;
	private String email;
	private String cellPhone;
	private String individualRegistration;
	private String flowIndicator;
	private Boolean active;
	private Set<RoleDto> roles;
}