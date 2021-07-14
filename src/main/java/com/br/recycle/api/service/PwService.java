package com.br.recycle.api.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.br.recycle.api.exception.UserInvalidException;
import com.br.recycle.api.model.User;
import com.br.recycle.api.payload.RoleName;
import com.br.recycle.api.repository.PwRepository;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * Classe responsável por realizar os serviços das transações de comunicação
 * com a base de dados, com relação a senha de autenticação.
 */
@Service
public class PwService {

	private static final long EXPIRE_TOKEN_AFTER_MINUTES = 30;

	private PwRepository pwRepository;
	private PasswordEncoder passwordEncoder;

	@PersistenceContext
	private EntityManager entityManager;

	@Value("${recycle.jwtSecret}")
	private String jwtSecret;
	
	@Value("${recycle.jwtExpirationInMsPw}")
    private int jwtExpirationInMsPw;

	@Autowired
	public PwService(PwRepository pwRepository, PasswordEncoder passwordEncoder) {
		this.pwRepository = pwRepository;
		this.passwordEncoder = passwordEncoder;
	}

	public String forgotPassword(String email) {

		Optional<User> userOptional = Optional.ofNullable(pwRepository.findByEmail(email));

		if (!userOptional.isPresent()) {
			throw new EntityNotFoundException("O e-mail informado não existe.");
		}

		User user = userOptional.get();

		Object nameGroup = findByGroup(user.getId());
		user.setRole((RoleName.valueOf(nameGroup.toString())));

		 Date now = new Date();
	     Date expiryDate = new Date(now.getTime() + jwtExpirationInMsPw);
		
		@SuppressWarnings("deprecation")
		String token = ((Jwts.builder().setSubject(Long.toString(user.getId())).setIssuedAt(new Date())
				.setId(user.getId().toString()) //jti: Json Token Identifier ou ID único para o token;
				.setAudience("Recycle") //Audience, quem vai consumir o seu token;
				.setExpiration(expiryDate).signWith(SignatureAlgorithm.HS512, jwtSecret).compact()));

		user.setToken(token);
		user.setTokenCreationDate(LocalDateTime.now());

		user = pwRepository.save(user);

		return user.getToken();
	}

	public String resetPassword(String token, String password) {
		String tokenjwt = token.replace("Bearer", " ").trim();
		Optional<User> userOptional = Optional.ofNullable(pwRepository.findByToken(tokenjwt.trim()));

		if (!userOptional.isPresent()) {
			throw new UserInvalidException("O token informado está inválido.");
		}

		LocalDateTime tokenCreationDate = userOptional.get().getTokenCreationDate();

		if (isTokenExpired(tokenCreationDate)) {
			throw new UserInvalidException("O token informado está expirado.");
		}

		User user = userOptional.get();
		user.setPassword(passwordEncoder.encode(password));

		Object nameGroup = findByGroup(user.getId());
		user.setRole((RoleName.valueOf(nameGroup.toString())));
		user.setToken(null);
		user.setTokenCreationDate(null);
		pwRepository.save(user);

		return password;
	}

	private boolean isTokenExpired(final LocalDateTime tokenCreationDate) {

		LocalDateTime now = LocalDateTime.now();
		Duration diff = Duration.between(tokenCreationDate, now);

		return diff.toMinutes() >= EXPIRE_TOKEN_AFTER_MINUTES;
	}

	public Object findByGroup(Long userId) {
		Object group = entityManager.createNativeQuery(
				"select s.name from users u inner join user_roles r on u.id_user= r.user_id  inner join roles s on s.id = r.role_id and u.id_user ='"
						+ userId + "'")
				.getSingleResult();
		return group;
	}

}
