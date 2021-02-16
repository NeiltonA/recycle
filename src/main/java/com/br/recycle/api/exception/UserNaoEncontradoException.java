package com.br.recycle.api.exception;

public class UserNaoEncontradoException extends EntidadeNaoEncontradaException{

	private static final long serialVersionUID = 1L;

	public UserNaoEncontradoException(String mensagem) {
		super(mensagem);
	}
	
	public UserNaoEncontradoException(Long cozinhaId) {
		this(String.format("Não existe um cadastro de cozinha com código %d", cozinhaId));
	}
}
