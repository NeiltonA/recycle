package com.br.recycle.api.payload;

import com.br.recycle.api.validation.validator.NumberValidation;
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
public class AddressPartialInput  {

	@ApiModelProperty(example = "188", required = true)
	@NumberValidation
	private String number;

	@ApiModelProperty(example = "casa", required = false)
	private String complement;
}