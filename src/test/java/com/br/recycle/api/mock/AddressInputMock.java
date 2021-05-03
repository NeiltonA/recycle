package com.br.recycle.api.mock;

import com.br.recycle.api.payload.AddressInput;

/**
 * Classe de mock para atender os cenários de endereço entrada
 * para evitar repetição de código
 *
 * @author Caio Henrique do Carmo Bastos
 * @since 03/05/2021
 */
public class AddressInputMock {

    public static AddressInput getMockAddressInput() {
        AddressInput addressInput = new AddressInput();
        addressInput.setStreet("Rua Doutor Teste");
        addressInput.setNumber("123");
        addressInput.setComplement("AP123");
        addressInput.setZipCode("06766300");
        addressInput.setNeighborhood("Jundiai");
        addressInput.setState("SP");
        addressInput.setCity("São Paulo");
        addressInput.setUser(UserIdInputMock.getMockUser());

        return addressInput;
    }
}
