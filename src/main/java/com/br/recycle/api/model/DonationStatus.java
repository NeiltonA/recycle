package com.br.recycle.api.model;

import java.util.Arrays;
import java.util.List;

public enum DonationStatus {

    CREATED("Created"), //criado
    CONFIRMED("Confirmed", CREATED), //confirmado
    DELIVERED("Delivered", CONFIRMED), // entregue
    CANCELED("Canceled", CREATED); // cancelado

    private String description;
    private List<DonationStatus> previousStatus; //status anteriores

    DonationStatus(String description, DonationStatus... previousStatus) {
        this.description = description;
        this.previousStatus = Arrays.asList(previousStatus);
    }

    public String getDescription() {
        return this.description;
    }

    public boolean cantChangeTo(DonationStatus novoStatus) {
        return !novoStatus.previousStatus.contains(this);
    }

}