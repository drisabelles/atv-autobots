package com.autobots.automanager.controles.dto;

import com.autobots.automanager.entidades.Mercadoria;

import lombok.Data;

@Data
public class CadastrarMercadoria {
  public String razaoSocial;
  public Long usuarioId;
  public Mercadoria mercadoria;

  public String dataValidadeEmTexto;
  public String dataFabricacaoEmTexto;
}