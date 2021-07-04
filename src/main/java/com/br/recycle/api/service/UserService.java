package com.br.recycle.api.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.br.recycle.api.exception.BusinessException;
import com.br.recycle.api.exception.NoContentException;
import com.br.recycle.api.exception.NotAcceptableException;
import com.br.recycle.api.exception.UnprocessableEntityException;
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
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@PersistenceContext
	private EntityManager manager;

	@Autowired
	RoleRepository roleRepository;

	/**
	 * Método responsável por buscar todos os usuários cadastrados na base de dados.
	 * @return {@code List<User>} 
	 * 		- Caso o retorno da base esteja vazio, retorna que nenhum conteúdo foi encontrado.
	 * 		- Caso o retorno da base tenha conteúdo, é retorno a lista de usuários.
	 */
	public List<User> findAll() {
		List<User> users = userRepository.findAll();
		
		if (users.isEmpty()) {
			throw new NoContentException("Nenhum usuário cadastrado na aplicação");
		}
		return users;
	}
	
	/**
	 * Método responsável por realizar a comunicação com a base de dados para cadastrar
	 * o usuário e antes realizar algumas verificações.
	 * @param {@code User} - user
	 * @return {@code User}
	 * 		- Caso o email que é informado no cadastro, seja o mesmo que já esteja cadastrado
	 * 	é lançado uma exception do tipo <i>NOT ACCEPTABLE</i>
	 * 		- Caso as senhas infromadas não sejam iguais, é lançada uma esception 
	 * 	<i>UNPROCESSABLE ENTITY</i>
	 * 		- Caso o tipo de usuário seja diferente do que é esperado, é lançado uma exception
	 * 	<i>BUSINESS EXCEPTION</i> informando que o grupo não está correto.
	 * 		- Caso esteja correto as informações, é cadastrado com sucesso o usuário.
	 */
	@Transactional
	public User save(User user) {

		Optional<User> userOptional = userRepository.findByEmail(user.getEmail());

		if (userOptional.isPresent() && !userOptional.get().equals(user)) {
			log.error("Email já cadastrado.");
			throw new NotAcceptableException(
					String.format("Já existe um usuário cadastrado com o e-mail %s", user.getEmail()));
		}
		
		if (!user.getPassword().matches(user.getConfirmPassword())) {
			log.error("Senhas não conferem.");
			throw new UnprocessableEntityException(
					String.format("As senhas informadas, não conferem."));
		}
		
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		Role userRole = roleRepository.findByName(RoleName.ROLE_USER.name())
				.orElseThrow(() -> new BusinessException("Grupo do usuário não definido."));

		user.setRoles(Collections.singleton(userRole));
		return userRepository.save(user);
	}

	@Transactional
	public User update(User user) {

		if (StringUtils.isEmpty(user.getRole())) {
			Object nameGroup = findByGroup(user.getId());
			user.setRole((RoleName.valueOf(nameGroup.toString())));
		}

		return userRepository.save(user);
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

		return userRepository.save(user);
	}

	/**
	 * Método responsável por buscar um usuário na base de dados por 
	 * id que está cadastrado.
	 * @param {@code Long} - userId 
	 * @return {@code User} - 
	 * 		- Caso encontrar o usuário por ID, é retornado os dados do usuário.
	 * 		- Caso não encontre o registro na base, é retornado um erro de usuário na encontrado.
	 */
	public User fetchOrFail(Long userId) {
		return userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
	}

	public Object findByGroup(Long userId) {
		Object group = manager.createNativeQuery(
				"select s.name from users u inner join user_roles r on u.id_user= r.user_id  inner join roles s on s.id = r.role_id and u.id_user ='"
						+ userId + "'")
				.getSingleResult();
		return group;
	}



}