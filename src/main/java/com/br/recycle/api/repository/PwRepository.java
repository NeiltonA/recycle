package com.br.recycle.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.br.recycle.api.model.User;

@Repository
public interface PwRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
	User findByToken(String token);
}
