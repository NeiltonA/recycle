package com.br.recycle.api.mock;

import com.br.recycle.api.model.Address;

import java.util.List;

/**
 * Classe de mock para atender os cenários de endereço saida
 * para evitar repetição de código
 *
 * @author Caio Henrique do Carmo Bastos
 * @since 03/05/2021
 */
public class AddressMock {

    public static Address getMockAddress() {
        Address address = new Address();
        address.setStreet("Rua Doutor Teste");
        address.setNumber("123");
        address.setComplement("AP123");
        address.setZipCode("06766300");
        address.setNeighborhood("Jundiai");
        address.setState("SP");
        address.setCity("São Paulo");
        address.setUser(UserMock.getMockUserDto());

        return address;
    }

    public static List<Address> getMockCollectionAddress() {
        Address address1 = new Address();
        address1.setStreet("Rua Doutor Teste");
        address1.setNumber("123");
        address1.setComplement("AP123");
        address1.setZipCode("06766300");
        address1.setNeighborhood("Jundiai");
        address1.setState("SP");
        address1.setCity("São Paulo");
        address1.setUser(UserMock.getMockUserDto());

        Address address2 = new Address();
        address2.setStreet("Rua Carlos Teste");
        address2.setNumber("456");
        address2.setZipCode("05680200");
        address2.setNeighborhood("Pq Piramaba");
        address2.setState("RJ");
        address2.setCity("Rio de Janeiro");
        address2.setUser(UserMock.getMockUserDto());

        return List.of(address1, address2);
    }
}
