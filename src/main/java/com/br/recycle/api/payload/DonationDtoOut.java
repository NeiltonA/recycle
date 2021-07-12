package com.br.recycle.api.payload;

import java.time.OffsetDateTime;
import java.util.Calendar;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.br.recycle.api.model.AvailabilityDays;
import com.br.recycle.api.model.AvailabilityPeriod;
import com.br.recycle.api.model.DonationStatus;
import com.br.recycle.api.model.Storage;
import com.fasterxml.jackson.annotation.JsonFormat;
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
public class DonationDtoOut{


    private String code;
    private String donorUserName;
    private Long amount;
    private Storage storage;
    @Enumerated(EnumType.STRING) 
    private AvailabilityDays availabilityDays;
    @Enumerated(EnumType.STRING)
    private AvailabilityPeriod availabilityPeriod;
    @Enumerated(EnumType.STRING) 
    private DonationStatus status = DonationStatus.CREATED;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss", timezone = "America/Sao_Paulo")
    private Calendar dateRegister;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss", timezone = "America/Sao_Paulo")
    private Calendar updateDate;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private OffsetDateTime dateConfirmation;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private OffsetDateTime dateCancellation;
    
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private OffsetDateTime dateDelivery;
   
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private GiverDtoOut giver;
    
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private CooperativeDtoOut cooperative;
    
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private AddressDtoOut  address;

}
