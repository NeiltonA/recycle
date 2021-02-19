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
public class AddressDto{

	private String street;
	private String number;
	private String complement;
	private String zipCode;
	private String neighborhood;
	private String state;
	private String city;

}