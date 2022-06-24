package com.autobots.automanager.controles;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.autobots.automanager.controles.dto.EmpresaUsuario;
import com.autobots.automanager.entidades.Empresa;
import com.autobots.automanager.repositorios.EmpresaRepositorio;

@RestController
@RequestMapping("/dashboard")
public class DashboardControle {

  @Autowired
  EmpresaRepositorio repositorioEmpresa;

  @GetMapping("/usuarios")
  public ResponseEntity<List<EmpresaUsuario>> obterUsuarios() {
    List<Empresa> empresas = repositorioEmpresa.findAll();

    if (empresas.isEmpty()) {
      ResponseEntity<List<EmpresaUsuario>> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
      return resposta;
    } else {
      List<EmpresaUsuario> empresaUsuarios = new ArrayList<>();

      for (Empresa empresa : empresas) {
        EmpresaUsuario empresaUsuario = new EmpresaUsuario(empresa);
        empresaUsuarios.add(empresaUsuario);
      }

      ResponseEntity<List<EmpresaUsuario>> resposta = new ResponseEntity<>(empresaUsuarios, HttpStatus.FOUND);
      return resposta;
    }
  }
}