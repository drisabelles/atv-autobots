package com.autobots.automanager.controles.dto;

import java.util.Set;

import com.autobots.automanager.entidades.Empresa;
import com.autobots.automanager.entidades.Usuario;

import lombok.Data;

@Data
public class EmpresaUsuario {
  private String razaoSocial;
  private Set<Usuario> usuarios;

  public EmpresaUsuario(Empresa empresa) {
    this.razaoSocial = empresa.getRazaoSocial();
    this.usuarios = empresa.getUsuarios();
  }
}