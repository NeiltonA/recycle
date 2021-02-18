package com.br.recycle.api.payload;

import java.util.Date;

import lombok.Data;

@Data
public class JwtAuthenticationResponse {
    private String accessToken;
    private Date expirationDateToken;
    private String flowIndicator;
    private Boolean active;
    private String userType;
    /*
     * public JwtAuthenticationResponse(String accessToken, String flowIndicator) {
     * this.accessToken = accessToken; this.flowIndicator= flowIndicator; }
     */

}
