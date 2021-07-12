package com.br.recycle.api.mock;

import java.time.LocalDateTime;
import java.util.List;

import com.br.recycle.api.model.Flow;
import com.br.recycle.api.model.User;
import com.br.recycle.api.payload.RoleName;
import com.br.recycle.api.payload.UserDtoIn;
import com.br.recycle.api.payload.UserInput;

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
        user.setName("Teste Silva");
        user.setEmail("teste@recycle.com");
        user.setRole(RoleName.ROLE_USER);
        user.setCellPhone("11 999999999");
        user.setIndividualRegistration("10364680032");
        user.setPassword("admin123");
        user.setConfirmPassword("admin123");
        user.setFlowIndicator(Flow.D);
        user.setTokenCreationDate(LocalDateTime.of(2021, 07, 10, 07, 41));
		
        return user;
    }

	public static UserInput getDomainObject() {
		UserInput userInput = new UserInput();
		userInput.setName("Teste Silva");
		userInput.setEmail("teste@recycle.com");
		userInput.setCellPhone("11 999999999");
		userInput.setIndividualRegistration("10364680032");
		userInput.setPassword("admin123");
		userInput.setConfirmPassword("admin123");
		userInput.setFlowIndicator(Flow.D);
		
		return userInput;
	}

	public static List<User> getMockCollectionModel() {
        User user = new User();
        user.setId(1L);
        user.setName("Teste Silva");
        user.setEmail("teste@recycle.com");
        user.setRole(RoleName.ROLE_USER);
        user.setCellPhone("11 999999999");
        user.setIndividualRegistration("10364680032");
        user.setPassword("admin123");
        user.setConfirmPassword("admin123");
        user.setFlowIndicator(Flow.D);
		
        return List.of(user);
	}
	
	public static UserDtoIn getMockDtoInObject() {
		UserDtoIn userDtoIn = new UserDtoIn();
		userDtoIn.setName("Teste Silva");
		userDtoIn.setEmail("teste@recycle.com");
		userDtoIn.setCellPhone("11 999999999");
		userDtoIn.setIndividualRegistration("10364680032");
		userDtoIn.setFlowIndicator("D");
		
		return userDtoIn;
	}
}
