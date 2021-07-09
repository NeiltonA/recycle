package com.br.recycle.api.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.br.recycle.api.assembler.AddressDtoAssembler;
import com.br.recycle.api.bean.AddressResponseBean;
import com.br.recycle.api.commons.UriConstants;
import com.br.recycle.api.exception.BusinessException;
import com.br.recycle.api.model.Address;
import com.br.recycle.api.payload.AddressDtoOut;
import com.br.recycle.api.payload.AddressInput;
import com.br.recycle.api.payload.AddressPartialInput;
import com.br.recycle.api.payload.ApiResponse;
import com.br.recycle.api.repository.AddressRepository;
import com.br.recycle.api.service.AddressService;
import com.br.recycle.api.util.Dictionary;
import com.br.recycle.api.validation.AddressValidation;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * Classe responsável por ser a Contreller e conter o Endpoint de endereços da
 * aplicação.
 */
@RestController
@RequestMapping(UriConstants.URI_BASE_ACCESS)
@Api(value = "Address", description = "REST API for Address", tags = { "Address" })
public class AddressController {

	private AddressRepository addressRepository;
	private AddressService addressService;
	private AddressDtoAssembler addressDtoAssembler;

	@Autowired
	public AddressController(AddressRepository addressRepository, AddressService addressService,
			AddressDtoAssembler addressDtoAssembler) {
		this.addressRepository = addressRepository;
		this.addressService = addressService;
		this.addressDtoAssembler = addressDtoAssembler;
	}

	/**
	 * Método responsável por conter o endpoint que busca todos os endereços na base
	 * de dados.
	 * 
	 * @return {@code List<AddressDtoOut} - Retorna uma lista de endereços.
	 */
	// PreAuthorize("hasRole('USER')")
	@ApiOperation(value = "Method responsible for returning the list of addresses")
	@GetMapping
	public List<AddressDtoOut> getAll(@RequestParam(required = false) Long user) {

		List<Address> addresses = addressService.findAll(user);

		return addressDtoAssembler.toCollectionModel(addresses);
	}

	/**
	 * Método responsável por conter o endpoint que busca o endereço por CEP.
	 * 
	 * @param {@code String} zipCode
	 * @return {@code ResponseEntity<Dictionary} - Retorna os dados de endereço de
	 *         acordo com o CEP
	 */
	// @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	@ApiOperation(value = "Method responsible for returning the address via Zip Code")
	@GetMapping(value = UriConstants.URI_ACCESS_ZIPCODE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Dictionary> getZipCode(@PathVariable String zipCode) {

		AddressValidation.validate(zipCode);
		AddressResponseBean addressResponseBean = addressService.searchAddress(zipCode);
		Dictionary dictionary = addressDtoAssembler.toDictionary(addressResponseBean);
		
		return ResponseEntity.ok().body(dictionary);
	}

	// @PreAuthorize("hasRole('USER')")
	@ApiOperation(value = "Method responsible for searching the address by ID")
	@GetMapping(value = UriConstants.URI_ACCESS_ID, produces = MediaType.APPLICATION_JSON_VALUE)
	public AddressDtoOut getById(@PathVariable("id") long id) {
		try {
			Address add = addressService.findOrFail(id);
			return addressDtoAssembler.toModel(add);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	// @PreAuthorize("hasRole('USER')")
	@ApiOperation(value = "Method responsible for saving the address")
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ApiResponse> save(@Valid @RequestBody AddressInput address) {
		try {
			Address add = addressDtoAssembler.toDomainObject(address);

			addressService.save(add);
			return ResponseEntity.ok(new ApiResponse(true, "Address registrada com sucesso."));
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	// @PreAuthorize("hasRole('USER')")
	@ApiOperation(value = "Method responsible for changing the address")
	@PatchMapping(value = UriConstants.URI_ACCESS_ID, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ApiResponse> update(@PathVariable("id") Long id,
			@RequestBody AddressPartialInput addressPartialInput) {

		Address address = addressDtoAssembler.toDomainPartialObject(addressPartialInput);
		addressService.updatePartial(address, id);

		return ResponseEntity.ok(new ApiResponse(true, "Endereço alterado com sucesso."));

	}

	// @PreAuthorize("hasRole('USER')")
	@ApiOperation(value = "Method responsible for changing the address")
	@PutMapping(value = UriConstants.URI_ACCESS_ID, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ApiResponse> update(@PathVariable("id") Long id, @RequestBody AddressInput addressInput) {

		Address address = addressDtoAssembler.toDomainObject(addressInput);
		addressService.update(address, id);

		return ResponseEntity.ok(new ApiResponse(true, "Endereço alterado com sucesso."));

	}

	// @PreAuthorize("hasRole('USER')")
	@ApiOperation(value = "Method responsible for removing the address")
	@DeleteMapping(value = UriConstants.URI_ACCESS_ID, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<HttpStatus> delete(@PathVariable("id") long id) {
		try {
			Optional<Address> add = addressRepository.findById(id);
			if (add.isPresent()) {
				addressRepository.deleteById(id);
				return new ResponseEntity<>(HttpStatus.OK);
			} else {
				return ResponseEntity.notFound().build();
			}
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}

	}
}
