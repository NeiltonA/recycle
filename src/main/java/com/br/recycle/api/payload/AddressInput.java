package com.br.recycle.api.payload;

import javax.validation.constraints.NotEmpty;

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

	@ApiModelProperty(example = "Rua Juca", required = true)
	@NotEmpty
	private String street;

	@ApiModelProperty(example = "188", required = true)
	@NotEmpty
	private String number;

	@ApiModelProperty(example = "casa", required = false)
	private String complement;

	@ApiModelProperty(example = "07750000", required = true)
	@NotEmpty
	private String zipCode;

	@ApiModelProperty(example = "JUNDIAI", required = true)
	@NotEmpty
	private String neighborhood;

	@ApiModelProperty(example = "São paulo", required = true)
	@NotEmpty
	private String state;

	@ApiModelProperty(example = "São paulo", required = true)
	@NotEmpty
	private String city;

	@ApiModelProperty(example = "1", required = true)
	private UserIdInput user;

}