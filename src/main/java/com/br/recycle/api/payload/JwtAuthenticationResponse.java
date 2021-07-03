package com.br.recycle.api.payload;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Classe responsável por mapear os dados de resposta da
 * aplicação para a autenticação de JWT
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class JwtAuthenticationResponse {
    private String accessToken;
    private String flowIndicator;
    private Boolean active;
    private String userType;
    private String refreshToken;
    private String type = "Bearer";
    private Long id;
    private String name;
}

