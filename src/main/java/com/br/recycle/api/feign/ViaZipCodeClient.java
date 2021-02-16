package com.br.recycle.api.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.br.recycle.api.bean.AddressResponseBean;

@FeignClient(url="https://viacep.com.br/ws/", name = "viacep")
public interface ViaZipCodeClient {
	
	 @GetMapping("{zipCode}/json")
	AddressResponseBean searchAddress(@PathVariable("zipCode") String zipCode);
}
