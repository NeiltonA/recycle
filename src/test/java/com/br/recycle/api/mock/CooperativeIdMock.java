package com.br.recycle.api.mock;

import com.br.recycle.api.model.Cooperative;

/**
 * Classe de mock para atender os cenários de id da cooperativa de resposta
 * para evitar repetição de código
 *
 * @author Caio Henrique do Carmo Bastos
 * @since 03/05/2021
 */
public class CooperativeIdMock {

    public static Cooperative getMockCooperative() {
        Cooperative cooperative = new Cooperative();
        cooperative.setId(1L);
        cooperative.setCompanyName("Companhia teste Brasil");
        cooperative.setResponsibleName("Dono Teste");
        cooperative.setCpfResponsible("12345678900");
        cooperative.setCnpj("12345678900098");
        cooperative.setUser(UserMock.getMockUserDto());

        return cooperative;
    }
}
