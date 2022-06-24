package com.autobots.automanager.controles.dto;

import java.util.List;

import com.autobots.automanager.entidades.Mercadoria;

import lombok.Data;

@Data
public class CadastrarFornecedor extends CadastrarUsuario {
  List<Mercadoria> mercadorias;
}