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

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "rate")
public class Rate implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_rate")
	private Long id;
	
	private String note; //nota
	
	private String  comment;//comentario

	
	@OneToOne
	@JoinColumn(name = "id_cooperative", nullable = false)
	private Cooperative cooperative;
	
	@OneToOne
	@JoinColumn(name = "id_giver", nullable = false)
	private Giver giver;
}
