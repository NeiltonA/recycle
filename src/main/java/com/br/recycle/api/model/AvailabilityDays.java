package com.br.recycle.api.model;

public enum AvailabilityDays {
	
	WEEKENDS("Weekends"), //Finais de semana
	EVERY_DAY("Every day"),//Todos os dias
	FROMMONDAY_TO_FRIDAY("From monday to friday");//Segunda-feira a sexta-feira
	
	
private String description;
	
	AvailabilityDays(String description){
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}

}
