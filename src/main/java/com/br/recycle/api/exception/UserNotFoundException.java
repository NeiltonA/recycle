package com.br.recycle.api.exception;

public class UserNotFoundException extends EntidadeNaoEncontradaException {

    private static final long serialVersionUID = 1L;

    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException(Long kitchenId) {
        this(String.format("Não existe um cadastro de cozinha com código %d", kitchenId));
    }
}
