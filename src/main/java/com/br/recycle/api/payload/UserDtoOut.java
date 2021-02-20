package com.br.recycle.api.payload;

import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class UserDtoOut{

	
	private Long id;
	private String name;
	private String email;
	private String cellPhone;
	private String cpfCnpj;
	private String flowIndicator;
	 private Boolean active;
	private Set<RoleDto> roles;
	private List<AddressDto> Address;
}