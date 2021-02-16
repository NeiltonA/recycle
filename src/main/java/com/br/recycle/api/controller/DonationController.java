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
import com.br.recycle.api.model.Donation;
import com.br.recycle.api.repository.DonationRepository;
import com.br.recycle.api.service.DonationService;

import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping("/api/v1/donation")
public class DonationController {

	@Autowired
	private DonationRepository repository;

	@Autowired
	private DonationService service;

	@ApiOperation(value = "Method responsible for returning the list of donation")
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Donation>> getAll() {
		try {
			List<Donation> donation = repository.findAll();

			if (donation.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(donation, HttpStatus.OK);
		} catch (Exception e) {
			throw new NegocioException(e.getMessage(), e);
		}

	}

	@ApiOperation(value = "Method responsible for searching by ID")
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Donation> getById(@PathVariable("id") long id) {
		try {
			Optional<Donation> donation = repository.findById(id);

			if (donation.isPresent()) {
				return new ResponseEntity<>(donation.get(), HttpStatus.OK);
			}
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}

	@ApiOperation(value = "Method responsible for saving the donation")
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Donation> save(@Valid @RequestBody Donation donation) {
		try {
			Donation don = service.save(donation);
			log.info("Registered successfully -> []");
			return new ResponseEntity<>(don, HttpStatus.CREATED);
		} catch (Exception e) {
			log.error("failed to register -> [] ", e);
			throw new NegocioException(e.getMessage(), e);
		}
	}

	@ApiOperation(value = "Method responsible for changing the donation")
	@PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Donation> update(@PathVariable("id") long id, @RequestBody Donation donation) {
		try {
			Optional<Donation> don = repository.findById(id);
			if (don.isPresent()) {
				donation.setId(don.get().getId());
				return new ResponseEntity<>(service.save(donation), HttpStatus.OK);
			}
			return ResponseEntity.notFound().build();

		} catch (Exception e) {
			throw new NegocioException(e.getMessage(), e);
		}

	}

	@ApiOperation(value = "Method responsible for excluding the donation")
	@DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<HttpStatus> delete(@PathVariable("id") long id) {
		try {
			Optional<Donation> donation = repository.findById(id);
			if (donation.isPresent()) {
				service.excluir(id);
				return new ResponseEntity<>(HttpStatus.OK);
			}
			return ResponseEntity.notFound().build();
		} catch (Exception e) {
			throw new NegocioException(e.getMessage(), e);
		}

	}

}
