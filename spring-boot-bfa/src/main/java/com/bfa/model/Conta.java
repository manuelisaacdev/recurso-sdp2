package com.bfa.model;

import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.UUID;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SourceType;

import com.bfa.model.converter.TipoContaConverter;
import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonRootName;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "contas")
@JsonClassDescription("conta")
@JsonRootName(value = "conta", namespace = "contas")
@JsonPropertyOrder({"id", "numeroConta", "iban",  "bloqueada", "tipoConta", "saldoDisponivel", "saldoContabilistico", "totalDepositos", "totalLevantamentos", "totalTransferencias", "dataCriacao"})
public class Conta {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	@Column(name = "numero_conta", nullable = false, updatable = false)
	private String numeroConta;
	
	@Column(name = "iban", nullable = false, updatable = false)
	private String iban;
	
	@ColumnDefault("false")
	@Column(nullable = false, insertable = false)
	private Boolean bloqueada;
	
	@Convert(converter = TipoContaConverter.class)
	@Column(name = "tipo_conta", nullable = false)
	private TipoConta tipoConta;
	
	@ColumnDefault("0")
	@Column(name = "saldo_disponivel", nullable = false)
	private Double saldoDisponivel;

	@ColumnDefault("0")
	@Column(name = "saldo_contabilistico", nullable = false)
	private Double saldoContabilistico;

	@CreationTimestamp(source = SourceType.DB)
	@Column(name = "data_criacao", nullable = false, updatable = false)
	private LocalDateTime dataCriacao;
	
	@JsonGetter("saldoDisponivel")
	public String saldoDisponivel() {
		return NumberFormat.getCurrencyInstance(Locale.of("pt", "AO")).format(saldoDisponivel);
	}
	
	@JsonGetter("saldoContabilistico")
	public String saldoContabilistico() {
		return NumberFormat.getCurrencyInstance(Locale.of("pt", "AO")).format(saldoContabilistico);
	}
}
