package com.autobots.automanager.controles.dto;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import lombok.Setter;

@Setter
public class FormLogin {
  private String email;
  private String senha;

  public UsernamePasswordAuthenticationToken converter() {
    return new UsernamePasswordAuthenticationToken(email, senha);
  }
}