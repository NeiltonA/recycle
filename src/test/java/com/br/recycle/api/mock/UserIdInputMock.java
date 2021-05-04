package com.br.recycle.api.mock;

import com.br.recycle.api.payload.UserIdInput;

/**
 * Classe de mock para atender os cenários de usuario entrada
 * para evitar repetição de código
 *
 * @author Caio Henrique do Carmo Bastos
 * @since 03/05/2021
 */
public class UserIdInputMock {

    public static UserIdInput getMockUser() {
        UserIdInput userIdInput = new UserIdInput();
        userIdInput.setId(1L);

        return userIdInput;
    }
}
