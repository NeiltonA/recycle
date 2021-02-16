package com.br.recycle.api.exception;

public class CooperativeNaoEncontradaException extends EntidadeNaoEncontradaException{

	private static final long serialVersionUID = 1L;

	public CooperativeNaoEncontradaException(String mensagem) {
		super(mensagem);
	}
	
	public CooperativeNaoEncontradaException(Long cozinhaId) {
		this(String.format("Não existe um cadastro de cozinha com código %d", cozinhaId));
	}
}
