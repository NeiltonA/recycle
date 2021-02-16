package com.br.recycle.api.model;

import java.util.Arrays;
import java.util.List;

public enum DonationStatus {

	CREATED("Created"), //criado
	CONFIRMED("Confirmed", CREATED), //confirmado
	DELIVERED("Delivered", CONFIRMED), // entregue
	CANCELED("Canceled", CREATED); // cancelado
	
	private String descricao;
	private List<DonationStatus> previousStatus; //status anteriores
	
	DonationStatus(String descricao, DonationStatus... statusAnteriores) {
		this.descricao = descricao;
		this.previousStatus = Arrays.asList(statusAnteriores);
	}
	
	public String getDescricao() {
		return this.descricao;
	}
	
	public boolean naoPodeAlterarPara(DonationStatus novoStatus) {
		return !novoStatus.previousStatus.contains(this);
	}
	
}