package com.br.recycle.api.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.br.recycle.api.exception.AddressNotFoundException;
import com.br.recycle.api.exception.InternalServerException;
import com.br.recycle.api.exception.NoContentException;
import com.br.recycle.api.mock.UserMock;
import com.br.recycle.api.model.Address;
import com.br.recycle.api.repository.AddressRepository;

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

		List<Address> addresses = addressService.findAll();
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

		assertThrows(NoContentException.class, () -> addressService.findAll());
	}
	
	/**
	 * Método responsável por validar o cenário onde realiza a busca
	 * de dados de acordo com o ID.
	 */
	@Test
	public void testFindOrFailSuccess() {
		given(addressRepository.findById(1L)).willReturn(Optional.of(getMockAddress()));
		
		Address address = addressService.findOrFail(1L);
		assertNotNull(address);
		assertAll(
				() -> assertEquals(Long.valueOf(1), address.getId()),
				() -> assertEquals("Rua Doutor Carlos Siqueira Neto", address.getStreet()),
				() -> assertEquals("123", address.getNumber()),
				() -> assertEquals("", address.getComplement()),
				() -> assertEquals("06766200", address.getZipCode()),
				() -> assertEquals("Pq Teste", address.getNeighborhood()),
				() -> assertEquals("SP", address.getState()),
				() -> assertEquals("Taboao", address.getCity())
		);
	}
	
	/**
	 * Método responsável por validar o cenário onde realiza a busca
	 * de dados de acordo com o ID e lança exceção de não encontrado.
	 */
	@Test
	public void testFindOrFailAddressNotFound() {
		given(addressRepository.findById(1L)).willThrow(AddressNotFoundException.class);
		
		assertThrows(AddressNotFoundException.class, () -> addressService.findOrFail(1L));
	}
	
	/**
	 * Método responsável por validar o cenário onde é realizado o cadastro
	 * de endereços na base de dados.
	 */
	@Test
	public void testSaveSuccess() {
		doReturn(getMockAddress()).when(addressRepository).save(getMockAddress());

		Address address = addressService.save(getMockAddress());
		assertNotNull(address);
		assertAll(
				() -> assertEquals(Long.valueOf(1), address.getId()),
				() -> assertEquals("Rua Doutor Carlos Siqueira Neto", address.getStreet()),
				() -> assertEquals("123", address.getNumber()),
				() -> assertEquals("", address.getComplement()),
				() -> assertEquals("06766200", address.getZipCode()),
				() -> assertEquals("Pq Teste", address.getNeighborhood()),
				() -> assertEquals("SP", address.getState()),
				() -> assertEquals("Taboao", address.getCity())
		);
	}
	
	/**
	 * Método responsável por validar o cenário onde é realizado o cadastro
	 * de endereços na base de dados, mas ocorre um erro ao salvar.
	 */
	@Test
	public void testSaveException() {
		doThrow(InternalServerException.class).when(addressRepository).save(getMockAddress());

		assertThrows(InternalServerException.class, () -> addressService.save(getMockAddress()));
	}
	
	/**
	 * Método responsável por validar o cenário onde é atualizado 
	 * em partes os dados de endereço
	 */
	@Test
	public void testUpdateSuccess() {
		given(addressRepository.findById(1L)).willReturn(Optional.of(getMockAddress()));
		doReturn(getMockAddress()).when(addressRepository).save(getMockAddress());

		addressService.update(1L, getMockAddress());
	}
	
	/**
	 * Método responsável por validar o cenário onde é deletador 
	 * dados de endereço
	 */
	@Test
	public void testDeleteSuccess() {
		given(addressRepository.findById(1L)).willReturn(Optional.of(getMockAddress()));
		addressService.delete(1L);

		verify(addressRepository).deleteById(1L);

	}

	private Address getMockAddress() {
		Address address = new Address();
		address.setId(1L);
		address.setStreet("Rua Doutor Carlos Siqueira Neto");
		address.setNumber("123");
		address.setComplement("");
		address.setZipCode("06766200");
		address.setNeighborhood("Pq Teste");
		address.setCity("Taboao");
		address.setState("SP");
		address.setUser(UserMock.getMockUserDto());
		
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
		
		return List.of(address);
	}
}
