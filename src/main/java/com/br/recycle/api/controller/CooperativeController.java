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
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.br.recycle.api.assembler.CooperativeDtoAssembler;
import com.br.recycle.api.bean.CnpjResponseBean;
import com.br.recycle.api.commons.UriConstants;
import com.br.recycle.api.exception.BusinessException;
import com.br.recycle.api.exception.UnprocessableEntityException;
import com.br.recycle.api.model.Cooperative;
import com.br.recycle.api.payload.ApiResponse;
import com.br.recycle.api.payload.CooperativeDtoOut;
import com.br.recycle.api.payload.CooperativeInput;
import com.br.recycle.api.repository.CooperativeRepository;
import com.br.recycle.api.service.CooperativeService;
import com.br.recycle.api.util.DictionaryCnpj;
import com.br.recycle.api.validation.CnpjValidation;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping("/api/v1/cooperative")
@Api(value = "Cooperative", description = "REST API for Cooperative", tags = { "Cooperative" })
public class CooperativeController {

    @Autowired
    private CooperativeRepository repository;
    
    @Autowired
	private CooperativeDtoAssembler assembler;

    @Autowired
    private CooperativeService service;

    @ApiOperation(value = "Method responsible for returning the list of cooperatives")
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CooperativeDtoOut> getAll() {
        try {
            List<Cooperative> cooperatives = repository.findAll();
            return assembler.toCollectionModel(cooperatives);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }
    
	@ApiOperation(value = "Method responsible for returning the cnpj via revenue-ws")
	@GetMapping(value = UriConstants.URI_REVENUE_CNPJ, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<DictionaryCnpj> getCnpj(@PathVariable String cnpj) {

		CnpjValidation.validate(cnpj);
		CnpjResponseBean cnpjResponseBean = service.searchCnpj(cnpj);
		DictionaryCnpj dictionaryCnpj = assembler.toDictionary(cnpjResponseBean);
			if (dictionaryCnpj.getSocialReason() ==null) {
				throw new UnprocessableEntityException("De acordo com o CNPJ informado não está relacionado a nenhuma empresa");
			}
		return ResponseEntity.ok().body(dictionaryCnpj);
	}


    @ApiOperation(value = "Method responsible for searching the cooperative by ID")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CooperativeDtoOut getById(@PathVariable("id") long id) {
        try {
           Cooperative coop = service.findOrFail(id);
            return assembler.toModel(coop);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

    @ApiOperation(value = "Method responsible for saving the cooperative")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> save(@Valid @RequestBody CooperativeInput cooperative) {
        try {
        	Cooperative coop = assembler.toDomainObject(cooperative);

        	service.save(coop);
            log.info("Registered successfully -> []");
            return ResponseEntity.created(URI.create("")).body(new ApiResponse(true, "Cooperativa registrada com sucesso."));
        } catch (Exception e) {
            log.error("failed to register -> [] ", e);
            throw new BusinessException(e.getMessage(), e);
        }
    }

    @ApiOperation(value = "Method responsible for updating the cooperative")
    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> update(@PathVariable("id") long id, @RequestBody CooperativeInput cooperative) {
        try {
        	Cooperative coop = assembler.toDomainObject(cooperative);
        	
            Optional<Cooperative> cooper = repository.findById(id);
            if (cooper.isPresent()) {
            	coop.setId(cooper.get().getId());
                repository.save(coop);
                return ResponseEntity.ok(new ApiResponse(true, "Cooperativa alterada com sucesso."));
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }
    
    @ApiOperation(value = "Method responsible for updating the cooperative")
    @PatchMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> updatePatch(@PathVariable("id") long id, @RequestBody CooperativeInput cooperative) {
        	Cooperative coop = assembler.toDomainObject(cooperative);
        	service.updatePatch(coop, id);
         return ResponseEntity.ok(new ApiResponse(true, "Cooperativa alterada com sucesso."));
    }

    @ApiOperation(value = "Method responsible for removing the cooperative") // cooperative
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") long id) {
        try {
            Optional<Cooperative> cooperative = repository.findById(id);
            if (cooperative.isPresent()) {
                repository.deleteById(id);
                return new ResponseEntity<>(HttpStatus.OK);
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }
}
