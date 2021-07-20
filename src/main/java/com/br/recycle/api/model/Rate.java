package com.br.recycle.api.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.br.recycle.api.payload.RateDtoResponse;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "rate")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Rate implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rate")
    private Long id;

    private Long note;

    private String comment;

    @OneToOne
    @JoinColumn(name = "id_donation")
    private Donation donation;
    
    @OneToOne
    @JoinColumn(name = "id_cooperative")
    private Cooperative cooperative;

    @OneToOne
    @JoinColumn(name = "id_giver")
    private Giver giver;
    
    @Transient
    private List<RateDtoResponse> rateDto = new ArrayList<>();
}
