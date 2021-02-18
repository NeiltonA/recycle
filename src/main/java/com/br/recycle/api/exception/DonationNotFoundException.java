package com.br.recycle.api.exception;

public class DonationNotFoundException extends EntidadeNaoEncontradaException {

    private static final long serialVersionUID = 1L;

    public DonationNotFoundException(String message) {
        super(message);
    }

    public DonationNotFoundException(Long kitchenId) {
        this(String.format("Não existe um cadastro de cozinha com código %d", kitchenId));
    }
}
