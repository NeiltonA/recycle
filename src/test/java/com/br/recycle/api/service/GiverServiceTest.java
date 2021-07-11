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

import com.br.recycle.api.exception.GiverNotFoundException;
import com.br.recycle.api.exception.NoContentException;
import com.br.recycle.api.exception.UnprocessableEntityException;
import com.br.recycle.api.model.Cooperative;
import com.br.recycle.api.model.Flow;
import com.br.recycle.api.model.Giver;
import com.br.recycle.api.model.Role;
import com.br.recycle.api.model.User;
import com.br.recycle.api.repository.CooperativeRepository;
import com.br.recycle.api.repository.GiverRepository;

/**
 * Classe responsável por mapear os cenários de testes da classe de serviço do endereço.
 * 
 * @author Caio Henrique do Carmo Bastos
 * @since 10/07/2021
 *
 */
@ExtendWith(MockitoExtension.class)
public class GiverServiceTest {

	@Mock
	private GiverRepository giverRepository;
	
	@Mock
	private UserService userService;
	
	@Mock
	private CooperativeRepository cooperativeRepository;

	@InjectMocks
	private GiverService giverService;
	
	/*
	 * Método responsável por validar o cenário de busca de todos os doadores.
	 */
	@Test
	public void testFindAllSuccess() {
		given(giverRepository.findAll()).willReturn(getMockGivers());
		
		List<Giver> givers = giverService.findAll(null);
		
		assertAll(
				() -> assertEquals(Long.valueOf(1), givers.get(0).getId()),
				() -> assertEquals("b43b7541-3447-4bce-bc2c-571ff382b6ec", givers.get(0).getCode()),
				
				() -> assertEquals(Long.valueOf(1), givers.get(0).getUser().getId()),
				() -> assertEquals("Teste 1", givers.get(0).getUser().getName()),
				() -> assertEquals("develo11.tecnologia@gmail.com", givers.get(0).getUser().getEmail()),
				() -> assertEquals("11 983512000", givers.get(0).getUser().getCellPhone()),
				() -> assertEquals("10364680032", givers.get(0).getUser().getIndividualRegistration()),
				() -> assertEquals(Flow.D, givers.get(0).getUser().getFlowIndicator()),
				() -> assertEquals(true, givers.get(0).getUser().getActive())
		);
	}
	
	/*
	 * Método responsável por validar o cenário de busca de doador, com o id do USER.
	 */
	@Test
	public void testFindAllByUserIdSuccess() {
		given(giverRepository.findByUserId(1L)).willReturn(getMockGivers());
		
		List<Giver> givers = giverService.findAll(1L);
		
		assertAll(
				() -> assertEquals(Long.valueOf(1), givers.get(0).getId()),
				() -> assertEquals("b43b7541-3447-4bce-bc2c-571ff382b6ec", givers.get(0).getCode()),
				
				() -> assertEquals(Long.valueOf(1), givers.get(0).getUser().getId()),
				() -> assertEquals("Teste 1", givers.get(0).getUser().getName()),
				() -> assertEquals("develo11.tecnologia@gmail.com", givers.get(0).getUser().getEmail()),
				() -> assertEquals("11 983512000", givers.get(0).getUser().getCellPhone()),
				() -> assertEquals("10364680032", givers.get(0).getUser().getIndividualRegistration()),
				() -> assertEquals(Flow.D, givers.get(0).getUser().getFlowIndicator()),
				() -> assertEquals(true, givers.get(0).getUser().getActive())
		);
	}
	
	/*
	 * Método responsável por validar o cenário de busca de todos os doadores,
	 * mas a base de dados está vazia, então retorna um conteúdo vazio.
	 */
	@Test
	public void testFindAllNoContent() {
		given(giverRepository.findAll()).willReturn(Collections.emptyList());
		
		assertThrows(NoContentException.class, () -> giverService.findAll(null));
	}
	
	/**
	 * Método responsável por realizar a busca de doador por ID.
	 */
	@Test
	public void testFindOrFailSuccess() {
		given(giverRepository.findById(1L)).willReturn(Optional.of(getMockGiver()));
		
		Giver giver = giverService.findOrFail(1L);
		assertNotNull(giver);
		
		assertAll(
				() -> assertEquals(Long.valueOf(1), giver.getId()),
				() -> assertEquals("b43b7541-3447-4bce-bc2c-571ff382b6ec", giver.getCode()),
				
				() -> assertEquals(Long.valueOf(1), giver.getUser().getId()),
				() -> assertEquals("Teste 1", giver.getUser().getName()),
				() -> assertEquals("develo11.tecnologia@gmail.com", giver.getUser().getEmail()),
				() -> assertEquals("11 983512000", giver.getUser().getCellPhone()),
				() -> assertEquals("10364680032", giver.getUser().getIndividualRegistration()),
				() -> assertEquals(Flow.D, giver.getUser().getFlowIndicator()),
				() -> assertEquals(true, giver.getUser().getActive())
		);
	}
	
	/**
	 * Método responsável por realizar a busca de doador por ID,
	 * com erro ao buscar o usuário
	 */
	@Test
	public void testFindOrFailGiverNotFound() {
		given(giverRepository.findById(1L)).willThrow(GiverNotFoundException.class);
		
		assertThrows(GiverNotFoundException.class, () -> giverService.findOrFail(1L));
	}

	/**
	 * Método responsável por realizar o cenário onde é salvo os dados do usuário
	 * como doador na base de dados.
	 */
	@Test
	public void testSaveSuccess() {
		given(userService.findById(1L)).willReturn(getMockUser());
		given(giverRepository.findByUserId(1L)).willReturn(Collections.emptyList());
		given(cooperativeRepository.findById(1L)).willReturn(Optional.empty());
		doReturn(getMockGiverSave()).when(giverRepository).save(getMockGiverSave());
		
		Giver giver = giverService.save(1L);
		assertNotNull(giver);
	}
	
	/**
	 * Método responsável por realizar o cenário onde é informado um id de usuário que já é doador.
	 */
	@Test
	public void testSaveGiverUnprocessableEntity() {
		given(userService.findById(1L)).willReturn(getMockUser());
		given(giverRepository.findByUserId(1L)).willReturn(List.of(getMockGiver()));
		
		assertThrows(UnprocessableEntityException.class, () -> giverService.save(1L));
	}

	/**
	 * Método responsável por realizar o cenário onde é informado um id de usuário que já é cooperative.
	 */
	@Test
	public void testSaveCooperativeUnprocessableEntity() {
		given(userService.findById(1L)).willReturn(getMockUser());
		given(giverRepository.findByUserId(1L)).willReturn(Collections.emptyList());
		given(cooperativeRepository.findById(1L)).willReturn(Optional.of(getMockCooperative()));
		
		assertThrows(UnprocessableEntityException.class, () -> giverService.save(1L));
	}
	
	
	private Cooperative getMockCooperative() {
		Cooperative cooperative = new Cooperative();
		cooperative.setId(1L);
		
		return cooperative;
	}

	private Giver getMockGiverSave() {
		Giver giver = new Giver();
		giver.setUser(getMockUserSave());
		
		return giver;
	}

	private User getMockUserSave() {
		User user = new User();
		user.setId(1L);
		
		return user;
	}

	private Giver getMockGiver() {
		Giver giver = new Giver();
		giver.setId(1L);
		giver.setCode("b43b7541-3447-4bce-bc2c-571ff382b6ec");
		giver.setUser(getMockUser());
		
		return giver;
	}

	private List<Giver> getMockGivers() {
		Giver giver = new Giver();
		giver.setId(1L);
		giver.setCode("b43b7541-3447-4bce-bc2c-571ff382b6ec");
		giver.setUser(getMockUser());
		
		return List.of(giver);
	}

	private User getMockUser() {
		User user = new User();
		user.setId(1L);
		user.setName("Teste 1");
		user.setEmail("develo11.tecnologia@gmail.com");
		user.setCellPhone("11 983512000");
		user.setIndividualRegistration("10364680032");
		user.setFlowIndicator(Flow.D);
		user.setActive(true);
		user.setRoles(getMockRoles());
		return user;
	}

	private Set<Role> getMockRoles() {
		Role role = new Role();
		role.setName("ROLE_USER");
		
		return Set.of(role);
	}
}
