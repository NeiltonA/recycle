package com.br.recycle.api.exception;

public class DonationNaoEncontradaException extends EntidadeNaoEncontradaException{

	private static final long serialVersionUID = 1L;

	public DonationNaoEncontradaException(String mensagem) {
		super(mensagem);
	}
	
	public DonationNaoEncontradaException(Long cozinhaId) {
		this(String.format("Não existe um cadastro de cozinha com código %d", cozinhaId));
	}
}
