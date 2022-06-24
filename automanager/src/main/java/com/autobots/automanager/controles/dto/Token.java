package com.autobots.automanager.controles.dto;

import com.autobots.automanager.entidades.Usuario;

import lombok.Data;

@Data
public class Token {
  private String token;
  private String type;
  private UsuarioLogin usuario;

  public Token(String token, String type, Usuario usuario) {
    this.token = token;
    this.type = type;

    this.usuario = UsuarioLogin.create(usuario);
  }
}