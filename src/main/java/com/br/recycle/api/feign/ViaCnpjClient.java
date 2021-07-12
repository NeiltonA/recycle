package com.br.recycle.api.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.br.recycle.api.bean.CnpjResponseBean;
import com.br.recycle.api.commons.UriConstants;

@FeignClient(name = "cnpj", url = UriConstants.URI_PROVIDER_CNPJ)
public interface ViaCnpjClient {

	@RequestMapping("/cnpj/{cnpj}")
	CnpjResponseBean searchCnpj(@PathVariable("cnpj") String cnpj);
}
