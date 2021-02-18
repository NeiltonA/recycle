package com.br.recycle.api.exception;

public class GiverNotFoundException extends EntidadeNaoEncontradaException{

	private static final long serialVersionUID = 1L;

	public GiverNotFoundException(String message) {
		super(message);
	}
	
	public GiverNotFoundException(Long kitchenId) {
		this(String.format("Não existe um cadastro de cozinha com código %d", kitchenId));
	}
}
