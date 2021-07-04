package com.br.recycle.api.exception;

/**
 * Classe responsável por tratar a exceção com relação a requisição que tem
 * um entidade que não é processada pelo servidor.
 * @author Caio Henrique do Carmo Bastos
 * @since 4/07/2021
 */
public class UnprocessableEntityException  extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public UnprocessableEntityException(String message) {
        super(message);
    }
}