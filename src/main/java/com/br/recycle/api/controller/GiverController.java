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

import com.br.recycle.api.bean.GiverResponseBean;
import com.br.recycle.api.exception.GiverNaoEncontradaException;
import com.br.recycle.api.exception.NegocioException;
import com.br.recycle.api.model.Giver;
import com.br.recycle.api.repository.GiverRepository;
import com.br.recycle.api.service.GiverService;

import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping("/api/v1/giver")
public class GiverController {

	@Autowired
	private GiverRepository repository;
	
	@Autowired
	private GiverService service;
	
	@ApiOperation(value = "Method responsible for returning the list of giver")//Método responsável pelo retorno da lista de doadores
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<GiverResponseBean>> getAll() {
		try {
			List<GiverResponseBean> giver = service.consultByAll();

			if (giver.isEmpty()) {

				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(giver, HttpStatus.OK);
		} catch (Exception e) {
			throw new NegocioException(e.getMessage(), e);
		}

	}

	@ApiOperation(value = "Method responsible for searching by ID")//Método responsável pela busca por ID
	@GetMapping(value="/consult/{idGiver}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Giver> getById(@PathVariable("idGiver") Long idGiver) {
		Optional<Giver> giver = repository.findById(idGiver);

		if (giver.isPresent()) {
			return new ResponseEntity<>(giver.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	@ApiOperation(value = "Method responsible for saving the giver")//Método responsável por salvar o doador
	@PostMapping(value = "/save", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Giver> save(@RequestBody @Valid Giver giver) {
		try {
			Giver giv = service.save(giver);
			log.info("Registered successfully -> []");
			return new ResponseEntity<>(giv, HttpStatus.CREATED);
		} catch (GiverNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}

	@ApiOperation(value = "Method responsible for changing the giver")//Método responsável por alterar o doador
	@PutMapping(value = "/update/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Giver> update(@PathVariable("id") long id, @RequestBody Giver giver) {
		Optional<Giver> giv = repository.findById(id);
		if (giv.isPresent()) {
			giver.setId(giv.get().getId());
			return new ResponseEntity<>(repository.save(giver), HttpStatus.OK);
		} else {
			return ResponseEntity.notFound().build();

		}

	}

	@ApiOperation(value = "Method responsible for excluding the giver")//Método responsável pela exclusão do doador
	@DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<HttpStatus> deleteFunc(@PathVariable("id") long id) {
		try {
			Optional<Giver> giv = repository.findById(id);
			if (giv.isPresent()) {
				repository.deleteById(id);
				return new ResponseEntity<>(HttpStatus.OK);
			}else {
				return ResponseEntity.notFound().build();
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}


}
