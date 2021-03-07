package com.br.recycle.api.payload;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.br.CPF;

import com.br.recycle.api.model.Flow;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class UserInput{

	@ApiModelProperty(example = "Teste Silva", required = true)
	@NotEmpty
	private String name;
	
	@ApiModelProperty(example = "teste@recycle.com", required = true)
	@NotEmpty(message = "{validation.mail.notEmpty}")
	@Email(regexp = ".*@.*\\..*", message = "Email inválido!")
	@NotEmpty
	private String email;
	
	@ApiModelProperty(example = "11 99999999", required = false)
	private String cellPhone;
	
	@ApiModelProperty(example = "10364680032", required = false)
	@CPF(message = "CPF inválido")
	private String individualRegistration;
	
	@ApiModelProperty(example = "admin123", required = true)
	@NotEmpty
	private String password;
	
	@ApiModelProperty(example = "admin123", required = true)
	@NotEmpty
	private String confirmPassword;
	
	@ApiModelProperty(example = "\"D = Doador\",\"C = Cooperativa\",\"A = Admin\"",  required = true)
	@NotNull
	@Enumerated(EnumType.STRING)
	private Flow flowIndicator;
	
	private Boolean active = Boolean.TRUE;
}