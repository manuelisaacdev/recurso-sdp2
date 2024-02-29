package com.bfa.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SourceType;

import com.bfa.model.converter.GeneroConverter;
import com.bfa.model.converter.PapelConverter;
import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
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
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Pattern;
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
@JsonClassDescription("funcionario")
@JsonRootName(value = "funcionario", namespace = "funcionarios")
@Table(
	name = "funcionarios",
	indexes = {
		@Index(name = "idx_funcionarios_nome", columnList = "nome"),
		@Index(name = "idx_funcionarios_email", columnList = "email"),
		@Index(name = "idx_funcionarios_bilhete_identidade", columnList = "bilhete_identidade"),
	},
	uniqueConstraints = {
		@UniqueConstraint(name = "uk_funcionarios_email", columnNames = "email"),
		@UniqueConstraint(name = "uk_funcionarios_bilhete_identidade", columnNames = "bilhete_identidade"),
	}
)
@JsonPropertyOrder({"id", "nome", "genero", "dataNascimento", "email", "bilheteIdentidade", "morada", "papel", "dataCriacao", "fotoPerfil", "pais", "telefone"})
public class Funcionario {
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

	@Column(nullable = false)
	private String email;

	@Column(name = "bilhete_identidade", nullable = false)
	private String bilheteIdentidade;

	@Pattern(message = "{Funcionario.morada.pattern}", regexp = "Rua \\d+ - Casa \\d+ - .+")
	@Column(nullable = false)
	private String morada;
		
	@Column(nullable = false)
	@Convert(converter = PapelConverter.class)
	private Papel papel;

	@Column(nullable = false)
	@JsonProperty(access = Access.WRITE_ONLY)
	private String senha;

	@CreationTimestamp(source = SourceType.DB)
	@Column(name = "data_criacao", nullable = false, updatable = false)
	private LocalDateTime dataCriacao;

	@Column(name = "foto_perfil")
	private String fotoPerfil;
	
	@ManyToOne
	@JoinColumn(name = "pais_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "fk_funcionarios_paises"))
	private Pais pais;

	@JoinTable(
		name="telefones_funcionarios",
		indexes = @Index(name = "idx_telefones_funcionarios_funcionario_id", columnList = "funcionario_id"),
		uniqueConstraints = @UniqueConstraint(name = "uk_telefones_funcionarios_funcionario_id_telefone_id", columnNames = {"funcionario_id", "telefone_id"}),
        joinColumns=@JoinColumn(name="funcionario_id", referencedColumnName="id", nullable = false, foreignKey = @ForeignKey(name = "fk_telefones_funcionarios_funcionario")),
        inverseJoinColumns=@JoinColumn(name="telefone_id", referencedColumnName="id", nullable = false, foreignKey = @ForeignKey(name = "fk_telefones_funcionarios_telefone"))
	)
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	private List<Telefone> telefones;
}
