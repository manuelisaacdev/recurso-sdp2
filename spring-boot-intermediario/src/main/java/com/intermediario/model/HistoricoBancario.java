package com.intermediario.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SourceType;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
@Table(name = "hitoricos_bancarios")
@JsonPropertyOrder({"id","dataSolicitacao","dataResposta","status","iban"})
public class HistoricoBancario {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@CreationTimestamp(source = SourceType.DB)
	@Column(name = "data_solicitacao", nullable = false, updatable = false)
	private LocalDateTime dataSolicitacao;
	
	@Column(name = "data_resposta", insertable = false)
	private LocalDateTime dataResposta;

	@Column(nullable = false)
	private Status status;
	
	@Column(nullable = false)
	private String iban;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "historicoBancario", orphanRemoval = true, fetch = FetchType.EAGER)
	private List<Deposito> depositos;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "historicoBancario", orphanRemoval = true, fetch = FetchType.EAGER)
	private List<Levantamento> levantamentos;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "historicoBancario", orphanRemoval = true, fetch = FetchType.EAGER)
	private List<Transferencia> transferencias;
}
