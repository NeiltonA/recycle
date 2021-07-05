package com.br.recycle.api.exception;

/**
 * Classe para mapear a exeção de quando não existe o usuário
 * na base de dados.
 * @author Caio Henrique do Carmo Bastos
 * @since 04/07/2021
 */
public class UserNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public UserNotFoundException() {}
    
    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException(Long kitchenId) {
        this(String.format("Não existe um cadastro de usuário com código %d", kitchenId));
    }
}
