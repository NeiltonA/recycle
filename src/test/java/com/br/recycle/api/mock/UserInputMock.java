package com.br.recycle.api.mock;

import com.br.recycle.api.model.Flow;
import com.br.recycle.api.payload.UserInput;

/**
 * Classe de mock para atender os cenários de Usuario entrada
 * para evitar repetição de código
 *
 * @author Caio Henrique do Carmo Bastos
 * @since 04/05/2021
 */
public class UserInputMock {

    public static UserInput getMockUserInput() {
        UserInput userInput = new UserInput();
        userInput.setName("Teste Silva");
        userInput.setEmail("teste@recycle.com");
        userInput.setCellPhone("11 99999999");
        userInput.setIndividualRegistration("10364680032");
        userInput.setPassword("admin123");
        userInput.setConfirmPassword("admin123");
        userInput.setFlowIndicator(Flow.D);
        userInput.setActive(Boolean.TRUE);

        return userInput;
    }
}
