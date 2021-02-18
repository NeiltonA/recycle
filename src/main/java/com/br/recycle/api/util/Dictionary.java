package com.br.recycle.api.util;

import lombok.Data;

@Data
public class Dictionary {

    private String zipCode;//cep
    private String street;//rua
    private String complement; //complemento
    private String neighborhood;//bairro
    private String city; //cidade
    private String state;//estado
}
