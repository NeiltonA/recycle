package com.br.recycle.api.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.br.recycle.api.mock.UserMock;
import com.br.recycle.api.repository.UserRepository;

/**
 * Classe responsável por validar os cenários de testes da classe
 * de serviço do detalhes do usuário.
 * 
 * @author Caio Henrique do Carmo Bastos
 * @since 11/07/2021
 *
 */
@ExtendWith(MockitoExtension.class)
public class UserDetailsServiceImplTest {

	@Mock
	private UserRepository userRepository;

	@InjectMocks
	private UserDetailsServiceImpl detailsServiceImpl;
	
	/**
	 * Método responsável por buscar o usuário pelo ID informado
	 * e seguir com a autenticação.
	 */
	@Test
	public void testLoadUserByUsernameSuccess() {
		given(userRepository.findById(1L)).willReturn(Optional.of(UserMock.getMockUserDto()));
		
		UserDetails userDetails = detailsServiceImpl.loadUserByUsername("1");
		assertNotNull(userDetails);
	}
	
	/**
	 * Método responsável por buscar o usuário pelo ID informado
	 * e não encontrar
	 */
	@Test
	public void testLoadUserByUsernameUsernameNotFound() {
		given(userRepository.findById(1L)).willThrow(UsernameNotFoundException.class);
		
		assertThrows(UsernameNotFoundException.class, () -> detailsServiceImpl.loadUserByUsername("1"));
	}
}
