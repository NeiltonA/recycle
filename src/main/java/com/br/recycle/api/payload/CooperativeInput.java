package com.br.recycle.api.payload;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.br.CPF;

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
    private String companyName; 

    @ApiModelProperty(example = "Juca do brasil LTDA", required = true)
    private String responsibleName;
    
    @ApiModelProperty(example = "29090890025", required = true)
    @CPF(message = "CPF inválido")
    private String CpfResponsible;

    @ApiModelProperty(example = "1", required = true)
    @NotNull
    private UserIdInput user;
}
