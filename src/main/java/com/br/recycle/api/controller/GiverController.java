package com.br.recycle.api.controller;

import java.net.URI;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.br.recycle.api.assembler.GiverDtoAssembler;
import com.br.recycle.api.exception.BusinessException;
import com.br.recycle.api.model.Giver;
import com.br.recycle.api.payload.ApiResponse;
import com.br.recycle.api.payload.GiverDtoOut;
import com.br.recycle.api.repository.GiverRepository;
import com.br.recycle.api.service.GiverService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping("/api/v1/giver")
@Api(value = "Giver", description = "REST API for Flow Giver", tags = { "Giver" })
public class GiverController {

	@Autowired
	private GiverRepository repository;

	@Autowired
	private GiverDtoAssembler giverDtoAssembler;
	
	@Autowired
	private GiverService service;

	@ApiOperation(value = "Method responsible for returning the list of givers")
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<GiverDtoOut> getAll() {
		try {
			List<Giver> givers = repository.findAll();
			return giverDtoAssembler.toCollectionModel(givers);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@ApiOperation(value = "Method responsible for searching the giver by ID")
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public GiverDtoOut getById(@PathVariable("id") Long id) {
		try {
			Giver giver = service.buscarOuFalhar(id);
			return giverDtoAssembler.toModel(giver);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	@ApiOperation(value = "Method responsible for saving the giver")
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ApiResponse> save(@RequestBody @Valid Giver giver) {
		try {
			service.save(giver);
			log.info("Registered successfully -> []");
		        return ResponseEntity.created(URI.create("")).body(new ApiResponse(true, "Giver registered successfully"));
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}


	@ApiOperation(value = "Method responsible for removing the giver")
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
			throw new BusinessException(e.getMessage(), e);
		}
	}
}
