package com.bfa.model;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SourceType;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonRootName;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
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
@JsonInclude(Include.NON_NULL)
@JsonClassDescription("empresa")
@JsonRootName(value = "empresa", namespace = "empresas")
@JsonPropertyOrder({"id", "nome", "dataCriacao", "telefone", "conta", "funcionario"})
@Table(
	name = "empresas",
	indexes = @Index(name = "idx_empresas_nome", columnList = "nome"),
	uniqueConstraints = @UniqueConstraint(name = "uk_empresas_nome", columnNames = "nome")
)
public class Empresa {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	
	@Column(nullable = false)
	private String nome;

	@CreationTimestamp(source = SourceType.DB)
	@Column(name = "data_criacao", nullable = false, updatable = false)
	private LocalDateTime dataCriacao;

	@OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true, fetch = FetchType.EAGER)
	@JoinColumn(name = "telefone_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "fk_empresas_telefones"))
	private Telefone telefone;

	@OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true, fetch = FetchType.EAGER)
	@JoinColumn(name = "conta_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "fk_empresas_contas"))
	private Conta conta;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "funcionario_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "fk_empresas_funcionarios"))
	private Funcionario funcionario;

}
