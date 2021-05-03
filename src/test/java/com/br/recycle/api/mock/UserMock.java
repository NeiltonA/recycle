package com.br.recycle.api.mock;

import com.br.recycle.api.model.User;

/**
 * Classe de mock para atender os cenários de usuario saida
 * para evitar repetição de código
 *
 * @author Caio Henrique do Carmo Bastos
 * @since 03/05/2021
 */
public class UserMock {

    public static User getMockUserDto() {
        User user = new User();
        user.setId(1L);

        return user;
    }
}
