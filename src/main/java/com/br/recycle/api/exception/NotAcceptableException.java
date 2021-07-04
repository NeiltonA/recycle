package com.br.recycle.api.exception;

/**
 * Classe responsável por tratar a exceção com relação a requisição que não é 
 * aceita pelo servidor.
 * @author Caio Henrique do Carmo Bastos
 * @since 4/07/2021
 */
public class NotAcceptableException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public NotAcceptableException(String message) {
        super(message);
    }
}