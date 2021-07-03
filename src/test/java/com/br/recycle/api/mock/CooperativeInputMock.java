package com.br.recycle.api.mock;

import com.br.recycle.api.payload.CooperativeInput;

/**
 * Classe de mock para atender os cenários de cooperativa entrada
 * para evitar repetição de código
 *
 * @author Caio Henrique do Carmo Bastos
 * @since 03/05/2021
 */
public class CooperativeInputMock {

    public static CooperativeInput getMockCooperativeInput() {
        CooperativeInput cooperativeInput = new CooperativeInput();
        cooperativeInput.setCompanyName("Recycle do brasil LTDA");
        cooperativeInput.setFantasyName("Juca do brasil LTDA");
        cooperativeInput.setCnpj("52288720000106");
        cooperativeInput.setUser(UserIdInputMock.getMockUser());

        return cooperativeInput;
    }
}
