package com.br.recycle.api.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CnpjResponseBean {

		private String fantasia;
	    private String nome;
	    private String uf;
	    private String situacao;
	    private String tipo;

}
