package com.br.recycle.api.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.br.recycle.api.assembler.CooperativeDtoAssembler;
import com.br.recycle.api.bean.CnpjResponseBean;
import com.br.recycle.api.commons.UriConstants;
import com.br.recycle.api.model.Cooperative;
import com.br.recycle.api.payload.ApiResponse;
import com.br.recycle.api.payload.CooperativeDtoOut;
import com.br.recycle.api.payload.CooperativeInput;
import com.br.recycle.api.payload.DictionaryCnpj;
import com.br.recycle.api.service.CooperativeService;
import com.br.recycle.api.validation.CnpjValidation;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * Classe responsável por ser a Contreller e conter o Endpoint de cooperativa da
 * aplicação.
 */
@RestController
@RequestMapping(UriConstants.URI_BASE_COOPERATIVE)
@Api(value = "Cooperative", description = "REST API for Cooperative", tags = { "Cooperative" })
public class CooperativeController {

	private CooperativeDtoAssembler cooperativeDtoAssembler;
	private CooperativeService cooperativeService;

	@Autowired
	public CooperativeController(CooperativeDtoAssembler cooperativeDtoAssembler, CooperativeService cooperativeService) {
		this.cooperativeDtoAssembler = cooperativeDtoAssembler;
		this.cooperativeService = cooperativeService;
	}

	/**
	 * Método responsável por conter o endpoint que busca todos as cooperativas na
	 * base de dados. E pode buscar através do filtro pod id do usuário.
	 * 
	 * @param {@code Long} - user
	 * @return {@code List<CooperativeDtoOut} - Retorna uma lista de cooperativas.
	 */
	@ApiOperation(value = "Method responsible for returning the list of cooperatives")
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public List<CooperativeDtoOut> getAll(@RequestParam(required = false) Long user) {
		List<Cooperative> cooperatives = cooperativeService.findAll(user);

		return cooperativeDtoAssembler.toCollectionModel(cooperatives);
	}

	/**
	 * Método responsável por conter o endpoint que busca a cooperativa pelo CNPJ,
	 * na base da receita.
	 * 
	 * @param {@code String} - cnpj
	 * @return {@code ResponseEntity<DictionaryCnpj>} - Retorna os dados de cadastro
	 *         da empresa.
	 */
	@ApiOperation(value = "Method responsible for returning the cnpj via revenue-ws")
	@GetMapping(value = UriConstants.URI_COOPERATIVE_CNPJ, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<DictionaryCnpj> getCnpj(@PathVariable String cnpj) {

		CnpjValidation.validate(cnpj);
		CnpjResponseBean cnpjResponseBean = cooperativeService.searchCnpj(cnpj);
		DictionaryCnpj dictionaryCnpj = cooperativeDtoAssembler.toDictionary(cnpjResponseBean);

		return ResponseEntity.ok().body(dictionaryCnpj);
	}

	/**
	 * Método responsável por conter o endpoint que busca os dados da cooperativa
	 * pelo o seu ID.
	 * 
	 * @param {@code Long} - id
	 * @return {@code CooperativeDtoOut} - Retorna os dados de cadastro da
	 *         cooperativa.
	 */
	@ApiOperation(value = "Method responsible for searching the cooperative by ID")
	@GetMapping(value = UriConstants.URI_COOPERATIVE_ID, produces = MediaType.APPLICATION_JSON_VALUE)
	public CooperativeDtoOut getById(@PathVariable("id") Long id) {
		Cooperative cooperative = cooperativeService.findOrFail(id);
		return cooperativeDtoAssembler.toModel(cooperative);
	}

	/**
	 * Método responsável por conter o endpoint que salva os dados da cooperativa.
	 * 
	 * @param {@code CooperativeInput} - cooperativeInput
	 * @return {@code ResponseEntity<ApiResponse>} - retorna que os dados foram
	 *         salvos.
	 */
	@ApiOperation(value = "Method responsible for saving the cooperative")
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ApiResponse> save(@Valid @RequestBody CooperativeInput cooperativeInput) {
		Cooperative cooperative = cooperativeDtoAssembler.toDomainObject(cooperativeInput);
		cooperativeService.save(cooperative);

		return ResponseEntity.created(URI.create(""))
				.body(new ApiResponse(true, "Cooperativa registrada com sucesso."));
	}

	/**
	 * Método responsável por conter o endpoint que atualiza por completo os dados
	 * da cooperativa.
	 * 
	 * @param {@code Long} - id
	 * @param {@code CooperativeInput} cooperative
	 * @return {@code ResponseEntity<ApiResponse>} - Retorna que os dados foram
	 *         salvos com sucesso.
	 */
	@ApiOperation(value = "Method responsible for updating the cooperative")
	@PutMapping(value = UriConstants.URI_COOPERATIVE_ID, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ApiResponse> update(@PathVariable("id") Long id,
			@Valid @RequestBody CooperativeInput cooperativeInput) {

		Cooperative cooperative = cooperativeDtoAssembler.toDomainObject(cooperativeInput);
		cooperativeService.update(id, cooperative);

		return ResponseEntity.ok(new ApiResponse(true, "Cooperativa alterada com sucesso."));
	}

	/**
	 * Método responsável por conter o endpoint que atualiza parcialmente os daods
	 * da cooperativa.
	 * 
	 * @param {@code Long} - id
	 * @param {@code CooperativeInput} cooperative
	 * @return {@code ResponseEntity<ApiResponse>} - Retorna os dados atualizados.
	 */
	@ApiOperation(value = "Method responsible for updating the cooperative")
	@PatchMapping(value = UriConstants.URI_COOPERATIVE_ID, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ApiResponse> updatePatch(@PathVariable("id") Long id,
			@RequestBody CooperativeInput cooperativeInput) {
		
		Cooperative cooperative = cooperativeDtoAssembler.toDomainObject(cooperativeInput);
		cooperativeService.updatePatch(cooperative, id);
		return ResponseEntity.ok(new ApiResponse(true, "Cooperativa alterada com sucesso."));
	}

	/**
	 * Método responsável por conter o endpoint que deleta a cooperativa por ID.
	 * 
	 * @param {@code Long} id
	 * @return {@code ResponseEntity<Void>} - Retorna uma resposta de sucesso sem conteúdo.
	 */
	@ApiOperation(value = "Method responsible for removing the cooperative") // cooperative
	@DeleteMapping(value = UriConstants.URI_COOPERATIVE_ID, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
		
		cooperativeService.delete(id);
		
		return ResponseEntity.noContent().build();
	}
}
