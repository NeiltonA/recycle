package com.br.recycle.api.exception;

public class DonationNotFoundException extends EntityNotFoundException {

    private static final long serialVersionUID = 1L;

    public DonationNotFoundException(String message) {
        super(message);
    }

    public DonationNotFoundException(Long kitchenId) {
        this(String.format("Não existe um cadastro de doação com código %d", kitchenId));
    }
}
