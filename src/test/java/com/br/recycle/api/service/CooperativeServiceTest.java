package com.br.recycle.api.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.br.recycle.api.exception.CooperativeNotFoundException;
import com.br.recycle.api.exception.InternalServerException;
import com.br.recycle.api.exception.NoContentException;
import com.br.recycle.api.exception.UnprocessableEntityException;
import com.br.recycle.api.mock.UserMock;
import com.br.recycle.api.model.Cooperative;
import com.br.recycle.api.repository.CooperativeRepository;
import com.br.recycle.api.repository.GiverRepository;

/**
 * Classe responsável por validar os cenários de testes da classe de serviço da
 * cooperativa.
 * 
 * @author Caio Henrique do Carmo Bastos
 * @since 11/07/2021
 *
 */
@ExtendWith(MockitoExtension.class)
public class CooperativeServiceTest {

	@Mock
	private CooperativeRepository cooperativeRepository;

	@Mock
	private GiverRepository giverRepository;

	@InjectMocks
	private CooperativeService cooperativeService;

	/**
	 * Método responsável por validar o cenário onde a busca de dados da cooperativa
	 * retorna com sucesso.
	 */
	@Test
	public void testFindAllSuccess() {
		given(cooperativeRepository.findAll()).willReturn(getMockCooperatives());
		List<Cooperative> cooperatives = cooperativeService.findAll(null);
		assertNotNull(cooperatives);
		assertEquals(1, cooperatives.size());
	}

	/**
	 * Método responsável por validar o cenário onde a busca de dados da cooperativa
	 * por id do usuário retorna com sucesso.
	 */
	@Test
	public void testFindAllByIdUserSuccess() {
		given(cooperativeRepository.findByUserId(1L)).willReturn(getMockCooperatives());
		List<Cooperative> cooperatives = cooperativeService.findAll(1L);
		assertNotNull(cooperatives);
		assertEquals(1, cooperatives.size());
	}

	/**
	 * Método responsável por validar o cenário onde a busca de dados da cooperativa
	 * por id do usuário retorna uma lista vazia e a lança a exceção de sem
	 * conteudo.
	 */
	@Test
	public void testFindAllByIdUserNoContent() {
		given(cooperativeRepository.findByUserId(1L)).willReturn(Collections.emptyList());

		assertThrows(NoContentException.class, () -> cooperativeService.findAll(1L));
	}

	/**
	 * Método responsável por validar o cenário onde a busca de dados da cooperativa
	 * por id retorna com sucesso.
	 */
	@Test
	public void testFindByIdSuccess() {
		given(cooperativeRepository.findById(1L)).willReturn(Optional.of(getMockCooperative()));
		Cooperative cooperative = cooperativeService.findOrFail(1L);
		assertNotNull(cooperative);
	}

	/**
	 * Método responsável por validar o cenário onde a busca de dados da cooperativa
	 * por id que não existe e lança a exceção.
	 */
	@Test
	public void testFindByIdCooperativeNotFound() {
		given(cooperativeRepository.findById(1L)).willReturn(Optional.empty());
		assertThrows(CooperativeNotFoundException.class, () -> cooperativeService.findOrFail(1L));
	}

	/**
	 * Método responsável por validar o cenário onde é salvo com sucesso os dados da
	 * cooperativa.
	 */
	@Test
	public void testSaveSuccess() {
		given(cooperativeRepository.findByCnpj("01445418000175")).willReturn(Collections.emptyList());
		doReturn(getMockCooperative()).when(cooperativeRepository).save(getMockCooperative());

		cooperativeService.save(getMockCooperative());
	}

	/**
	 * Método responsável por validar o cenário onde ocorre um erro ao salvar os
	 * dados porque já existe um CNPJ cadastrado.
	 */
	@Test
	public void testSaveUnprocessableEntityCnpj() {
		given(cooperativeRepository.findByCnpj("01445418000175")).willReturn(getMockCooperatives());

		assertThrows(UnprocessableEntityException.class, () -> cooperativeService.save(getMockCooperative()));
	}

	/**
	 * Método responsável por validar o cenário onde ocorre um erro ao salvar os
	 * dados, mas o erro é interno.
	 */
	@Test
	public void testSaveInternalServerError() {
		given(cooperativeRepository.findByCnpj("01445418000175")).willReturn(Collections.emptyList());
		doThrow(InternalServerException.class).when(cooperativeRepository).save(getMockCooperative());

		assertThrows(InternalServerException.class, () -> cooperativeService.save(getMockCooperative()));
	}

	/**
	 * Método responsável por validar o cenário onde os dados da cooperativa é salvo
	 * com sucesso.
	 */
	@Test
	public void testUpdateSuccess() {
		given(cooperativeRepository.findById(2L)).willReturn(Optional.of(getMockCooperative()));
		doReturn(getMockCooperative()).when(cooperativeRepository).save(getMockCooperative());

		cooperativeService.update(2L, getMockCooperative());
	}
	
	/**
	 * Método responsável por validar o cenário onde os dados da cooperativa é informado
	 * um CNPJ do que já está cadastrado.
	 */
	@Test
	public void testUpdateUnprocessabelEntityCnpj() {
		given(cooperativeRepository.findById(2L)).willReturn(Optional.of(getMockCooperative()));

		assertThrows(UnprocessableEntityException.class, () -> cooperativeService.update(2L, getMockCooperativeErro()));
	}
	
	/**
	 * Método responsável por validar o cenário onde os dados da cooperativa ocorre
	 * um erro ao salvar os dados
	 */
	@Test
	public void testUpdateInternalServerError() {
		given(cooperativeRepository.findById(2L)).willReturn(Optional.of(getMockCooperative()));
		doThrow(InternalServerException.class).when(cooperativeRepository).save(getMockCooperative());

		assertThrows(InternalServerException.class, () -> cooperativeService.update(2L, getMockCooperative()));
	}
	
	/**
	 * Método responsável por validar o cenário de sucess de deleção.
	 */
	@Test
	public void testDeleteByIdSuccess() {
		given(cooperativeRepository.findById(1L)).willReturn(Optional.of(getMockCooperative()));		
		cooperativeService.delete(1L);
		verify(cooperativeRepository, times(1)).deleteById(1L);
	}
	
	private Cooperative getMockCooperativeErro() {
		Cooperative cooperative = new Cooperative();
		cooperative.setId(2L);
		cooperative.setCompanyName("RC aqui no Brasil");
		cooperative.setFantasyName("RCBrasil");
		cooperative.setCnpj("62702148000125");
		cooperative.setUser(UserMock.getMockUserDto());

		return cooperative;
	}

	private Cooperative getMockCooperative() {
		Cooperative cooperative = new Cooperative();
		cooperative.setId(2L);
		cooperative.setCompanyName("RC aqui no Brasil");
		cooperative.setFantasyName("RCBrasil");
		cooperative.setCnpj("01445418000175");
		cooperative.setUser(UserMock.getMockUserDto());

		return cooperative;
	}

	private List<Cooperative> getMockCooperatives() {
		Cooperative cooperative = new Cooperative();
		cooperative.setId(2L);
		cooperative.setCompanyName("RC aqui no Brasil");
		cooperative.setFantasyName("RCBrasil");
		cooperative.setCnpj("01445418000175");
		cooperative.setUser(UserMock.getMockUserDto());

		return List.of(cooperative);
	}

}
