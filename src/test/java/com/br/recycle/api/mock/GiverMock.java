package com.br.recycle.api.mock;

import com.br.recycle.api.model.Giver;

import java.util.Collections;
import java.util.List;

/**
 * Classe de mock para atender os cenários de Doador saida
 * para evitar repetição de código
 *
 * @author Caio Henrique do Carmo Bastos
 * @since 04/05/2021
 */
public class GiverMock {

    public static Giver getMockGiver() {
        Giver giver = new Giver();
        giver.setId(1L);
        giver.setUser(UserMock.getMockUserDto());

        return giver;
    }

    public static List<Giver> getMockCollectionMock() {

        Giver giver = new Giver();
        giver.setId(1L);
        giver.setUser(UserMock.getMockUserDto());

        return Collections.singletonList(giver);
    }
}
