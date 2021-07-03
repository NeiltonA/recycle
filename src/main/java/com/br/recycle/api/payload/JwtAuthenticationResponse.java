package com.br.recycle.api.payload;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class JwtAuthenticationResponse {
    private String accessToken;
   // private Date expirationDateToken;
    private String flowIndicator;
    private Boolean active;
    private String userType;
    private String refreshToken;
    private String type = "Bearer";
    private Long id;
    private String name;

	public JwtAuthenticationResponse(String accessToken,String flowIndicator, Boolean active,
			String userType, String refreshToken, Long id, String name) {
		this.accessToken = accessToken;
		//this.expirationDateToken = expirationDateToken;
		this.flowIndicator = flowIndicator;
		this.active = active;
		this.userType = userType;
		this.refreshToken = refreshToken;
		//this.type = type;
		this.name = name;
		this.id = id;
	}
	
	
}

