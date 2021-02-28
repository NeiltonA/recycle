package com.br.recycle.api.model;

public enum Flow {

    D("Giver"), 
    C("Cooperative"),
    A("Admin"); 

    private String description;

    Flow(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

}
