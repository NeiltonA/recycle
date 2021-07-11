package com.br.recycle.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.br.recycle.api.commons.UriConstants;
import com.br.recycle.api.service.FlowDonationService;

import io.swagger.annotations.Api;

/**
 * Classe responsável por ser a Contreller e conter do status da doação da aplicação.
 */
@RestController
@RequestMapping(value = UriConstants.URI_BASE_FLOW_DONATION)
@Api(value = "Flow Donation", description = "REST API for Flow Donation", tags = { "Flow Donation" })
public class FlowDonationController {

	private FlowDonationService flowDonationService;
	
	@Autowired
	public FlowDonationController(FlowDonationService flowDonationService) {
		this.flowDonationService = flowDonationService;
	}
	
	/**
	 * Método responsável por atualizar o estado da doação, 
	 * informando que está confirmdo.
	 * @param {@code String} code
	 */
	@PatchMapping(value = UriConstants.URI_FLOW_DONATION_CONFIRMED, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void confirm(@PathVariable String code) {
		flowDonationService.confirm(code);
	}
	
	/**
	 * Método responsável por atualizar o estado da doação, 
	 * informando que está cancelado.
	 * @param {@code String} code
	 */
	@PatchMapping(value = UriConstants.URI_FLOW_DONATION_CANCEL, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void cancel(@PathVariable String code) {
		flowDonationService.cancel(code);
	}
	
	/**
	 * Método responsável por atualizar o estado da doação, 
	 * informando que está em andamento.
	 * @param {@code String} code
	 */
	@PatchMapping(value = UriConstants.URI_FLOW_DONATION_DELIVERY, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deliver(@PathVariable String code) {
		flowDonationService.deliver(code);
	}	
}