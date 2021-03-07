package com.br.recycle.api.service;

import java.util.Collections;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.br.recycle.api.exception.AppException;
import com.br.recycle.api.exception.BusinessException;
import com.br.recycle.api.exception.UserNotFoundException;
import com.br.recycle.api.model.Role;
import com.br.recycle.api.model.User;
import com.br.recycle.api.payload.RoleName;
import com.br.recycle.api.repository.RoleRepository;
import com.br.recycle.api.repository.UserRepository;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class UserService {

	@Autowired
	private UserRepository repository;

//	@Autowired
//	private GrupoService cadastroGrupo;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@PersistenceContext
	private EntityManager manager;

	@Autowired
	RoleRepository roleRepository;

	@Transactional
	public User save(User user) {

		Optional<User> foundUser = repository.findByEmail(user.getEmail());

		if (foundUser.isPresent() && !foundUser.get().equals(user)) {
			log.error("Email já cadastrado!");
			throw new BusinessException(
					String.format("Já existe um usuário cadastrado com o e-mail %s", user.getEmail()));

		}
		
		
		if (!user.getPassword().matches(user.getConfirmPassword())) {
			throw new BusinessException(
					String.format("Senha informada não conferem."));
		}
		user.setPassword(passwordEncoder.encode(user.getPassword()));

		Role userRole = roleRepository.findByName(RoleName.ROLE_USER.name())
				.orElseThrow(() -> new AppException("Grupo do usuário não definido."));

		user.setRoles(Collections.singleton(userRole));
		return repository.save(user);

	}

	@Transactional
	public User update(User user) {

		if (StringUtils.isEmpty(user.getRole())) {
			Object nameGroup = findByGroup(user.getId());
			user.setRole((RoleName.valueOf(nameGroup.toString())));
		}

		return repository.save(user);
	}

	@Transactional
	public User changePassword(Long userId, String passwordAtual, String novoPassword) {
		User user = fetchOrFail(userId);

		if (!passwordEncoder.matches(passwordAtual, user.getPassword())) {
			throw new BusinessException("A senha atual informada não coincide com a password do user.");
		}

		Object nameGroup = findByGroup(userId);
		user.setRole((RoleName.valueOf(nameGroup.toString())));
		user.setPassword(passwordEncoder.encode(novoPassword));

		return repository.save(user);
	}

	public User fetchOrFail(Long userId) {
		return repository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
	}

	public Object findByGroup(Long userId) {
		Object group = manager.createNativeQuery(
				"select s.name from users u inner join user_roles r on u.id_user= r.user_id  inner join roles s on s.id = r.role_id and u.id_user ='"
						+ userId + "'")
				.getSingleResult();
		return group;
	}

}