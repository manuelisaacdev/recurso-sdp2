package com.bai.transacao.model;

import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SourceType;

import com.bai.transacao.model.converter.StatusConverter;
import com.bai.transacao.model.converter.TipoTransferenciaConverter;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(
	name = "transferencias", 
	indexes = @Index(name = "idx_transferencias_numero_ordem", columnList = "numero_ordem"),
	uniqueConstraints = @UniqueConstraint(name = "uk_transferencias_numero_ordem", columnNames = "numero_ordem")
)
@JsonPropertyOrder({"id","numeroOrdem","montante","status","tipoTransferencia","dataTransacao","ibanContaOrigem","ibanContaDestino"})
public class Transferencia {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	
	@Column(name = "numero_ordem")
	private String numeroOrdem;
	
	@Column(nullable = false)
	private Double montante;
	
	@Column(nullable = false)
	@Convert(converter = StatusConverter.class)
	private Status status;
	
	@Convert(converter = TipoTransferenciaConverter.class)
	@Column(name = "tipo_transferencia", nullable = false)
	private TipoTransferencia tipoTransferencia;

	@CreationTimestamp(source = SourceType.DB)
	@Column(name = "data_transacao", nullable = false, updatable = false)
	private LocalDateTime dataTransacao;

	@Column(name = "iban_conta_origem", nullable = false, updatable = false)
	private String ibanContaOrigem;

	@Column(name = "iban_conta_destino", nullable = false, updatable = false)
	private String ibanContaDestino;
	
	@JsonGetter("montante")
	public String montante() {
		return NumberFormat.getCurrencyInstance(Locale.of("pt", "AO")).format(montante);
	}

	@JsonGetter("dataTransacao")
	private String dataTransacao() {
		return dataTransacao.toString();
	}
}
