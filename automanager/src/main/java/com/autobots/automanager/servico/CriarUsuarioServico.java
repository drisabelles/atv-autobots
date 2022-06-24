package com.autobots.automanager.servico;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.autobots.automanager.controles.dto.CadastrarFornecedor;
import com.autobots.automanager.controles.dto.CadastrarUsuario;
import com.autobots.automanager.entidades.Empresa;
import com.autobots.automanager.enumeracoes.PerfilUsuario;
import com.autobots.automanager.repositorios.EmpresaRepositorio;
import com.autobots.automanager.repositorios.UsuarioRepositorio;

@Service
public class CriarUsuarioServico {

  @Autowired
  private EmpresaRepositorio repositorioEmpresa;

  @Autowired
  private UsuarioRepositorio repositorioUsuario;

  @Autowired
  private EmpresaServico servicoEmpresa;

  public CadastrarUsuario cadastrarUsuario(CadastrarUsuario usuario, PerfilUsuario perfilUsuario) {
    BCryptPasswordEncoder toCriptografy = new BCryptPasswordEncoder();
    String senhaEncriptografa = toCriptografy.encode(usuario.getUsuario().getSenha());

    usuario.getUsuario().setSenha(senhaEncriptografa);
    usuario.getUsuario().getPerfis().add(perfilUsuario);

    usuario.getUsuario().setEndereco(usuario.getEndereco());

    usuario.getTelefones().forEach(novoTelefone -> {
      usuario.getUsuario().getTelefones().add(novoTelefone);
    });

    usuario.getDocumentos().forEach(novoDocumento -> {
      Date dataEmissao = new Date();
      novoDocumento.setDataEmissao(dataEmissao);
      usuario.getUsuario().getDocumentos().add(novoDocumento);
    });

    return usuario;
  }

  public void cadastrarAdministrador(CadastrarUsuario admin) {
    Empresa empresa = encontrarEmpresa(admin.getRazaoSocial());

    CadastrarUsuario novoUsuario = cadastrarUsuario(admin, PerfilUsuario.ADMINISTRADOR);

    repositorioUsuario.save(novoUsuario.getUsuario());
    empresa.getUsuarios().add(novoUsuario.getUsuario());

    repositorioEmpresa.save(empresa);
  }

  public void cadastrarGerente(CadastrarUsuario admin) {
    Empresa empresa = encontrarEmpresa(admin.getRazaoSocial());

    CadastrarUsuario novoUsuario = cadastrarUsuario(admin, PerfilUsuario.GERENTE);

    repositorioUsuario.save(novoUsuario.getUsuario());
    empresa.getUsuarios().add(novoUsuario.getUsuario());

    repositorioEmpresa.save(empresa);
  }

  public void cadastrarFuncionario(CadastrarUsuario funcionario) {
    Empresa empresa = encontrarEmpresa(funcionario.getRazaoSocial());

    CadastrarUsuario novoUsuario = cadastrarUsuario(funcionario, PerfilUsuario.VENDEDOR);

    repositorioUsuario.save(novoUsuario.getUsuario());
    empresa.getUsuarios().add(novoUsuario.getUsuario());

    repositorioEmpresa.save(empresa);
  }

  public void cadastrarFornecedor(CadastrarFornecedor fornecedor) {
    Empresa empresa = encontrarEmpresa(fornecedor.getRazaoSocial());

    CadastrarFornecedor novoUsuario = (CadastrarFornecedor) cadastrarUsuario(fornecedor, PerfilUsuario.FORNECEDOR);

    novoUsuario.getMercadorias().forEach(novaMercadoria -> {
      novaMercadoria.setCadastro(new Date());
      novaMercadoria.setFabricao(new Date());
      novaMercadoria.setValidade(new Date());

      novoUsuario.getUsuario().getMercadorias().add(novaMercadoria);
    });

    repositorioUsuario.save(novoUsuario.getUsuario());
    empresa.getUsuarios().add(novoUsuario.getUsuario());

    repositorioEmpresa.save(empresa);
  }

  public void cadastrarCliente(CadastrarUsuario cliente) {
    Empresa empresa = encontrarEmpresa(cliente.getRazaoSocial());

    CadastrarUsuario novoUsuario = cadastrarUsuario(cliente, PerfilUsuario.CLIENTE);

    repositorioUsuario.save(novoUsuario.getUsuario());
    empresa.getUsuarios().add(novoUsuario.getUsuario());

    repositorioEmpresa.save(empresa);
  }

  public Empresa encontrarEmpresa(String razaoSocial) {
    Empresa empresa = servicoEmpresa.encontrarEmpresa(razaoSocial);

    if (empresa == null) {
      new Exception("Não foi possível encontrar empresa, tente novamente");
    }

    return empresa;
  }

}