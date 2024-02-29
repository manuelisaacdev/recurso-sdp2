package com.bfa.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SourceType;

import com.bfa.model.converter.GeneroConverter;
import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonRootName;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
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
@JsonClassDescription("pessoa")
@JsonRootName(value = "pessoa", namespace = "pessoas")
@Table(
	name = "pessoas",
	indexes = {
		@Index(name = "idx_pessoas_nome", columnList = "nome"),
		@Index(name = "idx_pessoas_email", columnList = "email"),
		@Index(name = "idx_pessoas_bilhete_identidade", columnList = "bilhete_identidade"),
	},
	uniqueConstraints = {
		@UniqueConstraint(name = "uk_pessoas_email", columnNames = "email"),
		@UniqueConstraint(name = "uk_pessoas_conta_id", columnNames = "conta_id"),
		@UniqueConstraint(name = "uk_pessoas_telefone_id", columnNames = "telefone_id"),
		@UniqueConstraint(name = "uk_pessoas_bilhete_identidade", columnNames = "bilhete_identidade"),
	}
)
@JsonPropertyOrder({"id", "nome", "genero", "dataNascimento", "email", "bilheteIdentidade", "morada", "dataCriacao", "fotoPerfil", "pais", "telefone", "conta", "funcionario"})
public class Pessoa {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	
	@Column(nullable = false)
	private String nome;

	@Column(nullable = false)
	@Convert(converter = GeneroConverter.class)
	private Genero genero;
	
	@Column(nullable = false)
	private LocalDate dataNascimento;

	private String email;

	@Column(name = "bilhete_identidade", nullable = false)
	private String bilheteIdentidade;

	@Column(nullable = false)
	private String morada;

	@CreationTimestamp(source = SourceType.DB)
	@Column(name = "data_criacao", nullable = false, updatable = false)
	private LocalDateTime dataCriacao;

	@Column(name = "foto_perfil")
	private String fotoPerfil;
	
	@ManyToOne
	@JoinColumn(name = "pais_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "fk_pessoas_paises"))
	private Pais pais;

	@OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true, fetch = FetchType.EAGER)
	@JoinColumn(name = "telefone_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "fk_pessoas_telefones"))
	private Telefone telefone;

	@OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true, fetch = FetchType.EAGER)
	@JoinColumn(name = "conta_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "fk_pessoas_contas"))
	private Conta conta;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "funcionario_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "fk_pessoas_funcionarios"))
	private Funcionario funcionario;

}
