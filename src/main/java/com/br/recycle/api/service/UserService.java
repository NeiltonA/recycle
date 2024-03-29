package com.br.recycle.api.service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.br.recycle.api.exception.EntityNotFoundException;
import com.br.recycle.api.exception.NoContentException;
import com.br.recycle.api.exception.NotAcceptableException;
import com.br.recycle.api.exception.UnprocessableEntityException;
import com.br.recycle.api.exception.UserNotFoundException;
import com.br.recycle.api.model.Role;
import com.br.recycle.api.model.User;
import com.br.recycle.api.payload.RoleName;
import com.br.recycle.api.repository.RoleRepository;
import com.br.recycle.api.repository.UserRepository;
import com.br.recycle.api.model.Flow;
import lombok.extern.log4j.Log4j2;

/**
 * Classe responsável por realizar os serviços das transações de comunicação
 * com a base de dados. 
 */
@Service
@Log4j2
public class UserService {

	private UserRepository userRepository;
	private PasswordEncoder passwordEncoder;
	private RoleRepository roleRepository;

	@PersistenceContext
	private EntityManager manager;

	@Autowired
	public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.roleRepository = roleRepository;
	}

	/**
	 * Método responsável por buscar todos os usuários cadastrados na base de dados.
	 * @return {@code List<User>} 
	 * 		- Caso o retorno da base esteja vazio, retorna que nenhum conteúdo foi encontrado.
	 * 		- Caso o retorno da base tenha conteúdo, é retorno a lista de usuários.
	 */
	//@Cacheable(cacheNames = "User", key="#root.method.name")
	public List<User> findAll() {
		log.info("User No cache");
		List<User> users = userRepository.findAll();
		
		if (users.isEmpty()) {
			throw new NoContentException("Nenhum usuário cadastrado na aplicação");
		}
		return users;
	}
	
	/**
	 * Método responsável por realizar o serviço de deletar um usuário.
	 * Primeiro busca o usuário na base de acordo com o Id dele e em seguida
	 * realiza a deleção da base de dados.
	 * @param {@code Long} - id
	 */
	//@CacheEvict(cacheNames = "User", key="#id")
	public void deleteById(Long id) {
		findById(id);
		
		userRepository.deleteById(id);
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
	//@CacheEvict(cacheNames = "User", allEntries = true)
	public User save(User user) {

		if(Flow.C.equals(user.getFlowIndicator())){
			user.setActive(false);
		}
		Optional<User> userOptionalCpf = userRepository.findByIndividualRegistration(user.getIndividualRegistration());

		Optional<User> userOptionalEmail = userRepository.findByEmail(user.getEmail());

		if (userOptionalCpf.isPresent() && !userOptionalCpf.get().equals(user)) {
			log.error("CPF já cadastrado.");
			throw new NotAcceptableException(
					String.format("Já existe um usuário cadastrado com o CPF %s.", user.getIndividualRegistration()));
		}
		
		if (userOptionalEmail.isPresent() && !userOptionalEmail.get().equals(user)) {
			log.error("E-mail já cadastrado.");
			throw new NotAcceptableException(
					String.format("Já existe um usuário cadastrado com o E-mail %s.", user.getEmail()));
		}
		
		if (!user.getPassword().matches(user.getConfirmPassword())) {
			log.error("Senhas não conferem.");
			throw new UnprocessableEntityException(
					String.format("As senhas informadas, não são iguais. Tente novamente!"));
		}
		
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		Role userRole = roleRepository.findByName(RoleName.ROLE_USER.name())
				.orElseThrow(() -> new EntityNotFoundException("Grupo do usuário não definido."));

		user.setRoles(Collections.singleton(userRole));
		return userRepository.save(user);
	}

	/**
	 * Método de serviço para atualizar os dados de usuário de acordo com o Id
	 * informado. Caso o id não seja encontrado na busca, é retornado que os dados 
	 * não foram encontrados.
	 * @param {@code User} - user
	 * @param {@code Long} - id
	 * @return {@code User} 
	 * 		- Caso o email informado na atualização seja diferente do email cadastrado
	 * 	retorna que a transação nao pode ser aceita.
	 * 		- Caso esteja tudo correto, os dados serão atualizados e salvo na base de dados.
	 */
	@Transactional
	//@CachePut(cacheNames = "User", key = "#user.getId()")
	public User updatPatch(User user, Long id) {
		User userActual = findById(id);

		user.setId(userActual.getId());
		user.setPassword(userActual.getPassword());
		user.setRoles(userActual.getRoles());
		user.setToken(userActual.getToken());
		user.setFlowIndicator(userActual.getFlowIndicator());

		if (Objects.nonNull(user.getIndividualRegistration())) {
			if (!userActual.getIndividualRegistration().matches(user.getIndividualRegistration())) {
				log.error("CPF não pode ser alterado!");
				throw new NotAcceptableException(
						String.format("CPF não pode ser alterado! %s", user.getIndividualRegistration()));
			}			
		}
		
		user.setIndividualRegistration(userActual.getIndividualRegistration());
		
		if (Objects.isNull(user.getEmail())) {
			user.setEmail(userActual.getEmail());
		}
			
		if(Objects.isNull(user.getName())) {
			user.setName(userActual.getName());
		}
		
		if(Objects.isNull(user.getCellPhone())) {
			user.setCellPhone(userActual.getCellPhone());
		}

		return userRepository.save(user);
	}

	/**
	 * Método responsável por realizar a alteração da senha do usuário. De acordo
	 * com o id informado, realiza a busca dos dados do usuário na base de dados e 
	 * faz a alteração para a nova senha.
	 * @param {@code Long} - userId
	 * @param {@code String} - passwordAtual
	 * @param {@code String} - novoPassword
	 * @return
	 * 		- Caso a senha atual informada na requisição, não coincidir com a senha
	 * 	que está salva na base de dados, é retornado que a entidade não pode ser processada.
	 * 		- Caso esteja tudo correto, é alterado com sucesso a senha na base de dados.
	 */
	@Transactional
	public User changePassword(Long userId, String passwordAtual, String novoPassword) {
		User user = findById(userId);

		if (!passwordEncoder.matches(passwordAtual, user.getPassword())) {
			throw new UnprocessableEntityException(
					String.format("Desculpe. A senha informada, não coincide com a senha no cadastro do usuário: '%s'", user.getName()));
		}
		
		if (passwordEncoder.matches(novoPassword, user.getPassword())) {
			throw new UnprocessableEntityException(
					String.format("Desculpe. A senha informada, é igual a senha que tem no cadastro do usuário: '%s'", user.getName()));
		}

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
	//@Cacheable(cacheNames = "User", key="#userId")
	public User findById(Long userId) {
		return userRepository.findById(userId)
				.orElseThrow(() -> new UserNotFoundException(userId));
	}

//	@Cacheable(cacheNames = "User", key="#userId")
//	public Object findByGroup(Long userId) {
//		Object group = manager.createNativeQuery(
//				"select s.name from users u inner join user_roles r on u.id_user= r.user_id  inner join roles s on s.id = r.role_id and u.id_user ='"
//						+ userId + "'")
//				.getSingleResult();
//		return group;
//	}
}
