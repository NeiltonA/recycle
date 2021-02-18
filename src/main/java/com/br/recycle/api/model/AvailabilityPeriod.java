package com.br.recycle.api.model;

public enum AvailabilityPeriod {

	
	ANY_TIME("Any time"), //Qualquer horário
	MORNING("Morning"), //Manhã
	EVENING("Evening"), //Tarde
	NIGHT("Night"); //Noite
	
	private String description;
	
	AvailabilityPeriod(String description){
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}

}
