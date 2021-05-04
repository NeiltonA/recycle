package com.br.recycle.api.mock;

import com.br.recycle.api.model.Flow;
import com.br.recycle.api.model.User;
import com.br.recycle.api.payload.RoleName;

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
        user.setName("Caio Henrique Bastos");
        user.setEmail("caioTeste@teste.com");
        user.setRole(RoleName.ROLE_USER);
        user.setPassword("Teste123");
        user.setConfirmPassword("Teste123");
        user.setCellPhone("11983512000");
        user.setIndividualRegistration("Teste");
        user.setFlowIndicator(Flow.D);
        user.setToken("1234567890asdfghjk");

        return user;
    }
}
