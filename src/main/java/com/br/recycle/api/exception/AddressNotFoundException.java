package com.br.recycle.api.exception;

public class AddressNotFoundException extends EntityNotFoundException {

    private static final long serialVersionUID = 1L;

    public AddressNotFoundException(String message) {
        super(message);
    }

    public AddressNotFoundException(Long kitchenId) {
        this(String.format("Não existe um cadastro de endereço com código %d", kitchenId));
    }
}
