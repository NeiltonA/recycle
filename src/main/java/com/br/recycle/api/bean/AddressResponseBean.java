package com.br.recycle.api.bean;

import lombok.Getter;
import lombok.Setter;

/**
 * Classe responsável por mapear os dados de resposta da pesquisa
 * de endereço por CEP.
 *
 */
@Getter
@Setter
public class AddressResponseBean {

	private String cep;
	private String logradouro;
	private String complemento;
	private String bairro;
	private String localidade;
	private String uf;
	private String ibge;
	private String gia;
	private String ddd;
	private String siafi;
}
