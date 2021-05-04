package com.br.recycle.api.mock;

import com.br.recycle.api.model.Cooperative;

import java.util.List;

/**
 * Classe de mock para atender os cenários de cooperativa saida
 * para evitar repetição de código
 *
 * @author Caio Henrique do Carmo Bastos
 * @since 03/05/2021
 */
public class CooperativeMock {

    public static Cooperative getMockCooperative() {
        Cooperative cooperative = new Cooperative();
        cooperative.setCompanyName("Recycle do brasil LTDA");
        cooperative.setResponsibleName("Juca do brasil LTDA");
        cooperative.setCpfResponsible("29090890025");
        cooperative.setCnpj("52288720000106");
        cooperative.setUser(UserMock.getMockUserDto());

        return cooperative;
    }

    public static List<Cooperative> getMockCollectionCooperative() {
        Cooperative cooperative1 = new Cooperative();
        cooperative1.setCompanyName("Recycle do brasil LTDA");
        cooperative1.setResponsibleName("Juca do brasil LTDA");
        cooperative1.setCpfResponsible("29090890025");
        cooperative1.setCnpj("52288720000106");
        cooperative1.setUser(UserMock.getMockUserDto());

        Cooperative cooperative2 = new Cooperative();
        cooperative2.setCompanyName("Recycle do USA LTDA");
        cooperative2.setResponsibleName("Juca do USA LTDA");
        cooperative2.setCpfResponsible("45090890030");
        cooperative2.setCnpj("25288720000250");
        cooperative2.setUser(UserMock.getMockUserDto());

        return List.of(cooperative1, cooperative2);
    }
}
