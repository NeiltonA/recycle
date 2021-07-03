package com.br.recycle.api.assembler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;

import com.br.recycle.api.model.RefreshToken;
import com.br.recycle.api.payload.JwtAuthenticationResponse;
import com.br.recycle.api.security.MainUser;

/**
 * Classe responsável por mapear os cenários de testes de transformação
 * dos dados da entidade de Atenticação do JWT para os dados de saída 
 * da aplicação.
 * @author Caio Henrique do Carmo Bastos
 * @since 03/07/2021
 *
 */
public class AuthAssemblerTest {

	private static final String JWT = "tokeTesteParaChamada";
	
	/**
	 * Método responśavel por validar o cenário de teste
	 * no qual realiza a conversao dos dados de saida do JWT.
	 */
	@Test
	public void testToModelResponseSuccess() {
		JwtAuthenticationResponse jwtAuthenticationResponse = 
				AuthAssembler.toModelResponse(JWT, getMockMainUser(), getMockRefreshToken());
		assertNotNull(jwtAuthenticationResponse);
		assertEquals(JWT, jwtAuthenticationResponse.getAccessToken());
		assertEquals("D", jwtAuthenticationResponse.getFlowIndicator());
		assertEquals(true, jwtAuthenticationResponse.getActive());
		assertEquals(Collections.EMPTY_LIST.toString(), jwtAuthenticationResponse.getUserType());
		assertEquals(JWT, jwtAuthenticationResponse.getRefreshToken());
		assertEquals("Teste Recycle", jwtAuthenticationResponse.getName());
		assertEquals(Long.valueOf(1), jwtAuthenticationResponse.getId());
		
	}

	/**
	 * Mock responsável por representr os dados de RefreshToken
	 * @return {@code RefreshToken}
	 */
	private RefreshToken getMockRefreshToken() {
		RefreshToken refreshToken = new RefreshToken();
		refreshToken.setToken(JWT);
		return refreshToken;
	}

	/**
	 * Mock responsável por representr os dados de MainUser
	 * @return {@code MainUser}
	 */
	private MainUser getMockMainUser() {
		List<GrantedAuthority> authorities = new ArrayList<>();
		MainUser mainUser = new MainUser(1L, "Teste Recycle", "teste@teste.com", true, "D", "senhaTeste", authorities);
		return mainUser;
	}
}
