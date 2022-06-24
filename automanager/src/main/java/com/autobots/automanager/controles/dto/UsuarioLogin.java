package com.autobots.automanager.controles.dto;

import java.util.Set;

import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.enumeracoes.PerfilUsuario;

import lombok.Data;

@Data
public class UsuarioLogin {
  private Long id;
  private String email;
  private String nome;
  private String nomeSocial;
  private Set<PerfilUsuario> perfis;

  public UsuarioLogin(Usuario usuario) {
    this.id = usuario.getId();
    this.email = usuario.getEmail();
    this.nome = usuario.getNome();
    this.nomeSocial = usuario.getNomeSocial();
    this.perfis = usuario.getPerfis();
  }

  public static UsuarioLogin create(Usuario usuario) {
    return new UsuarioLogin(usuario);
  }
}