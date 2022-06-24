package com.autobots.automanager.controles.dto;

import java.util.List;

import com.autobots.automanager.entidades.Documento;
import com.autobots.automanager.entidades.Endereco;
import com.autobots.automanager.entidades.Telefone;
import com.autobots.automanager.entidades.Usuario;

import lombok.Data;

@Data
public class CadastrarUsuario {
  private String razaoSocial;
  private Usuario usuario;
  private Endereco endereco;
  private List<Telefone> telefones;
  private List<Documento> documentos;
}