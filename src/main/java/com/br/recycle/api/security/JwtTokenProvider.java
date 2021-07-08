package com.br.recycle.api.security;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@SuppressWarnings("deprecation")
@Component
public class JwtTokenProvider {

	private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

	@Value("${recycle.jwtSecret}")
	private String jwtSecret;

	@Value("${recycle.jwtExpirationInMs}")
	private int jwtExpirationInMs;
	
	@Value("${recycle.jwtRefreshExpirationInMs}")
	private int refreshTokenDurationMs;

	public String generateJwtToken(MainUser userPrincipal) {
		boolean refresh = false;
		return generateTokenFromUsername(userPrincipal.getEmail(), userPrincipal.getId(), refresh);
	}

	public String generateTokenFromUsername(String username, Long id, boolean refresh) {
		     long expirationRefreshEhToken = 0;
			if (refresh) {
				expirationRefreshEhToken = this.refreshTokenDurationMs;
			}else {
				expirationRefreshEhToken = this.jwtExpirationInMs;	
			}
		
		
		
		return Jwts.builder().setSubject(username).setIssuedAt(new Date())
				.setId(id.toString()) //jti: Json Token Identifier ou ID único para o token;
				.setAudience("Recycle") //Audience, quem vai consumir o seu token;
				.setExpiration(new Date((new Date()).getTime() + expirationRefreshEhToken)) //exp: Expiration, tempo para utilização do token;
				.signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
	}

	public String getUserNameFromJwtToken(String token) {
		return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
	}

	public boolean validateJwtToken(String authToken) {
		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
			return true;
		} catch (SignatureException e) {
			logger.error("Invalid JWT signature: {}", e.getMessage());
		} catch (MalformedJwtException e) {
			logger.error("Invalid JWT token: {}", e.getMessage());
		} catch (ExpiredJwtException e) {
			logger.error("JWT token is expired: {}", e.getMessage());
		} catch (UnsupportedJwtException e) {
			logger.error("JWT token is unsupported: {}", e.getMessage());
		} catch (IllegalArgumentException e) {
			logger.error("JWT claims string is empty: {}", e.getMessage());
		}

		return false;
	}
}