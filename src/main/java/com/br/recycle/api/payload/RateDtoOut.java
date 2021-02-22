package com.br.recycle.api.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class RateDtoOut{

    private Long id;
    private Long note;
    private String comment;
    
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private CooperativeDtoOut cooperative;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private GiverDtoOut giver;
}
