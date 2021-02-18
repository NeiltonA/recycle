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
import com.br.recycle.api.model.Rate;
import com.br.recycle.api.repository.RateRepository;
import com.br.recycle.api.service.RateService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping("/api/v1/rate")
@Api(value = "Rate", description = "REST API for Rate", tags = { "Rate" })
public class RateController {

	@Autowired
	private RateRepository repository;

	@Autowired
	private RateService service;

	@ApiOperation(value = "Method responsible for returning the list of rate")
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Rate>> getAll() {
		try {
			List<Rate> rate = repository.findAll();

			if (rate.isEmpty()) {

				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(rate, HttpStatus.OK);
		} catch (Exception e) {
			throw new NegocioException(e.getMessage(), e);
		}

	}

	@ApiOperation(value = "Method responsible for searching by ID")
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Rate> getById(@PathVariable("id") long id) {
		try {
			Optional<Rate> rate = repository.findById(id);

			if (rate.isPresent()) {
				return new ResponseEntity<>(rate.get(), HttpStatus.OK);
			}
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}

	@ApiOperation(value = "Method responsible for saving the rate")
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Rate> save(@Valid @RequestBody Rate rate) {
		try {
			Rate rat = service.save(rate);
			log.info("Registered successfully -> []");
			return new ResponseEntity<>(rat, HttpStatus.CREATED);
		} catch (Exception e) {
			log.error("failed to register -> [] ", e);
			throw new NegocioException(e.getMessage(), e);
		}
	}

	@ApiOperation(value = "Method responsible for changing the rate")
	@PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Rate> update(@PathVariable("id") long id, @RequestBody Rate rate) {
		try {
			Optional<Rate> rat = repository.findById(id);
			if (rat.isPresent()) {
				rate.setId(rat.get().getId());
				return new ResponseEntity<>(service.save(rate), HttpStatus.OK);
			}

			return ResponseEntity.notFound().build();
		} catch (Exception e) {
			throw new NegocioException(e.getMessage(), e);
		}

	}

	@ApiOperation(value = "Method responsible for excluding the rate")
	@DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<HttpStatus> delete(@PathVariable("id") long id) {
		try {
			Optional<Rate> rat = repository.findById(id);
			if (rat.isPresent()) {
				repository.deleteById(id);
				return new ResponseEntity<>(HttpStatus.OK);
			}
			return ResponseEntity.notFound().build();
		} catch (Exception e) {
			throw new NegocioException(e.getMessage(), e);
		}

	}

}
