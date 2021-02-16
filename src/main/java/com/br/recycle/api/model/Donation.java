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
import com.br.recycle.api.exception.NegocioException;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@Entity
@Table(name = "donation")
public class Donation  extends AbstractAggregateRoot<Donation>{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_donation")
	private Long id;
	
	private String codigo;
	
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
	private OffsetDateTime datedelivery;
	
	@OneToOne
	@JoinColumn(name = "id_giver", nullable = false)
	private User user;
	
	@OneToOne
	@JoinColumn(name = "id_cooperative", nullable = false)
	private Cooperative cooperative;
	
	@OneToOne
	@JoinColumn(name = "id_address", nullable = false)
	private Address address;
	
	public void confirm() {
		setStatus(DonationStatus.CONFIRMED);
		setDateConfirmation(OffsetDateTime.now());
		
		registerEvent(new DonationConfirmedEvent(this));
	}
	
	public void deliver() {
		setStatus(DonationStatus.DELIVERED);
		setDatedelivery(OffsetDateTime.now());
	}
	
	public void cancel() {
		setDateCancellation(OffsetDateTime.now());
		
		registerEvent(new DonationCancelEvent(this));
	}
	
	private void setStatus(DonationStatus newStatus) {
		if (getStatus().naoPodeAlterarPara(newStatus)) {
			throw new NegocioException(
					String.format("Status do donation %s não pode ser alterado de %s para %s",
							getCodigo(), getStatus().getDescricao(), 
							newStatus.getDescricao()));
		}
		
		this.status = newStatus;
	}
	
	@PrePersist
	private void gerarCodigo() {
		setCodigo(UUID.randomUUID().toString());
	}
}
