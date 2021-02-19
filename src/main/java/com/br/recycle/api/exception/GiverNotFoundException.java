package com.br.recycle.api.exception;

public class GiverNotFoundException extends EntityNotFoundException{

	private static final long serialVersionUID = 1L;

	public GiverNotFoundException(String message) {
		super(message);
	}
	
	public GiverNotFoundException(Long kitchenId) {
		this(String.format("Não existe um cadastro de doador com código %d", kitchenId));
	}
}
