package com.br.recycle.api.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.br.recycle.api.bean.AddressResponseBean;
import com.br.recycle.api.exception.NegocioException;
import com.br.recycle.api.feign.ViaZipCodeClient;
import com.br.recycle.api.model.Address;
import com.br.recycle.api.repository.AddressRepository;
import com.br.recycle.api.service.AddressService;
import com.br.recycle.api.util.Dictionary;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;


@Log4j2
@RestController
@RequestMapping("/api/v1/address")
@Api(value = "Address", description = "REST API for Address", tags = { "Address" })
public class AddressController {

	@Autowired
	private AddressRepository repository;

	@Autowired
	private AddressService addressService;

	@Autowired
	private ViaZipCodeClient service;

	@ApiOperation(value = "Method responsible for returning the list of address")
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")																				
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Address>> getAll() {
		try {
			List<Address> address = repository.findAll();

			if (address.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(address, HttpStatus.OK);
		} catch (Exception e) {
			throw new NegocioException(e.getMessage(), e);
		}

	}

	@ApiOperation(value = "method responsible for returning the address via zip code")
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	@GetMapping(value = "/zip_code/{zipCode}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Dictionary> getZipCode(@PathVariable String zipCode) throws Exception {
		try {
			AddressResponseBean bean = service.searchAddress(zipCode);
			Dictionary dic = new Dictionary();
			dic.setZipCode(bean.getCep());
			dic.setStreet(bean.getLogradouro());
			dic.setComplement(bean.getComplemento());
			dic.setNeighborhood(bean.getBairro());
			dic.setCity(bean.getLocalidade());
			dic.setState(bean.getUf());
			return dic.getZipCode() != null ? ResponseEntity.ok().body(dic) : ResponseEntity.noContent().build();
		} catch (Exception e) {
			log.error("Error querying zip code -> [] ", e);
			throw new NegocioException(e.getMessage(), e);
		}

	}
	
	@ApiOperation(value = "Method responsible for searching by ID")
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Address> getById(@PathVariable("id") long id) {
		try {
			Optional<Address> add = repository.findById(id);

			if (add.isPresent()) {
				return new ResponseEntity<>(add.get(), HttpStatus.OK);
			}
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	@ApiOperation(value = "Method responsible for saving the address")
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Address> save(@Valid @RequestBody Address address) {
		try {
			Address add = addressService.save(address);

			return new ResponseEntity<>(add, HttpStatus.CREATED);
		} catch (Exception e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	@ApiOperation(value = "Method responsible for changing the address") 
	@PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Address> update(@PathVariable("id") Long id, @RequestBody Address address) {
		try {
			Optional<Address> add = repository.findById(id);
			if (add.isPresent()) {
				address.setId(add.get().getId());
				return new ResponseEntity<>(addressService.save(address), HttpStatus.OK);
			}
			return ResponseEntity.notFound().build();

		} catch (Exception e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}

	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	@ApiOperation(value = "Method responsible for excluding the address")
	@DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<HttpStatus> delete(@PathVariable("id") long id) {
		try {
			Optional<Address> add = repository.findById(id);
			if (add.isPresent()) {
				repository.deleteById(id);
				return new ResponseEntity<>(HttpStatus.OK);
			} else {
				return ResponseEntity.notFound().build();
			}
		} catch (Exception e) {
			throw new NegocioException(e.getMessage(), e);
		}

	}
}
