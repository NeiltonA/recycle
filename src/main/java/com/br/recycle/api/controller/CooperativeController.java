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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.br.recycle.api.exception.NegocioException;
import com.br.recycle.api.model.Cooperative;
import com.br.recycle.api.repository.CooperativeRepository;
import com.br.recycle.api.service.CooperativeService;

import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping("/api/v1/cooperative")
public class CooperativeController {

	@Autowired
	private CooperativeRepository repository;
	
	@Autowired
	private CooperativeService service;
	
	@ApiOperation(value = "Method responsible for returning the list of cooperative")//Método responsável pelo retorno da lista de doadores
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Cooperative>> getAll() {
		try {
			List<Cooperative> cooperative = repository.findAll();

			if (cooperative.isEmpty()) {

				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(cooperative, HttpStatus.OK);
		} catch (Exception e) {
			throw new NegocioException(e.getMessage(), e);
		}

	}

	@ApiOperation(value = "Method responsible for searching by ID")//Método responsável pela busca por ID
	@GetMapping(value="/consult/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Cooperative> getById(@PathVariable("id") long id) {
		Optional<Cooperative> coop = repository.findById(id);

		if (coop.isPresent()) {
			return new ResponseEntity<>(coop.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@ApiOperation(value = "Method responsible for saving the cooperative")//Método responsável por salvar o cooperative
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Cooperative> save(@Valid @RequestBody Cooperative giver) {
		try {
			Cooperative coop = service.save(giver);
			log.info("Registered successfully -> []");
			return new ResponseEntity<>(coop, HttpStatus.CREATED);
		} catch (Exception e) {
			log.error("failed to register -> [] ", e);
			throw new NegocioException(e.getMessage(), e);
		}
	}

	@ApiOperation(value = "Method responsible for changing the cooperative")//Método responsável por alterar o cooperative
	@PutMapping(value = "/update/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Cooperative> update(@PathVariable("id") long id, @RequestBody Cooperative giver) {
		Optional<Cooperative> coop = repository.findById(id);
		if (coop.isPresent()) {
			//employee.setId(id);
			return new ResponseEntity<>(repository.save(giver), HttpStatus.OK);
		} else {
			return ResponseEntity.notFound().build();

		}

	}

	@ApiOperation(value = "Method responsible for excluding the cooperative")//Método responsável pela exclusão do cooperative
	@DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<HttpStatus> deleteFunc(@PathVariable("id") long id) {
		try {
			Optional<Cooperative> coop = repository.findById(id);
			if (coop.isPresent()) {
				repository.deleteById(id);
				return new ResponseEntity<>(HttpStatus.OK);
			}else {
				return ResponseEntity.notFound().build();
			}
		} catch (Exception e) {
			throw new NegocioException(e.getMessage(), e);
		}

	}


}
