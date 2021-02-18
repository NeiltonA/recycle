package com.br.recycle.api.exception;

public class RatingNotFoundException extends EntidadeNaoEncontradaException{

	private static final long serialVersionUID = 1L;

	public RatingNotFoundException(String message) {
		super(message);
	}
	
	public RatingNotFoundException(Long kitchenId) {
		this(String.format("Não existe um cadastro de cozinha com código %d", kitchenId));
	}
}
