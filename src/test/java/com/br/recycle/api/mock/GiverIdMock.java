package com.br.recycle.api.mock;

import com.br.recycle.api.model.Giver;

/**
 * Classe de mock para atender os cenários de id de doação resposta
 * para evitar repetição de código
 *
 * @author Caio Henrique do Carmo Bastos
 * @since 03/05/2021
 */
public class GiverIdMock {

    public static Giver getMockGiver() {
        Giver giver = new Giver();
        giver.setId(1L);
        giver.setCode("Teste");
        giver.setUser(UserMock.getMockUserDto());

        return giver;
    }
}
