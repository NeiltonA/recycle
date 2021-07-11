package com.br.recycle.api.controller;

import java.net.URI;
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
import org.springframework.web.bind.annotation.RestController;

import com.br.recycle.api.assembler.RateDtoAssembler;
import com.br.recycle.api.commons.UriConstants;
import com.br.recycle.api.exception.BusinessException;
import com.br.recycle.api.model.Rate;
import com.br.recycle.api.payload.ApiResponse;
import com.br.recycle.api.payload.RateDtoOut;
import com.br.recycle.api.payload.RateInput;
import com.br.recycle.api.repository.RateRepository;
import com.br.recycle.api.service.RateService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;

/**
 * Classe responsável por ser a Contreller e conter o Endpoint de avaliação da
 * aplicação.
 */
@Log4j2
@RestController
@RequestMapping(UriConstants.URI_BASE_RATE)
@Api(value = "Rate", description = "REST API for Rate", tags = { "Rate" })
public class RateController {

	private RateRepository rateRepository;
	private RateService rateService;
	private RateDtoAssembler rateDtoAssembler;

	@Autowired
	public RateController(RateRepository rateRepository, RateService rateService, RateDtoAssembler rateDtoAssembler) {
		this.rateRepository = rateRepository;
		this.rateService = rateService;
		this.rateDtoAssembler = rateDtoAssembler;
	}
	
	/**
	 * Método responsável por conter o endpoint que busca todos as avaliações na base
	 * de dados.
	 * 
	 * @return {@code List<RateDtoOut} - Retorna uma lista de avaliações.
	 */
	@ApiOperation(value = "Method responsible for returning the list of rates")
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public List<RateDtoOut> getAll() {

		List<Rate> rates = rateService.findAll();

		return rateDtoAssembler.toCollectionModel(rates);
	}

	@ApiOperation(value = "Method responsible for searching the rating by ID")
	@GetMapping(value = UriConstants.URI_RATE_ID, produces = MediaType.APPLICATION_JSON_VALUE)
	public RateDtoOut getById(@PathVariable("id") Long id) {
		try {
			Rate rate = rateService.buscarOuFalhar(id);
			return rateDtoAssembler.toModel(rate);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@ApiOperation(value = "Method responsible for saving the rate")
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ApiResponse> save(@Valid @RequestBody RateInput rate) {
		try {
			Rate rat = rateDtoAssembler.toDomainObject(rate);
			rateService.save(rat);
			log.info("Registered successfully -> []");
			return ResponseEntity.created(URI.create(""))
					.body(new ApiResponse(true, "Avaliação registrada com sucesso!"));
		} catch (Exception e) {
			log.error("Failed to register -> [] ", e);
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@ApiOperation(value = "Method responsible for updating the rate")
	@PutMapping(value = UriConstants.URI_RATE_ID, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ApiResponse> update(@PathVariable("id") Long id, @RequestBody RateInput rate) {
		try {

			Rate rat = rateDtoAssembler.toDomainObject(rate);
			Optional<Rate> ra = rateRepository.findById(id);
			if (ra.isPresent()) {
				rat.setId(ra.get().getId());
				rateRepository.save(rat);
				return ResponseEntity.ok(new ApiResponse(true, "Avaliação modificada com sucesso!"));
			}
			return ResponseEntity.notFound().build();
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@ApiOperation(value = "Method responsible for removing the rate")
	@DeleteMapping(value = UriConstants.URI_RATE_ID, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<HttpStatus> delete(@PathVariable("id") Long id) {
		try {
			Optional<Rate> rat = rateRepository.findById(id);
			if (rat.isPresent()) {
				rateRepository.deleteById(id);
				return new ResponseEntity<>(HttpStatus.OK);
			}
			return ResponseEntity.notFound().build();
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}

	}

}
