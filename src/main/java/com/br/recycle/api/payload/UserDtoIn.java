package com.br.recycle.api.payload;

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

	
	private String name;
	private String email;
	private String cellPhone;
	private String individualRegistration;
	private String flowIndicator;
	private Boolean active = Boolean.TRUE;
}