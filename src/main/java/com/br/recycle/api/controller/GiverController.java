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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.br.recycle.api.assembler.GiverDtoAssembler;
import com.br.recycle.api.commons.UriConstants;
import com.br.recycle.api.model.Giver;
import com.br.recycle.api.payload.ApiResponse;
import com.br.recycle.api.payload.GiverDtoOut;
import com.br.recycle.api.payload.GiverRequest;
import com.br.recycle.api.service.GiverService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;

/**
 * Classe responsável por ser a Contreller e conter do doador da aplicação.
 */
@Log4j2
@RestController
@RequestMapping(UriConstants.URI_BASE_GIVER)
@Api(value = "Giver", description = "REST API for Flow Giver", tags = { "Giver" })
public class GiverController {

	private GiverDtoAssembler giverDtoAssembler;
	private GiverService giverService;

	@Autowired
	public GiverController(GiverDtoAssembler giverDtoAssembler, GiverService giverService) {
		this.giverDtoAssembler = giverDtoAssembler;
		this.giverService = giverService;
	}

	/**
	 * Método responsável por conter o endpoint que busca todos os doadores na base
	 * de dados.
	 * 
	 * @return {@code List<GiverDtoOut} - Retorna uma lista de doadores.
	 */
	@ApiOperation(value = "Method responsible for returning the list of givers")
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<GiverDtoOut> getAll() {

		List<Giver> givers = giverService.findaAll();

		return giverDtoAssembler.toCollectionModel(givers);
	}

	/**
	 * Método responsável por conter o endpoint que busca od doador na base
	 * de dados por ID.
	 * 
	 * @return {@code GiverDtoOut} - Retorna um doador de acordo com o ID informado.
	 */
	@ApiOperation(value = "Method responsible for searching the giver by ID")
	@GetMapping(value = UriConstants.URI_GIVER_ID, produces = MediaType.APPLICATION_JSON_VALUE)
	public GiverDtoOut getById(@PathVariable("id") Long id) {

		Giver giver = giverService.findOrFail(id);

		return giverDtoAssembler.toModel(giver);
	}

	/**
	 * Método responsável por conter o endpoint salva os dados do doador na base
	 * de dados
	 * 
	 * @return {@code GiverDtoOut} - Retorna um doador de acordo com o ID informado.
	 */
	// @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	@ApiOperation(value = "Method responsible for saving the giver")
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ApiResponse> save(@RequestBody @Valid GiverRequest giverRequest) {
		
		giverService.save(giverRequest.getUser().getId());
		log.info("Registered successfully -> []");
		
		return ResponseEntity.created(URI.create("")).body(new ApiResponse(true, "Doador registrado com sucesso."));
	}

	/**
	 * Método responsável por conter o endpoint para excluir um doador por ID.
	 * @param {@code Long} id
	 * @return {@code ResponseEntity<Void>} Um retorno vazio de sucesso.
	 */
	@ApiOperation(value = "Method responsible for removing the giver")
	@DeleteMapping(value = UriConstants.URI_GIVER_ID, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
		
		giverService.deleteById(id);

		return ResponseEntity.noContent().build();
	}
}
