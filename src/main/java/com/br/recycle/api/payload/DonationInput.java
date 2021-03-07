package com.br.recycle.api.payload;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.br.recycle.api.model.AvailabilityDays;
import com.br.recycle.api.model.AvailabilityPeriod;
import com.br.recycle.api.model.DonationStatus;
import com.br.recycle.api.model.Storage;
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
public class DonationInput {

	@ApiModelProperty(example = "Juca Silva", required = false)
	private String donorUserName;

	@ApiModelProperty(example = "10", required = false)
	private Long amount;

	@ApiModelProperty(example = "\"PET_BOTTLE\",\"BARREL\",\"GLASS\",\"TANK\"", required = true)
	@Enumerated(EnumType.STRING) // armazenamento
	private Storage storage;

	@ApiModelProperty(example = "\"WEEKENDS\",\"EVERY_DAY\",\"WEEK_DAYS\"\"", required = true)
	@Enumerated(EnumType.STRING) // Disponibilidade (dias)
	private AvailabilityDays availabilityDays;

	@ApiModelProperty(example = "\"ANY_TIME\",\"MORNING\",\"EVENING\",\"NIGHT\"", required = true)
	@Enumerated(EnumType.STRING) // Disponibilidade período
	private AvailabilityPeriod availabilityPeriod;

	@Enumerated(EnumType.STRING) // status Doação
	private DonationStatus status = DonationStatus.CREATED;
	
	@ApiModelProperty(example = "1", required = false)
	private GiverIdInput giver;

	@ApiModelProperty(example = "1", required = false)
	private CooperativeIdInput cooperative;


}
