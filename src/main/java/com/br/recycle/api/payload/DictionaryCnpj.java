package com.br.recycle.api.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@Data
@JsonInclude(Include.NON_EMPTY)
@JsonNaming(SnakeCaseStrategy.class)
public class DictionaryCnpj {

	private String socialReason;
    private String fantasyName;
    private String state;
    private String situation;
    private String type;
}
