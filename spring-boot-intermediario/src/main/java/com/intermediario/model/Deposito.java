package com.intermediario.model;

import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SourceType;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.intermediario.model.converter.StatusConverter;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@JsonPropertyOrder({"id","numeroOrdem","montante","status","dataTransacao","numeroConta"})
@Table(name = "depositos", indexes = @Index(name = "idx_depositos_numero_ordem", columnList = "numero_ordem"))
public class Deposito {
	@Id
	private UUID id;
	
	@Column(name = "numero_ordem")
	private String numeroOrdem;
	
	@Column(nullable = false)
	private Double montante;
	
	@Column(nullable = false)
	@Convert(converter = StatusConverter.class)
	private Status status;
	
	@CreationTimestamp(source = SourceType.DB)
	@Column(name = "data_transacao", nullable = false, updatable = false)
	private LocalDateTime dataTransacao;

	@Column(name = "numero_conta", nullable = false, updatable = false)
	private String numeroConta;
	
	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "historico_bancario_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "fk_depositos_hitoricos_bancarios"))
	private HistoricoBancario historicoBancario;
	
	@JsonGetter("montante")
	public String montante() {
		return NumberFormat.getCurrencyInstance(Locale.of("pt", "AO")).format(montante);
	}
	
	@JsonSetter("montante")
	public void montante(String montante) {
		try {
			this.montante = NumberFormat.getCurrencyInstance(Locale.of("pt", "AO")).parse(montante).doubleValue();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	@JsonGetter("dataTransacao")
	private String dataTransacao() {
		return dataTransacao.toString();
	}

	@JsonSetter("dataTransacao")
	private void dataTransacao(String dataTransacao) {
		this.dataTransacao = LocalDateTime.parse(dataTransacao);
	}
}
