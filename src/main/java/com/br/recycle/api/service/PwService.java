package com.br.recycle.api.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.br.recycle.api.exception.BusinessException;
import com.br.recycle.api.model.User;
import com.br.recycle.api.payload.RoleName;
import com.br.recycle.api.repository.PwRepository;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class PwService {

	private static final long EXPIRE_TOKEN_AFTER_MINUTES = 30;

	@PersistenceContext
	private EntityManager manager;

	@Value("${recycle.jwtSecret}")
	private String jwtSecret;
	
	@Value("${recycle.jwtExpirationInMs}")
    private int jwtExpirationInMs;

	@Autowired
	private PwRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public String forgotPassword(String email) {

		Optional<User> userOptional = Optional.ofNullable(userRepository.findByEmail(email));

		if (!userOptional.isPresent()) {
			throw new BusinessException("Invalid email id.");
		}

		User user = userOptional.get();

		Object nameGroup = findByGroup(user.getId());
		user.setRole((RoleName.valueOf(nameGroup.toString())));

		 Date now = new Date();
	     Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);
		
		@SuppressWarnings("deprecation")
		String token = (Jwts.builder().setSubject(Long.toString(user.getId())).setIssuedAt(new Date())
				.setExpiration(expiryDate).signWith(SignatureAlgorithm.HS512, jwtSecret).compact());

		user.setToken(token);
		user.setTokenCreationDate(LocalDateTime.now());

		user = userRepository.save(user);

		return user.getToken();
	}

	public String resetPassword(String token, String password) {

		Optional<User> userOptional = Optional.ofNullable(userRepository.findByToken(token));

		if (!userOptional.isPresent()) {
			throw new BusinessException("Invalid token.");
		}

		LocalDateTime tokenCreationDate = userOptional.get().getTokenCreationDate();

		if (isTokenExpired(tokenCreationDate)) {
			throw new BusinessException("Token expired.");

		}

		User user = userOptional.get();
		user.setPassword(passwordEncoder.encode(password));

		Object nameGroup = findByGroup(user.getId());
		user.setRole((RoleName.valueOf(nameGroup.toString())));
		user.setToken(null);
		user.setTokenCreationDate(null);
		userRepository.save(user);

		return password;
	}

	private boolean isTokenExpired(final LocalDateTime tokenCreationDate) {

		LocalDateTime now = LocalDateTime.now();
		Duration diff = Duration.between(tokenCreationDate, now);

		return diff.toMinutes() >= EXPIRE_TOKEN_AFTER_MINUTES;
	}

	public Object findByGroup(Long userId) {
		Object group = manager.createNativeQuery(
				"select s.name from users u inner join user_roles r on u.id_user= r.user_id  inner join roles s on s.id = r.role_id and u.id_user ='"
						+ userId + "'")
				.getSingleResult();
		return group;
	}

}
