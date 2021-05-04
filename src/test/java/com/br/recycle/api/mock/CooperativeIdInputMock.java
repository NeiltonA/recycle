package com.br.recycle.api.mock;

import com.br.recycle.api.payload.CooperativeIdInput;

/**
 * Classe de mock para atender os cenários de id da cooperativa
 * para evitar repetição de código
 *
 * @author Caio Henrique do Carmo Bastos
 * @since 03/05/2021
 */
public class CooperativeIdInputMock {

    public static CooperativeIdInput getMockCooperativeIdInput() {
        CooperativeIdInput cooperativeIdInput = new CooperativeIdInput();
        cooperativeIdInput.setId(1L);

        return cooperativeIdInput;
    }
}
