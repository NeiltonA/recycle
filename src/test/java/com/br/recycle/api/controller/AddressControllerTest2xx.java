package com.br.recycle.api.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.br.recycle.api.assembler.AddressDtoAssembler;
import com.br.recycle.api.mock.AddressMock;
import com.br.recycle.api.payload.AddressDtoOut;
import com.br.recycle.api.payload.Dictionary;
import com.br.recycle.api.service.AddressService;

/**
 * Método responsável por validar os testes da classe de 
 * controller de sucesso do endereço.
 *
 */
@ExtendWith(SpringExtension.class)
public class AddressControllerTest2xx {

	@Mock
	private AddressService addressService;
	
	@Mock
	private AddressDtoAssembler addressDtoAssembler;
	
	@InjectMocks
	private AddressController addressController = new AddressController(addressService, addressDtoAssembler);
	
	@Test
	public void testGetAllSuccess() {
		given(addressService.findAll(null)).willReturn(AddressMock.getMockCollectionAddress());
		given(addressDtoAssembler.toCollectionModel(AddressMock.getMockCollectionAddress()))
			.willReturn(getMockAddreses());
		
		List<AddressDtoOut> addresses = addressController.getAll(null);
		assertNotNull(addresses);
	} 
	
	@Test
	public void testGetAllByIdSuccess() {
		given(addressService.findAll(1L)).willReturn(AddressMock.getMockCollectionAddress());
		given(addressDtoAssembler.toCollectionModel(AddressMock.getMockCollectionAddress()))
			.willReturn(getMockAddreses());
		
		List<AddressDtoOut> addresses = addressController.getAll(1L);
		assertNotNull(addresses);
	}
	
	@Test
	public void testGetByIdSuccess() {
		given(addressService.findOrFail(1L)).willReturn(AddressMock.getMockAddress());
		given(addressDtoAssembler.toModel(AddressMock.getMockAddress()))
			.willReturn(getMockAddres());
		
		List<AddressDtoOut> addresses = addressController.getAll(1L);
		assertNotNull(addresses);
	}
	
	@Test
	public void testGetZipCodeSuccess() {
		given(addressService.searchAddress("06766200")).willReturn(AddressMock.getMockAddressBean());
		given(addressDtoAssembler.toDictionary(AddressMock.getMockAddressBean())).willReturn(getMockDictionary());
		
		ResponseEntity<Dictionary> dictionary = addressController.getZipCode("06766200");
		assertEquals(200, dictionary.getStatusCode().value());
	}
	
	private Dictionary getMockDictionary() {
		Dictionary dictionary = new Dictionary();
		dictionary.setState("SP");
		
		return dictionary;
	}

	@Test
	public void testDeleteSuccess() {
		addressController.delete(1L);
		verify(addressService, times(1)).deleteById(1L);
		
		assertEquals(204, HttpStatus.NO_CONTENT.value());
	}

	private AddressDtoOut getMockAddres() {
		AddressDtoOut addressDtoOut = new AddressDtoOut();
        addressDtoOut.setStreet("Rua Doutor Teste");
        addressDtoOut.setNumber("123");
        addressDtoOut.setComplement("AP123");
        addressDtoOut.setZipCode("06766300");
        addressDtoOut.setNeighborhood("Jundiai");
        addressDtoOut.setState("SP");
        addressDtoOut.setCity("São Paulo");

        return addressDtoOut;
	}
	
	private List<AddressDtoOut> getMockAddreses() {
		AddressDtoOut addressDtoOut = new AddressDtoOut();
        addressDtoOut.setStreet("Rua Doutor Teste");
        addressDtoOut.setNumber("123");
        addressDtoOut.setComplement("AP123");
        addressDtoOut.setZipCode("06766300");
        addressDtoOut.setNeighborhood("Jundiai");
        addressDtoOut.setState("SP");
        addressDtoOut.setCity("São Paulo");

        AddressDtoOut addressDtoOut2 = new AddressDtoOut();
        addressDtoOut2.setStreet("Rua Carlos Teste");
        addressDtoOut2.setNumber("456");
        addressDtoOut2.setZipCode("05680200");
        addressDtoOut2.setNeighborhood("Pq Piramaba");
        addressDtoOut2.setState("RJ");
        addressDtoOut2.setCity("Rio de Janeiro");

        return List.of(addressDtoOut, addressDtoOut2);
	}
}
