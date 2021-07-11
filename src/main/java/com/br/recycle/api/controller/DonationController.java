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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.br.recycle.api.assembler.DonationDtoAssembler;
import com.br.recycle.api.exception.BusinessException;
import com.br.recycle.api.model.Donation;
import com.br.recycle.api.payload.ApiResponse;
import com.br.recycle.api.payload.DonationDtoOut;
import com.br.recycle.api.payload.DonationInput;
import com.br.recycle.api.repository.DonationRepository;
import com.br.recycle.api.service.DonationService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping("/api/v1/donation")
@Api(value = "Donation", description = "REST API for Donation", tags = { "Donation" })
public class DonationController {

    @Autowired
    private DonationRepository repository;

    @Autowired
    private DonationService service;

    @Autowired
	private DonationDtoAssembler assembler;
    
    @ApiOperation(value = "Method responsible for returning the list of donations")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<DonationDtoOut> getAll(@RequestParam(required = false) Long user) {
    	
            List<Donation> donations = service.findAll(user);
            return assembler.toCollectionModel(donations);
    }

    @ApiOperation(value = "Method responsible for searching the donation by ID")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public DonationDtoOut getById(@PathVariable("id") Long id) {
        try {
           Donation donation = service.findOrFail(id);
            return assembler.toModel(donation);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

    @ApiOperation(value = "Method responsible for saving the donation")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> save(@Valid @RequestBody DonationInput newDonation) {
        try {
        	
        	Donation don = assembler.toDomainObject(newDonation);
            service.save(don);
            log.info("Registered successfully -> []");
            return ResponseEntity.created(URI.create("")).body(new ApiResponse(true, "Doação registrada com sucesso."));
        } catch (Exception e) {
            log.error("failed to register -> [] ", e);
            throw new BusinessException(e.getMessage(), e);
        }
    }

    @ApiOperation(value = "Method responsible for updating the donation")
    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> update(@PathVariable("id") long id, @RequestBody DonationInput newDonation) {
        try {
        	Donation don = assembler.toDomainObject(newDonation);
        	
            Optional<Donation> donation = repository.findById(id);
            if (donation.isPresent()) {
            	don.setId(donation.get().getId());
            	repository.save(don);
                return ResponseEntity.ok(new ApiResponse(true, "Doação modificada com sucesso."));
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

    @ApiOperation(value = "Method responsible for removing the donation")
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") long id) {
        try {
            Optional<Donation> donation = repository.findById(id);
            if (donation.isPresent()) {
                service.remove(id);
                return new ResponseEntity<>(HttpStatus.OK);
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }
}
