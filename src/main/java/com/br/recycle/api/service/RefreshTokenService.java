package com.br.recycle.api.service;

import java.time.Instant;
import java.util.Date;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.br.recycle.api.exception.TokenRefreshException;
import com.br.recycle.api.exception.UserNotFoundException;
import com.br.recycle.api.model.RefreshToken;
import com.br.recycle.api.repository.RefreshTokenRepository;
import com.br.recycle.api.repository.UserRepository;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class RefreshTokenService {
	

  @Value("${recycle.jwtRefreshExpirationInMs}")
  private Long refreshTokenDurationMs;

  @Autowired
  private RefreshTokenRepository refreshTokenRepository;

  @Value("${recycle.jwtSecret}")
  private String jwtSecret;
  
  @Autowired
  private UserRepository userRepository;

  public Optional<RefreshToken> findByToken(String token) {
    return refreshTokenRepository.findByToken(token);
  }

  public RefreshToken createRefreshToken(Long userId) {
    RefreshToken refreshToken = new RefreshToken();

    refreshToken.setUser(userRepository.findById(userId).get());
    refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
    
    Date now = new Date();
    Date expiryDate = new Date(now.getTime());
	
	@SuppressWarnings("deprecation")
	String refreshtoken = (Jwts.builder().setSubject(Long.toString(userId)).setIssuedAt(new Date())
			.setExpiration(expiryDate).signWith(SignatureAlgorithm.HS512, jwtSecret).compact());
	
    refreshToken.setToken(refreshtoken);

    refreshToken = refreshTokenRepository.save(refreshToken);
    return refreshToken;
  }

  public RefreshToken verifyExpiration(RefreshToken token) {
    if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
      refreshTokenRepository.delete(token);
      throw new TokenRefreshException("O token de atualização expirou. Faça uma nova solicitação de login!");
    }

    return token;
  }

  @Transactional
  public int deleteByUserId(Long userId) {
    return refreshTokenRepository.deleteByUser(userRepository.findById(userId)
    		.orElseThrow(() -> new UserNotFoundException(userId)));
  }
}
