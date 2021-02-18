package com.br.recycle.api.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.validator.constraints.br.CPF;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "cooperative")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Cooperative implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_cooperative")
	private Long id;
	
	@Column(name = "company_name")
	private String companyName; //Razão social
	
	@Column(name = "responsible_name")
	private String responsibleName;// nome do responsavel
	
	@Column(name = "cpf_responsible")
	@CPF(message = "Invalid CPF")
	private String CpfResponsible;

	@OneToOne
	@JoinColumn(name = "id_user", nullable = false)
	private User user;
}
