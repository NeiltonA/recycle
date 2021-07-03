package com.br.recycle.api.assembler;

import com.br.recycle.api.model.RefreshToken;
import com.br.recycle.api.payload.JwtAuthenticationResponse;
import com.br.recycle.api.security.MainUser;

/**
 * Classe responsável por transformar os dados recebido da entidade
 * para os dados de saída da aplicação para a autenticação do JWT.
 * 
 * @author Caio Henrique do Carmo Bastos
 * @since 03/07/2021
 *
 */
public class AuthAssembler {

	public static JwtAuthenticationResponse toModelResponse(String jwt, MainUser userDetails, RefreshToken refreshToken) {
		JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();
		jwtAuthenticationResponse.setAccessToken(jwt);
		jwtAuthenticationResponse.setFlowIndicator(userDetails.getFlowIndicator());
		jwtAuthenticationResponse.setActive(userDetails.getActive());
		jwtAuthenticationResponse.setUserType(userDetails.getAuthorities().toString());
		jwtAuthenticationResponse.setRefreshToken(refreshToken.getToken());
		jwtAuthenticationResponse.setName(userDetails.getName());
		jwtAuthenticationResponse.setId(userDetails.getId());
		
		return jwtAuthenticationResponse;
	}
}
