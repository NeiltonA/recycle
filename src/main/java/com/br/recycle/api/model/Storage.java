package com.br.recycle.api.model;

public enum Storage {

    PET_BOTTLE("Pet bottle"), // Garrafa Pet
    BARREL("Barrel"), // Barril
    GLASS("Glass"), // Vidro
    TANK("Tank"); // Tanque

    private String description;

    Storage(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

}
