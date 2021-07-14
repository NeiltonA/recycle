package com.br.recycle.api.payload;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.br.CPF;

import com.br.recycle.api.model.Flow;
import com.br.recycle.api.validation.validator.CelularValidation;
import com.br.recycle.api.validation.validator.EmailValidation;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Classe responsável por conter os dados de entrada de cadastro
 * do usuário para a requisição.
 */
@Getter
@Setter
@ToString
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class UserInput{

	@ApiModelProperty(example = "Teste Silva", required = true)
	@NotBlank
	private String name;
	
	@ApiModelProperty(example = "teste@recycle.com", required = true)
	@NotBlank
	@EmailValidation
	private String email;
	
	@ApiModelProperty(example = "\"11 99999999\",\"(11) 99999-9999\"", required = false)
	@CelularValidation
	private String cellPhone;
	
	@ApiModelProperty(example = "10364680032", required = false)
	@CPF(message = "O CPF está inválido")	
	@NotBlank
	private String individualRegistration;
	
	@ApiModelProperty(example = "admin123", required = true)
	@NotBlank
	private String password;
	
	@ApiModelProperty(example = "admin123", required = true)
	@NotBlank
	private String confirmPassword;
	
	@ApiModelProperty(example = "\"D = Doador\",\"C = Cooperativa\",\"A = Admin\"",  required = true)
	@Enumerated(EnumType.STRING)
	@NotNull
	private Flow flowIndicator;
	
	private Boolean active = Boolean.TRUE;
}