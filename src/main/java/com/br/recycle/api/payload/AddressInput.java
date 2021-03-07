package com.br.recycle.api.payload;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class AddressInput  {


	@ApiModelProperty(example = "Rua Juca", required = false)
	private String street;

	@ApiModelProperty(example = "188", required = false)
	private String number;

	@ApiModelProperty(example = "casa", required = false)
	private String complement;

	@ApiModelProperty(example = "07750000", required = false)
	private String zipCode;

	@ApiModelProperty(example = "JUNDIAI", required = false)
	private String neighborhood;

	@ApiModelProperty(example = "São paulo", required = false)
	private String state;

	@ApiModelProperty(example = "São paulo", required = false)
	private String city;

	@ApiModelProperty(example = "1", required = true)
	private UserIdInput user;

}