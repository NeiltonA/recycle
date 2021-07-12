package com.br.recycle.api.payload;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AddressIdInput {

	@NotNull
	private Long id;
	
}
