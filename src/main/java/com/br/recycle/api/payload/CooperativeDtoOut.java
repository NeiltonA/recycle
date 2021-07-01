package com.br.recycle.api.payload;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class CooperativeDtoOut  {


    private Long id;
    private String companyName; 
    private String fantasyName;
    //private String cpfResponsible;
    private String cnpj;
    private UserDtoOut user;
}
