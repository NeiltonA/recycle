package com.br.recycle.api.exception;

public class RateNaoEncontradaException extends EntidadeNaoEncontradaException{

	private static final long serialVersionUID = 1L;

	public RateNaoEncontradaException(String mensagem) {
		super(mensagem);
	}
	
	public RateNaoEncontradaException(Long cozinhaId) {
		this(String.format("Não existe um cadastro de cozinha com código %d", cozinhaId));
	}
}
