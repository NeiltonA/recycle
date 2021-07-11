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

import com.br.recycle.api.assembler.DonationDtoAssembler;
import com.br.recycle.api.commons.UriConstants;
import com.br.recycle.api.model.Donation;
import com.br.recycle.api.payload.ApiResponse;
import com.br.recycle.api.payload.DonationDtoOut;
import com.br.recycle.api.payload.DonationInput;
import com.br.recycle.api.service.DonationService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping(UriConstants.URI_BASE_DONATION)
@Api(value = "Donation", description = "REST API for Donation", tags = { "Donation" })
public class DonationController {

	private DonationService donationService;
	private DonationDtoAssembler donationDtoAssembler;

	@Autowired
	public DonationController(DonationService donationService, DonationDtoAssembler donationDtoAssembler) {
		this.donationService = donationService;
		this.donationDtoAssembler = donationDtoAssembler;
	}

	/**
	 * Método responsável por conter o endpoint que busca todos os dados da doação
	 * na base de dados.
	 * 
	 * @return {@code List<DonationDtoOut>} - Retorna a lista de dados da doçãoa.
	 */
	@ApiOperation(value = "Method responsible for returning the list of donations")
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public List<DonationDtoOut> getAll() {
		List<Donation> donations = donationService.findAll();

		return donationDtoAssembler.toCollectionModel(donations);
	}

	/**
	 * Método responsável por conter o endpoint que busca os dados da doação por ID
	 * na base de dados.
	 * 
	 * @param {@code Long} - id
	 * @return {@code DonationDtoOut} - Retorna os dados da doação por ID
	 */
	@ApiOperation(value = "Method responsible for searching the donation by ID")
	@GetMapping(value = UriConstants.URI_DONATION_ID, produces = MediaType.APPLICATION_JSON_VALUE)
	public DonationDtoOut getById(@PathVariable("id") Long id) {
		Donation donation = donationService.findOrFail(id);

		return donationDtoAssembler.toModel(donation);
	}

	/**
	 * Método responsável por conter o endpoint que salva os dados da doação na base
	 * de dados.
	 * 
	 * @param {@code DonationInput} newDonation
	 * @return {@code ResponseEntity<ApiResponse>} - Retorna uma resposta de
	 *         sucesso.
	 */
	@ApiOperation(value = "Method responsible for saving the donation")
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ApiResponse> save(@Valid @RequestBody DonationInput newDonation) {

		Donation donation = donationDtoAssembler.toDomainObject(newDonation);
		donationService.save(donation);

		log.info("Registered successfully -> []");
		return ResponseEntity.created(URI.create("")).body(new ApiResponse(true, "Doação registrada com sucesso."));
	}

	/**
	 * Método responsável por conter o endpoint que atualiza na base de dados os
	 * dados da doação.
	 * 
	 * @param {@code DonationInput} newDonation
	 * @return {@code ResponseEntity<ApiResponse>} - Retorna uma resposta de
	 *         sucesso.
	 */
	@ApiOperation(value = "Method responsible for updating the donation")
	@PutMapping(value = UriConstants.URI_DONATION_ID, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ApiResponse> update(@PathVariable("id") Long id, @RequestBody DonationInput newDonation) {

		Donation donation = donationDtoAssembler.toDomainObject(newDonation);
		donationService.update(id, donation);

		return ResponseEntity.ok(new ApiResponse(true, "Doação modificada com sucesso."));
	}

	/**
	 * Método responsável por conter o endpoint que atualiza na base de dados os
	 * dados da doação.
	 * 
	 * @param {@code Long} - id
	 * @return {@code ResponseEntity<ApiResponse>} - Retorna uma resposta de
	 *         sucesso.
	 */
	@ApiOperation(value = "Method responsible for removing the donation")
	@DeleteMapping(value = UriConstants.URI_DONATION_ID, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> delete(@PathVariable("id") Long id) {

		donationService.remove(id);
		return ResponseEntity.noContent().build();
	}
}
