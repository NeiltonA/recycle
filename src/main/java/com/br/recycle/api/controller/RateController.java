package com.br.recycle.api.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.br.recycle.api.model.Rate;
import com.br.recycle.api.payload.ApiResponse;
import com.br.recycle.api.payload.RateDtoOut;
import com.br.recycle.api.payload.RateInput;
import com.br.recycle.api.service.RateService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * Classe responsável por ser a Contreller e conter o Endpoint de avaliação da
 * aplicação.
 */
@RestController
@RequestMapping(UriConstants.URI_BASE_RATE)
@Api(value = "Rate", description = "REST API for Rate", tags = { "Rate" })
public class RateController {

	private RateService rateService;
	private RateDtoAssembler rateDtoAssembler;

	@Autowired
	public RateController(RateService rateService, RateDtoAssembler rateDtoAssembler) {
		this.rateService = rateService;
		this.rateDtoAssembler = rateDtoAssembler;
	}

	/**
	 * Método responsável por conter o endpoint que busca todos as avaliações na
	 * base de dados.
	 * 
	 * @return {@code List<RateDtoOut} - Retorna uma lista de avaliações.
	 */
	@ApiOperation(value = "Method responsible for returning the list of rates")
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public List<RateDtoOut> getAll() {

		List<Rate> rates = rateService.findAll();

		return rateDtoAssembler.toCollectionModel(rates);
	}

	/**
	 * Método responsável por conter o endpoint que busca as avaliações por ID na
	 * base de dados.
	 * 
	 * @param {@code Long} - id
	 * @return {@code RateDtoOut} - Retorna as avaliações por ID.
	 */
	@ApiOperation(value = "Method responsible for searching the rating by ID")
	@GetMapping(value = UriConstants.URI_RATE_ID, produces = MediaType.APPLICATION_JSON_VALUE)
	public RateDtoOut getById(@PathVariable("id") Long id) {

		Rate rate = rateService.findOrFail(id);

		return rateDtoAssembler.toModel(rate);
	}

	/**
	 * Método responsável por conter o endpoint que salva as avaliações na base de
	 * dados.
	 * 
	 * @param {@code RateInput} - rateInput
	 * @return {@code ResponseEntity<ApiResponse>} - Retorna que a avaliação foi
	 *         salva.
	 */
	@ApiOperation(value = "Method responsible for saving the rate")
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ApiResponse> save(@Valid @RequestBody RateInput rateInput) {

		Rate rate = rateDtoAssembler.toDomainObject(rateInput);
		rateService.save(rate);

		return ResponseEntity.created(URI.create("")).body(new ApiResponse(true, "Avaliação registrada com sucesso!"));
	}

	/**
	 * Método responsável por conter o endpoint que atualiza as avaliações na base
	 * de dados.
	 * 
	 * @param {@code Long} - id
	 * @param {@code RateInput} - rate
	 * @return {@code ResponseEntity<ApiResponse>} - Retorna que a avaliação foi
	 *         alterada.
	 */
	@ApiOperation(value = "Method responsible for updating the rate")
	@PutMapping(value = UriConstants.URI_RATE_ID, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ApiResponse> update(@PathVariable("id") Long id, @Valid @RequestBody RateInput rateInput) {

		Rate rate = rateDtoAssembler.toDomainObject(rateInput);
		rateService.update(id, rate);

		return ResponseEntity.ok(new ApiResponse(true, "Avaliação modificada com sucesso!"));
	}

	/**
	 * Método responsável por conter o endpoint que deleta uma avaliação por ID.
	 * 
	 * @param {@code Long} id
	 * @return {@code ResponseEntity<Void>} - Retorna uma resposta de sucesso.
	 */
	@ApiOperation(value = "Method responsible for removing the rate")
	@DeleteMapping(value = UriConstants.URI_RATE_ID, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> delete(@PathVariable("id") Long id) {

		rateService.delete(id);

		return ResponseEntity.noContent().build();
	}
}
