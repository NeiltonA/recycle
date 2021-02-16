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

	@ApiOperation(value = "Method responsible for returning the list of giver")
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Giver>> getAll() {
		try {
			List<Giver> giver = repository.findAll();

			if (giver.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(giver, HttpStatus.OK);
		} catch (Exception e) {
			throw new NegocioException(e.getMessage(), e);
		}

	}

	@ApiOperation(value = "Method responsible for searching by ID")
	@GetMapping(value = "/{idGiver}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Giver> getById(@PathVariable("idGiver") Long idGiver) {
		try {
			Optional<Giver> giver = repository.findById(idGiver);

			if (giver.isPresent()) {
				return new ResponseEntity<>(giver.get(), HttpStatus.OK);
			}
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}

	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	@ApiOperation(value = "Method responsible for saving the giver")
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Giver> save(@RequestBody @Valid Giver giver) {
		try {
			Giver giv = service.save(giver);
			log.info("Registered successfully -> []");
			return new ResponseEntity<>(giv, HttpStatus.CREATED);
		} catch (Exception e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}

	@ApiOperation(value = "Method responsible for changing the giver")
	@PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Giver> update(@PathVariable("id") long id, @RequestBody Giver giver) {
		try {
			Optional<Giver> giv = repository.findById(id);
			if (giv.isPresent()) {
				giver.setId(giv.get().getId());
				return new ResponseEntity<>(service.save(giver), HttpStatus.OK);
			}
			return ResponseEntity.notFound().build();
		} catch (Exception e) {
			throw new NegocioException(e.getMessage(), e);
		}

	}

	@ApiOperation(value = "Method responsible for excluding the giver")
	@DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<HttpStatus> delete(@PathVariable("id") long id) {
		try {
			Optional<Giver> giv = repository.findById(id);
			if (giv.isPresent()) {
				repository.deleteById(id);
				return new ResponseEntity<>(HttpStatus.OK);
			}
			return ResponseEntity.notFound().build();
		} catch (Exception e) {
			throw new NegocioException(e.getMessage(), e);
		}

	}

}
