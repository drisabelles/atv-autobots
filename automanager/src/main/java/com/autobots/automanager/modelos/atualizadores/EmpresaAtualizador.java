package com.autobots.automanager.modelos.atualizadores;

import com.autobots.automanager.entidades.Empresa;
import com.autobots.automanager.modelos.StringVerificadorNulo;

public class EmpresaAtualizador {
  private StringVerificadorNulo verificador = new StringVerificadorNulo();

  public void atualizar(Empresa empresa, Empresa atualizacao) {
    if (atualizacao != null) {
      if (!verificador.verificar(atualizacao.getRazaoSocial())) {
        empresa.setRazaoSocial(atualizacao.getRazaoSocial());
      }

      if (!verificador.verificar(atualizacao.getNomeFantasia())) {
        empresa.setNomeFantasia(atualizacao.getNomeFantasia());
      }

      if (!verificador.verificar(atualizacao.getEndereco())) {
        empresa.setEndereco(atualizacao.getEndereco());
      }

      if (!verificador.verificar(atualizacao.getCadastro())) {
        empresa.setCadastro(atualizacao.getCadastro());
      }

    }
  }

}