package com.br.recycle.api.payload;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class LogOutRequest {

	@NotNull
	private Long userId;
}
