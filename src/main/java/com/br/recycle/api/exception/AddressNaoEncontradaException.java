package com.br.recycle.api.exception;

public class AddressNaoEncontradaException extends EntidadeNaoEncontradaException{

	private static final long serialVersionUID = 1L;

	public AddressNaoEncontradaException(String mensagem) {
		super(mensagem);
	}
	
	public AddressNaoEncontradaException(Long cozinhaId) {
		this(String.format("Não existe um cadastro de cozinha com código %d", cozinhaId));
	}
}
