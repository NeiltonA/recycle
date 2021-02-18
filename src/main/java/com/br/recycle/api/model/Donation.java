package com.br.recycle.api.model;

import java.time.OffsetDateTime;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.domain.AbstractAggregateRoot;

import com.br.recycle.api.event.DonationCancelEvent;
import com.br.recycle.api.event.DonationConfirmedEvent;
import com.br.recycle.api.event.DonationDeliveredEvent;
import com.br.recycle.api.exception.BusinessException;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@Entity
@Table(name = "donation")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Donation extends AbstractAggregateRoot<Donation> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_donation")
    private Long id;

    private String code;

    @Column(name = "donor_user_name")
    private String donorUserName;

    @Column(name = "amount")
    private Long amount;

    @Enumerated(EnumType.STRING)// armazenamento
    private Storage storage;

    @Enumerated(EnumType.STRING) // Disponibilidade (dias)
    private AvailabilityDays availabilityDays;

    @Enumerated(EnumType.STRING) //Disponibilidade período
    private AvailabilityPeriod availabilityPeriod;

    @Enumerated(EnumType.STRING) // status Doação
    private DonationStatus status = DonationStatus.CREATED;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss", timezone = "America/Sao_Paulo")
    @Column(name = "date_register")
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar dateRegister = new GregorianCalendar(TimeZone.getTimeZone("America/Sao_Paulo")); //data cadastro

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss", timezone = "America/Sao_Paulo")
    @Column(name = "update_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar updateDate = new GregorianCalendar(TimeZone.getTimeZone("America/Sao_Paulo")); //data atualização

    @Column(name = "date_confirmation")
    private OffsetDateTime dateConfirmation;

    @Column(name = "date_cancellation")
    private OffsetDateTime dateCancellation;

    @Column(name = "date_delivery")
    private OffsetDateTime dateDelivery;

    @OneToOne
    @JoinColumn(name = "id_giver")
    private Giver giver;

    @OneToOne
    @JoinColumn(name = "id_cooperative")
    private Cooperative cooperative;

    @OneToOne
    @JoinColumn(name = "id_address")
    private Address address;

    public void confirm() {
        setStatus(DonationStatus.CONFIRMED);
        setDateConfirmation(OffsetDateTime.now());

        registerEvent(new DonationConfirmedEvent(this));
    }

    public void deliver() {
        setStatus(DonationStatus.DELIVERED);
        setDateDelivery(OffsetDateTime.now());
        registerEvent(new DonationDeliveredEvent(this));

    }

    public void cancel() {
        setStatus(DonationStatus.CANCELED);
        setDateCancellation(OffsetDateTime.now());
        registerEvent(new DonationCancelEvent(this));
    }

    private void setStatus(DonationStatus newStatus) {
        if (getStatus().cantChangeTo(newStatus)) {
            throw new BusinessException(
                    String.format("Status da donation %s não pode ser alterado de %s para %s",
                            getCode(), getStatus().getDescription(),
                            newStatus.getDescription()));
        }

        this.status = newStatus;
    }

    @PrePersist
    private void generateCode() {
        setCode(UUID.randomUUID().toString());
    }
}
