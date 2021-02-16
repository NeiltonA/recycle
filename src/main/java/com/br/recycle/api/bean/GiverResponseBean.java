package com.br.recycle.api.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class GiverResponseBean {

	private String name;
	private String email;

	private String cpf_cnpj;
	private String cell_phone;
	private String zip_code;
	private String street;
	private String complement;
	private String number;
	private String neighborhood;
	private String city; 
	private String state;
}
