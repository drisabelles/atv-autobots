package com.autobots.automanager.servico;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autobots.automanager.entidades.Empresa;
import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.entidades.Veiculo;
import com.autobots.automanager.modelos.atualizadores.UsuarioAtualizador;
import com.autobots.automanager.repositorios.EmpresaRepositorio;
import com.autobots.automanager.repositorios.UsuarioRepositorio;
import com.autobots.automanager.repositorios.VeiculoRepositorio;

@Service
public class UsuarioServico {

  @Autowired
  EmpresaRepositorio repositorioEmpresa;

  @Autowired
  UsuarioRepositorio repositorioUsuario;

  @Autowired
  VeiculoRepositorio repositorioVeiculo;

  @Autowired
  EmpresaServico servicoEmpresa;

  public Usuario encontrarUsuario(Long id) {
    Optional<Usuario> usuario = repositorioUsuario.findById(id);

    if (usuario.isEmpty()) {
      return null;
    }

    return usuario.get();
  }

  public void cadastrarVeiculo(List<Veiculo> veiculos, Long idUsuario) {
    Usuario cliente = encontrarUsuario(idUsuario);

    veiculos.forEach(veiculo -> {
      veiculo.setProprietario(cliente);
      cliente.getVeiculos().add(veiculo);

      repositorioVeiculo.save(veiculo);
    });
  }

  public Veiculo encontrarVeiculo(Usuario usuario, String placa) {
    Set<Veiculo> veiculos = usuario.getVeiculos();

    Veiculo veiculoEncontrado = null;

    for (Veiculo veiculo : veiculos) {
      if (veiculo.getPlaca().equals(placa)) {
        veiculoEncontrado = veiculo;
      }
    }

    return veiculoEncontrado;
  }

  public void atualizarUsuario(Usuario usuarioAtualizado) {
    Usuario usuario = encontrarUsuario(usuarioAtualizado.getId());

    if (usuario == null) {
      new Exception("Não foi possível localizar essa usuario");
    }

    UsuarioAtualizador atualizador = new UsuarioAtualizador();
    atualizador.atualizar(usuario, usuarioAtualizado);

    repositorioUsuario.save(usuario);
  }

  public void excluirUsuario(Long idEmpresa, Long idUsuario) {
    Empresa empresa = servicoEmpresa.encontrarEmpresa(idEmpresa);

    if (empresa == null) {
      new Exception("Não foi possível encontrar empresa, tente novamente");
    }

    Set<Usuario> usuarios = empresa.getUsuarios();

    Usuario usuario = null;
    for (Usuario usuarioIterado : usuarios) {
      if (usuarioIterado.getId() == idUsuario) {
        usuario = usuarioIterado;
      }
    }

    usuarios.remove(usuario);
    repositorioUsuario.deleteById(usuario.getId());
    repositorioEmpresa.save(empresa);

  }
}