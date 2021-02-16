package com.br.recycle.api.exception;

public class GiverNaoEncontradaException extends EntidadeNaoEncontradaException{

	private static final long serialVersionUID = 1L;

	public GiverNaoEncontradaException(String mensagem) {
		super(mensagem);
	}
	
	public GiverNaoEncontradaException(Long cozinhaId) {
		this(String.format("Não existe um cadastro de cozinha com código %d", cozinhaId));
	}
}
