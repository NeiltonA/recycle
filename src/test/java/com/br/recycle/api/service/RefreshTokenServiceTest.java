package com.br.recycle.api.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

import java.time.Instant;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import com.br.recycle.api.exception.UserNotFoundException;
import com.br.recycle.api.mock.UserMock;
import com.br.recycle.api.model.RefreshToken;
import com.br.recycle.api.repository.RefreshTokenRepository;
import com.br.recycle.api.repository.UserRepository;

/**
 * Classe responsável por mapear os cenários de testes da classe de serviço do Refreshtoken.
 * 
 * @author Caio Henrique do Carmo Bastos
 * @since 12/07/2021
 *
 */
@ExtendWith(MockitoExtension.class)
public class RefreshTokenServiceTest {
	
	@Mock
	private RefreshTokenRepository refreshTokenRepository;
	
	@Mock
	private UserRepository userRepository;
	
	@InjectMocks 
	private RefreshTokenService refreshTokenService;
	
	@BeforeEach
	public void setUp() {
		ReflectionTestUtils.setField(refreshTokenService, "refreshTokenDurationMs", 1234567890L);
		ReflectionTestUtils.setField(refreshTokenService, "jwtSecret", "12345");
	}
	
	/**
	 * Método responsável por busca o token.
	 */
	@Test
	public void testFindByTokenSuccess() {
		given(refreshTokenRepository.findByToken("1234556788")).willReturn(Optional.of(getMockRefreshToken()));
		
		Optional<RefreshToken> refreshToken = refreshTokenService.findByToken("1234556788");
		assertNotNull(refreshToken);
	}
	
	/**
	 * Método responsável por busca o token.
	 */
	@Test
	public void testFindByTokenEntityNotFound() {
		given(refreshTokenRepository.findByToken("1234556788")).willThrow(EntityNotFoundException.class);
		
		assertThrows(EntityNotFoundException.class, () -> refreshTokenService.findByToken("1234556788"));
	}
	
	/**
	 * Método responsável por busca o token.
	 */
	@Test
	public void testFindByTokenEntityNotFoundEmpty() {
		given(refreshTokenRepository.findByToken("1234556788")).willReturn(Optional.empty());
		
		assertThrows(EntityNotFoundException.class, () -> refreshTokenService.findByToken("1234556788"));
	}
	
	/**
	 * Método responsável por deletar por ID
	 */
	@Test
	public void testDeleteByUserIdSuccess() {
		given(refreshTokenRepository.deleteByUser(UserMock.getMockUserDto())).willReturn(1);
		given(userRepository.findById(1L)).willReturn(Optional.of(UserMock.getMockUserDto()));
		
		int refreshToken = refreshTokenService.deleteByUserId(1L);
		assertNotNull(refreshToken);
	}
	
	/**
	 * Método responsável por deletar por ID, mas o usuário não é encontrado
	 */
	@Test
	public void testDeleteByUserIdUserNotFound() {
		given(userRepository.findById(1L)).willThrow(UserNotFoundException.class);
		
		assertThrows(UserNotFoundException.class, () -> refreshTokenService.deleteByUserId(1L));
	}

	private RefreshToken getMockRefreshToken() {
		RefreshToken refreshToken = new RefreshToken();
		refreshToken.setId(1L);
		refreshToken.setUser(UserMock.getMockUserDto());
		refreshToken.setToken("1234556788");
		refreshToken.setExpiryDate(Instant.now());
		
		return refreshToken;
	}
}
