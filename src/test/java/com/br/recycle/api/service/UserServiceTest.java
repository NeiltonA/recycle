package com.br.recycle.api.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doReturn;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.br.recycle.api.exception.EntityNotFoundException;
import com.br.recycle.api.exception.NoContentException;
import com.br.recycle.api.exception.NotAcceptableException;
import com.br.recycle.api.exception.UnprocessableEntityException;
import com.br.recycle.api.exception.UserNotFoundException;
import com.br.recycle.api.model.Flow;
import com.br.recycle.api.model.Role;
import com.br.recycle.api.model.User;
import com.br.recycle.api.payload.RoleName;
import com.br.recycle.api.repository.RoleRepository;
import com.br.recycle.api.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

	private final Long id = 1L;

	@Mock
	private UserRepository userRepository;
	
	@Mock
	private PasswordEncoder passwordEncoder;
	
	@Mock 
	private RoleRepository roleRepository;

	@InjectMocks
	private UserService userService;
	
	/**
	 * Método responsável por validar o cenário onde é solicitado a busca
	 * de todos os usuários na base de dados e retorna uma lista de usuários.
	 */
	@Test
	public void testFindAllSuccess() {
		given(userRepository.findAll()).willReturn(getMockUsers());

		List<User> users = userService.findAll();
		assertNotNull(users);
		assertEquals(2, users.size());
		
		assertAll(
				() -> assertEquals(Long.valueOf(1), users.get(0).getId()),
				() -> assertEquals("Caio Henrique Teste", users.get(0).getName()),
				() -> assertEquals("caio.cbastos@hotmail.com", users.get(0).getEmail()),
				() -> assertEquals(RoleName.ROLE_USER, users.get(0).getRole()),
				() -> assertEquals("$2a$10$X1tpGensr3VenL6oO/FWI.RA.y2nQTwpJ0y0Sx5l8DpyCeqa602xy", users.get(0).getPassword()),
				() -> assertEquals("11983512009", users.get(0).getCellPhone()),
				() -> assertEquals("61061700020", users.get(0).getIndividualRegistration()),
				() -> assertEquals(Flow.C, users.get(0).getFlowIndicator()),
				
				() -> assertEquals(Long.valueOf(2), users.get(1).getId()),
				() -> assertEquals("Henrique Bastos", users.get(1).getName()),
				() -> assertEquals("caio12bastos@gmail.com", users.get(1).getEmail()),
				() -> assertEquals(RoleName.ROLE_USER, users.get(1).getRole()),
				() -> assertEquals("$2a$10$X1tpGensr3VenL6oO/FWI.RA.y2nQTwpJ0y0Sx5l8DpyCeqa602xy", users.get(1).getPassword()),
				() -> assertEquals("11983512000", users.get(1).getCellPhone()),
				() -> assertEquals("61061700020", users.get(1).getIndividualRegistration()),
				() -> assertEquals(Flow.D, users.get(1).getFlowIndicator())
		);
	}
	
	/**
	 * Método responsável por realizar o cadastro de um usuário na base de dados
	 * e salvar esses dados.
	 */
	@Test
	public void testSaveSuccess() {
		
		given(userRepository.findByEmail(getMockUserRequest().getEmail())).willReturn(Optional.empty());
		given(passwordEncoder.encode(getMockUserRequest().getPassword())).willReturn("$2a$10$WXaRSUd08sUk67sQvxTDZO0XT7vVl2iWDJpcZAgP/xUuIncV6J/UC"); 
		given(roleRepository.findByName(RoleName.ROLE_USER.name())).willReturn(getMockRole());
		doReturn(getMockUserRequest()).when(userRepository).save(getMockUserRequestSave());

		User userResponse = userService.save(getMockUserRequest());
		assertNotNull(userResponse);	
	}
	
	/**
	 * Método responsável por realizar o cadastro de um usuário na base de dados
	 * e lançar a exception relacionado os emails são diferentes.
	 */
	@Test
	public void testSaveNotAcceptable() {
		
		given(userRepository.findByEmail(getMockUserRequestErro().getEmail())).willReturn(Optional.of(getMockUserRequest()));

		assertThrows(NotAcceptableException.class, () -> userService.save(getMockUserRequestErro()));	
	}
	
	/**
	 * Método responsável por realizar o cadastro de um usuário na base de dados
	 * e lançar a exception relacionado as senhas são diferentes.
	 */
	@Test
	public void testSaveUnprocessableEntity() {
		
		given(userRepository.findByEmail(getMockUserRequestErro().getEmail())).willReturn(Optional.empty());
		
		assertThrows(UnprocessableEntityException.class, () -> userService.save(getMockUserRequestErro()));	
	}

	/**
	 * Método responsável por realizar o cadastro de um usuário na base de dados
	 * e lançar a exception relacionado ao grupo de usuário.
	 */
	@Test
	public void testSaveEntityNotFound() {
		
		given(userRepository.findByEmail(getMockUserRequest().getEmail())).willReturn(Optional.empty());
		given(passwordEncoder.encode(getMockUserRequest().getPassword())).willReturn("$2a$10$WXaRSUd08sUk67sQvxTDZO0XT7vVl2iWDJpcZAgP/xUuIncV6J/UC"); 

		assertThrows(EntityNotFoundException.class, () -> userService.save(getMockUserRequest()));	
	}

	/**
	 * Método responsável por validar o cenário onde é solicitado a busca
	 * de todos os usuários na base de dados e uma lista vazio e lança a exceção
	 * do tipo NO_CONTENT
	 */
	@Test
	public void testFindAllNoContent() {
		given(userRepository.findAll()).willReturn(Collections.emptyList());

		assertThrows(NoContentException.class, () -> userService.findAll());
	}

	/**
	 * Método responsável por validar o cenário onde é informado
	 * um ID de usuário que esteja cadastrado na base de dados e retorna
	 * com sucesso a busca pelo ID.
	 */
	@Test
	public void testFindByIdSuccess() {
		given(userRepository.findById(id)).willReturn(Optional.of(getMockUser()));

		User user = userService.findById(id);
		assertNotNull(user);
		assertAll(
				() -> assertEquals(Long.valueOf(1), user.getId()),
				() -> assertEquals("Caio Henrique Teste", user.getName()),
				() -> assertEquals("caio12bastos@gmail.com", user.getEmail()),
				() -> assertEquals(RoleName.ROLE_USER, user.getRole()),
				() -> assertEquals("$2a$10$X1tpGensr3VenL6oO/FWI.RA.y2nQTwpJ0y0Sx5l8DpyCeqa602xy", user.getPassword()),
				() -> assertEquals("11983512009", user.getCellPhone()),
				() -> assertEquals("61061700020", user.getIndividualRegistration()),
				() -> assertEquals(Flow.C, user.getFlowIndicator())
		);
	}
	
	/**
	 * Método responsável por validar o cenário de procurar 
	 * o id do usuário onde não se encontra na base de dados.
	 */
	@Test
	public void testFindByIdNotFound() {
		given(userRepository.findById(id)).willThrow(new UserNotFoundException());
		
		assertThrows(UserNotFoundException.class, () -> userService.findById(id));
	}
	
	/**
	 * Método responsável por validar o cenário onde é atualizado 
	 * em partes os dados de usuário.
	 */
	@Test
	public void testUpdateSuccess() {
		given(userRepository.findById(id)).willReturn(Optional.of(getMockUser()));
		doReturn(getMockUser()).when(userRepository).save(getMockUser());

		User user = userService.update(getMockUser(), 1L);
		assertNotNull(user);
	}
	
	/**
	 * Método responsável por validar o cenário onde é os emails
	 * são diferentes.
	 */
	@Test
	public void testUpdateNotAcceptable() {
		given(userRepository.findById(id)).willReturn(Optional.of(getMockUserRequestErro()));

		assertThrows(NotAcceptableException.class, () -> userService.update(getMockUser(), 1L));

	}
		
	private List<User> getMockUsers() {
		User user = new User();
		user.setId(1L);
		user.setName("Caio Henrique Teste");
		user.setEmail("caio.cbastos@hotmail.com");
		user.setRole(RoleName.ROLE_USER);
		user.setPassword("$2a$10$X1tpGensr3VenL6oO/FWI.RA.y2nQTwpJ0y0Sx5l8DpyCeqa602xy");
		user.setCellPhone("11983512009");
		user.setIndividualRegistration("61061700020");
		user.setFlowIndicator(Flow.C);
		
		User user1 = new User();
		user1.setId(2L);
		user1.setName("Henrique Bastos");
		user1.setEmail("caio12bastos@gmail.com");
		user1.setRole(RoleName.ROLE_USER);
		user1.setPassword("$2a$10$X1tpGensr3VenL6oO/FWI.RA.y2nQTwpJ0y0Sx5l8DpyCeqa602xy");
		user1.setCellPhone("11983512000");
		user1.setIndividualRegistration("61061700020");
		user1.setFlowIndicator(Flow.D);
		
		return List.of(user, user1);
	}
	
	/**
	 * Mock para representar os dados de retorno da base de dados do usuário.
	 * @return {@code User} - entidade de usuário.
	 */
	private User getMockUser() {
		User user = new User();
		user.setId(1L);
		user.setName("Caio Henrique Teste");
		user.setEmail("caio12bastos@gmail.com");
		user.setRole(RoleName.ROLE_USER);
		user.setPassword("$2a$10$X1tpGensr3VenL6oO/FWI.RA.y2nQTwpJ0y0Sx5l8DpyCeqa602xy");
		user.setCellPhone("11983512009");
		user.setIndividualRegistration("61061700020");
		user.setFlowIndicator(Flow.C);
		
		return user;
	}
	
	private User getMockUserRequest() {
		User user = new User();
		user.setId(2L);
		user.setName("Bastos Teste");
		user.setEmail("caio12bastos@gmail.com");
		user.setRole(RoleName.ROLE_USER);
		user.setPassword("caioBastos123");
		user.setConfirmPassword("caioBastos123");
		user.setCellPhone("11983512009");
		user.setIndividualRegistration("61061700020");
		user.setFlowIndicator(Flow.D);
		
		return user;
	}
	
	private User getMockUserRequestSave() {
		User user = new User();
		user.setId(2L);
		user.setName("Bastos Teste");
		user.setEmail("caio12bastos@gmail.com");
		user.setRole(RoleName.ROLE_USER);
		user.setPassword("$2a$10$WXaRSUd08sUk67sQvxTDZO0XT7vVl2iWDJpcZAgP/xUuIncV6J/UC");
		user.setConfirmPassword("caioBastos123");
		user.setCellPhone("11983512009");
		user.setIndividualRegistration("61061700020");
		user.setFlowIndicator(Flow.D);
		user.setRoles(Set.of(getMockRole().get()));
		
		return user;
	}
	
	private User getMockUserRequestErro() {
		User user = new User();
		user.setId(2L);
		user.setName("Bastos Teste");
		user.setEmail("caio.cbastos@hotmail.com");
		user.setRole(RoleName.ROLE_USER);
		user.setPassword("$2a$10$WXaRSUd08sUk67sQvxTDZO0XT7vVl2iWDJpcZAgP/xUuIncV6J/UC");
		user.setConfirmPassword("caioBastos1");
		user.setCellPhone("11983512009");
		user.setIndividualRegistration("61061700020");
		user.setFlowIndicator(Flow.D);
		user.setRoles(Set.of(getMockRole().get()));
		
		return user;
	}
	
	private Optional<Role> getMockRole() {
		Role role = new Role();
		role.setId(1L);
		role.setName("ROLE_USER");
		
		return Optional.of(role);
	}

}
