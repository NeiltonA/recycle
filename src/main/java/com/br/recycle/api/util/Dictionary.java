package com.br.recycle.api.util;

import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@Data
@JsonNaming(SnakeCaseStrategy.class)
public class Dictionary {

    private String zipCode;//cep
    private String street;//rua
    private String complement; //complemento
    private String neighborhood;//bairro
    private String city; //cidade
    private String state;//estado
}
