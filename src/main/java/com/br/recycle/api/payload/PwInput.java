package com.br.recycle.api.payload;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PwInput {

	@NotNull
	@NotBlank
	private String password;
	
}
