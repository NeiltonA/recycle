package com.br.recycle.api.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.br.recycle.api.assembler.UserDtoAssembler;
import com.br.recycle.api.mock.UserMock;
import com.br.recycle.api.payload.ApiResponse;
import com.br.recycle.api.payload.PasswordInput;
import com.br.recycle.api.payload.UserDtoOut;
import com.br.recycle.api.service.PwService;
import com.br.recycle.api.service.UserService;
import com.br.recycle.api.service.email.SendEmail;

/**
 * Método responsável por validar os testes da classe de 
 * controller de usuario.
 *
 */
@ExtendWith(SpringExtension.class)
public class UserControllerTest2xx {
	
	@Mock
	private UserService userService;
	
	@Mock
	private PwService pwService;
	
	@Mock
	private SendEmail sendEmail;
	
	@Mock
	private UserDtoAssembler userDtoAssembler;

	@InjectMocks
	private UserController userController = new UserController(userService, pwService, sendEmail, userDtoAssembler);
	
	@Test
	public void testGetAllSuccess() {
		given(userService.findAll()).willReturn(UserMock.getMockCollectionModel());
		given(userDtoAssembler.toCollectionModel(UserMock.getMockCollectionModel())).willReturn(getMockUsers());
		
		List<UserDtoOut> usersDto = userController.findAll();
		assertEquals(Long.valueOf(1), usersDto.get(0).getId());
	}
	
	@Test
	public void testFindByIdSuccess() {
		given(userService.findById(1L)).willReturn(UserMock.getMockUserDto());
		given(userDtoAssembler.toModel(UserMock.getMockUserDto())).willReturn(getMockUser());
		
		UserDtoOut usersDto = userController.findById(1L);
		assertEquals(Long.valueOf(1), usersDto.getId());
	}
	
	@Test
	public void testUpdatePasswordSuccess() {
		given(userService.changePassword(1L, getMockPassword().getCurrentPassword(), getMockPassword().getNewPassword()))
				.willReturn(UserMock.getMockUserDto());
		
		ResponseEntity<ApiResponse> apiResponse = userController.updatePassword(1L, getMockPassword());
		assertEquals(200, apiResponse.getStatusCode().value());
	}

	@Test
	public void testDeleteSuccess() {
		userController.delete(1L);
		verify(userService, times(1)).deleteById(1L);
		
		assertEquals(204, HttpStatus.NO_CONTENT.value());
	}
	
	private PasswordInput getMockPassword() {
		PasswordInput passwordInput = new PasswordInput();
		passwordInput.setCurrentPassword("currentPassword");
		passwordInput.setNewPassword("newPassword");
		
		return passwordInput;
	}
	private UserDtoOut getMockUser() {
		UserDtoOut userDtoOut = new UserDtoOut();
        userDtoOut.setId(1L);
        userDtoOut.setName("Teste Silva");
        userDtoOut.setEmail("teste@recycle.com");
        userDtoOut.setCellPhone("11 999999999");
        userDtoOut.setIndividualRegistration("10364680032");
		
        return userDtoOut;
	}

	private List<UserDtoOut> getMockUsers() {
		UserDtoOut userDtoOut = new UserDtoOut();
        userDtoOut.setId(1L);
        userDtoOut.setName("Teste Silva");
        userDtoOut.setEmail("teste@recycle.com");
        userDtoOut.setCellPhone("11 999999999");
        userDtoOut.setIndividualRegistration("10364680032");
		
        return List.of(userDtoOut);
	}
}
