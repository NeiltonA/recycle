package com.br.recycle.api.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.br.recycle.api.bean.AddressResponseBean;
import com.br.recycle.api.commons.UriConstants;

@FeignClient(url = UriConstants.URI_ZIP_CORREIO, name = "viacep")
public interface ViaZipCodeClient {

	@GetMapping("{zipCode}/json")
	AddressResponseBean searchAddress(@PathVariable("zipCode") String zipCode);
}
