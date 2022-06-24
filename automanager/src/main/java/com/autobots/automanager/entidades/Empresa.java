package com.autobots.automanager.entidades;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Empresa extends RepresentationModel<Empresa> {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true)
  private String razaoSocial;
  @Column
  private String nomeFantasia;

  @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  private Set<Telefone> telefones = new HashSet<>();

  @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
  private Endereco endereco;

  @Column(nullable = false)
  private Date cadastro;

  @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  @JsonIgnore
  private Set<Usuario> usuarios = new HashSet<>();

  @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  @JsonIgnore
  private Set<Mercadoria> mercadorias = new HashSet<>();

  @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  @JsonIgnore
  private Set<Servico> servicos = new HashSet<>();

  @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  @JsonIgnore
  private Set<Venda> vendas = new HashSet<>();
}