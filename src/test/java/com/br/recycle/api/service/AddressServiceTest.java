package com.br.recycle.api.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.br.recycle.api.exception.AddressNotFoundException;
import com.br.recycle.api.model.Address;
import com.br.recycle.api.model.User;
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
		given(addressRepository.findAll()).willReturn(getMockAddress());
		
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
	 * de todos os emdereços na base de dados e retorna uma lista vazia.
	 */
	@Test
	public void testFindAllNoContent() {
		given(addressRepository.findAll()).willReturn(Collections.emptyList());

		assertThrows(AddressNotFoundException.class, () -> addressService.findAll(null));
	}

	private List<Address> getMockAddress() {
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
