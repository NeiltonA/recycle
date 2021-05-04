package com.br.recycle.api.mock;

import com.br.recycle.api.payload.GiverIdInput;

/**
 * Classe de mock para atender os cenários de id da doação entrada
 * para evitar repetição de código
 *
 * @author Caio Henrique do Carmo Bastos
 * @since 03/05/2021
 */
public class GiverIdInputMock {

    public static GiverIdInput getMockGiverIdInput() {
        GiverIdInput giverIdInput = new GiverIdInput();
        giverIdInput.setId(1L);

        return giverIdInput;
    }
}
