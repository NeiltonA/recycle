package com.br.recycle.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.br.recycle.api.service.FlowDonationService;

import io.swagger.annotations.Api;

@RestController
@RequestMapping(value = "/api/v1/donation/{code}")
@Api(value = "Flow Donation", description = "REST API for Flow Donation", tags = { "Flow Donation" })
public class FlowDonationController {

	@Autowired
	private FlowDonationService service;
	
	@PutMapping(value ="/confirmed", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void confirm(@PathVariable String code) {
		service.confirm(code);
	}
	
	
	@PutMapping(value ="/cancel",produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void cancel(@PathVariable String code) {
		service.cancel(code);
	}
	
	@PutMapping(value="/deliver", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deliver(@PathVariable String code) {
		service.deliver(code);
	}
	
}