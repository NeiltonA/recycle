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
public class RateInput {

	@ApiModelProperty(example = "9", required = true)
	@NotEmpty
	private Long note;

	@ApiModelProperty(example = "Excelente", required = false)
	private String comment;

	@ApiModelProperty(example = "1", required = false)
	private CooperativeIdInput cooperative;

	@ApiModelProperty(example = "1", required = false)
	private GiverIdInput giver;
}
