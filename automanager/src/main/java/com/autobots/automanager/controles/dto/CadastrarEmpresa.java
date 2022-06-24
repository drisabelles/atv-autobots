package com.autobots.automanager.controles.dto;

import java.util.List;

import com.autobots.automanager.entidades.Empresa;
import com.autobots.automanager.entidades.Endereco;
import com.autobots.automanager.entidades.Telefone;

import lombok.Data;

@Data
public class CadastrarEmpresa {
  private Empresa empresa;
  private Endereco endereco;
  private List<Telefone> telefones;
}