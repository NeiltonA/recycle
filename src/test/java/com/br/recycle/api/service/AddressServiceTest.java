package com.br.recycle.api.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import com.br.recycle.api.exception.AddressNotFoundException;
import com.br.recycle.api.exception.InternalServerException;
import com.br.recycle.api.exception.NoContentException;
import com.br.recycle.api.model.Address;
import com.br.recycle.api.model.User;
import com.br.recycle.api.repository.AddressRepository;

/**
 * Classe responsável por mapear os cenários de testes da classe de serviço do endereço.
 * 
 * @author Caio Henrique do Carmo Bastos
 * @since 10/07/2021
 *
 */
@ExtendWith(MockitoExtension.class)
public class AddressServiceTest {

	@Mock
	private AddressRepository addressRepository;
	
	@InjectMocks
	private AddressService addressService;
	
	/**
	 * Método responsável por validar o cenário onde é solicitado a busca
	 * de todos os endereços na base de dados e retorna uma lista de endereços.
	 */
	@Test
	public void testFindAllSuccess() {
		given(addressRepository.findAll()).willReturn(getMockAddresses());
		
		List<Address> addresses = addressService.findAll(null);
		assertNotNull(addresses);
		assertEquals(1, addresses.size());
		
		assertAll(
				() -> assertEquals(Long.valueOf(1), addresses.get(0).getId()),
				() -> assertEquals("Rua Doutor Carlos Teste", addresses.get(0).getStreet()),
				() -> assertEquals("123", addresses.get(0).getNumber()),
				() -> assertEquals("teste", addresses.get(0).getComplement()),
				() -> assertEquals("96766200", addresses.get(0).getZipCode()),
				() -> assertEquals("Pq Teste", addresses.get(0).getNeighborhood()),
				() -> assertEquals("SP", addresses.get(0).getState()),
				() -> assertEquals("Taboao da Serra", addresses.get(0).getCity())
		);
	}
	
	/**
	 * Método responsável por validar o cenário onde é solicitado a busca
	 * de todos os endereços com o ID de usuário na base de dados e retorna uma lista de endereços.
	 */
	@Test
	public void testFindAllByIdUserSuccess() {
		given(addressRepository.findByUserId(1L)).willReturn(getMockAddresses());
		
		List<Address> addresses = addressService.findAll(1L);
		assertNotNull(addresses);
		assertEquals(1, addresses.size());
		
		assertAll(
				() -> assertEquals(Long.valueOf(1), addresses.get(0).getId()),
				() -> assertEquals("Rua Doutor Carlos Teste", addresses.get(0).getStreet()),
				() -> assertEquals("123", addresses.get(0).getNumber()),
				() -> assertEquals("teste", addresses.get(0).getComplement()),
				() -> assertEquals("96766200", addresses.get(0).getZipCode()),
				() -> assertEquals("Pq Teste", addresses.get(0).getNeighborhood()),
				() -> assertEquals("SP", addresses.get(0).getState()),
				() -> assertEquals("Taboao da Serra", addresses.get(0).getCity())
		);
	}
	
	/**
	 * Método responsável por validar o cenário onde é solicitado a busca
	 * de todos os emdereços na base de dados e retorna uma lista vazia.
	 */
	@Test
	public void testFindAllNoContent() {
		given(addressRepository.findAll()).willReturn(Collections.emptyList());

		assertThrows(NoContentException.class, () -> addressService.findAll(null));
	}
	
	/**
	 * Método responsável por validar o cenário onde é solicitado a busca
	 * de todos os emdereços com usuário na base de dados e retorna uma lista vazia.
	 */
	@Test
	public void testFindAllByIdUserNoContent() {
		given(addressRepository.findByUserId(1L)).willReturn(Collections.emptyList());

		assertThrows(NoContentException.class, () -> addressService.findAll(1L));
	}

	/**
	 * Método responsável por salvar os endereços na base de dados.
	 */
	@Test
	public void testSaveSuccess() {
		doReturn(getMockAddress()).when(addressRepository).save(getMockAddress());
		addressService.save(getMockAddress());
	}
	
	/**
	 * Método responsável por salvar os endereços na base de dados, 
	 * mas ocorre algum erro e é lançado a exceçaõ de erro interno.
	 */
	@Test
	public void testSaveInternalServerError() {
		doThrow(InternalServerException.class).when(addressRepository).save(getMockAddress());
		assertThrows(InternalServerException.class, () -> addressService.save(getMockAddress()));
	}
	
	/**
	 * Método responsável por realizar a atualização parcial do endereço.
	 */
	@Test
	public void testUpdatePartialSuccess() {
		given(addressRepository.findById(1L)).willReturn(Optional.of(getMockAddressNew()));
		doReturn(getMockAddressNew()).when(addressRepository).save(getMockAddressNew());
		
		addressService.updatePartial(getMockAddressNew(), 1L);
	}
	
	/**
	 * Método responsável por realizar a atualização parcial do endereço, mas ocorre erro
	 * ao realizar a atualização.
	 */
	@Test
	public void testUpdatePartialDataIntegritViolation() {
		given(addressRepository.findById(1L)).willReturn(Optional.of(getMockAddressNew()));
		doThrow(DataIntegrityViolationException.class).when(addressRepository).save(getMockAddressNew());
		
		assertThrows(AddressNotFoundException.class, () -> addressService.updatePartial(getMockAddressNew(), 1L));
	}
	
	/**
	 * Método responsável por realizar a atualização por completo do endereço.
	 */
	@Test
	public void testUpdateSuccess() {
		given(addressRepository.findById(1L)).willReturn(Optional.of(getMockAddressNew()));
		doReturn(getMockAddressNew()).when(addressRepository).save(getMockAddressNew());
		
		addressService.update(getMockAddressNew(), 1L);
	}
	
	/**
	 * Método responsável por realizar a atualização por completo do endereço, mas ocorre erro
	 * ao realizar a atualização.
	 */
	@Test
	public void testUpdateDataIntegritViolation() {
		given(addressRepository.findById(1L)).willReturn(Optional.of(getMockAddress()));
		doReturn(getMockAddress()).when(addressRepository).save(getMockAddressNew());
		
		addressService.update(getMockAddressNew(), 1L);
	}
	
	/**
	 * Método responsável por realizar a busca de endereços por ID do endereço.
	 */
	@Test
	public void testFindOrFailSuccess() {
		given(addressRepository.findById(1L)).willReturn(Optional.of(getMockAddress()));
		
		Address address = addressService.findOrFail(1L);
		assertNotNull(address);
		
		assertAll(
				() -> assertEquals(Long.valueOf(1), address.getId()),
				() -> assertEquals("Rua Doutor Carlos Teste", address.getStreet()),
				() -> assertEquals("123", address.getNumber()),
				() -> assertEquals("teste", address.getComplement()),
				() -> assertEquals("96766200", address.getZipCode()),
				() -> assertEquals("Pq Teste", address.getNeighborhood()),
				() -> assertEquals("SP", address.getState()),
				() -> assertEquals("Taboao da Serra", address.getCity())
		);
	}
	
	/**
	 * Método responsável por realizar a busca de endereços por ID do endereço,
	 * com erro ao buscar o usuário
	 */
	@Test
	public void testFindOrFailAddressNotFound() {
		given(addressRepository.findById(1L)).willThrow(AddressNotFoundException.class);
		
		assertThrows(AddressNotFoundException.class, () -> addressService.findOrFail(1L));
	}
	
	/**
	 * Método responsável por validar o cenário de sucess de deleção.
	 */
	@Test
	public void testDeleteByIdSuccess() {
		//given(addressRepository.findById(1L)).willReturn(Optional.of(getMockAddress()));		
		//addressService.deleteById(1L);
		//verify(addressRepository, times(1)).deleteById(1L);
	}

	private Address getMockAddressNew() {
		Address address = new Address();
		address.setId(1L);
		address.setStreet("Rua Doutor Carlos Siqueira");
		address.setNumber("1234");
		address.setComplement("ComplementoTeste");
		address.setZipCode("96766201");
		address.setNeighborhood("Pq Maraba");
		address.setState("SP");
		address.setCity("Taboao da Serra");
		
		User user = new User();
		user.setId(1L);
		address.setUser(user);
		
		return address;
	}

	private Address getMockAddress() {
		Address address = new Address();
		address.setId(1L);
		address.setStreet("Rua Doutor Carlos Teste");
		address.setNumber("123");
		address.setComplement("teste");
		address.setZipCode("96766200");
		address.setNeighborhood("Pq Teste");
		address.setState("SP");
		address.setCity("Taboao da Serra");
		
		User user = new User();
		user.setId(1L);
		address.setUser(user);
		
		return address;
	}

	private List<Address> getMockAddresses() {
		Address address = new Address();
		address.setId(1L);
		address.setStreet("Rua Doutor Carlos Teste");
		address.setNumber("123");
		address.setComplement("teste");
		address.setZipCode("96766200");
		address.setNeighborhood("Pq Teste");
		address.setState("SP");
		address.setCity("Taboao da Serra");
		
		User user = new User();
		user.setId(1L);
		address.setUser(user);
		
		return List.of(address);
	}
}
