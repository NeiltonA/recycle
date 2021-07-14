package com.br.recycle.api.payload;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.br.CNPJ;

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
public class CooperativeInput  {

    @ApiModelProperty(example = "Recycle do brasil LTDA", required = true)
    @NotBlank
    private String companyName; 

    @ApiModelProperty(example = "Juca do brasil LTDA", required = true)
    @NotBlank
    private String fantasyName;
     
    @ApiModelProperty(example = "52288720000106", required = true)
    @CNPJ(message = "CNPJ inválido")
    @NotBlank
    private String cnpj;

    @ApiModelProperty(example = "1", required = true)
    @NotNull
    private UserIdInput user;
}
