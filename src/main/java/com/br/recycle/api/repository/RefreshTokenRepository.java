package com.br.recycle.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import com.br.recycle.api.model.RefreshToken;
import com.br.recycle.api.model.User;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
	 
	Optional<RefreshToken> findByToken(String token);

	@Modifying
	int deleteByUser(User user);
}
