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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.br.recycle.api.assembler.RateDtoAssembler;
import com.br.recycle.api.exception.BusinessException;
import com.br.recycle.api.model.Rate;
import com.br.recycle.api.payload.ApiResponse;
import com.br.recycle.api.payload.RateDtoOut;
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
    
    @Autowired
  	private RateDtoAssembler assembler;

    @ApiOperation(value = "Method responsible for returning the list of rates")
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<RateDtoOut> getAll() {
        try {
            List<Rate> rates = repository.findAll();
            return assembler.toCollectionModel(rates);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

    @ApiOperation(value = "Method responsible for searching the rating by ID")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public RateDtoOut getById(@PathVariable("id") long id) {
        try {
            Rate rate = service.buscarOuFalhar(id);
            return assembler.toModel(rate);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

    @ApiOperation(value = "Method responsible for saving the rate")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> save(@Valid @RequestBody Rate rate) {
        try {
            service.save(rate);
            log.info("Registered successfully -> []");
            return ResponseEntity.created(URI.create("")).body(new ApiResponse(true, "Rate registered successfully"));
        } catch (Exception e) {
            log.error("Failed to register -> [] ", e);
            throw new BusinessException(e.getMessage(), e);
        }
    }

    @ApiOperation(value = "Method responsible for updating the rate")
    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> update(@PathVariable("id") long id, @RequestBody Rate rate) {
        try {
            Optional<Rate> rat = repository.findById(id);
            if (rat.isPresent()) {
                rate.setId(rat.get().getId());
                return ResponseEntity.ok(new ApiResponse(true, "Rate modify successfully"));
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

    @ApiOperation(value = "Method responsible for removing the rate")
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
            throw new BusinessException(e.getMessage(), e);
        }

    }

}
