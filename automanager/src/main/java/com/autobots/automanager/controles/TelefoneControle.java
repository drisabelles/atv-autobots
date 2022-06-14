package com.autobots.automanager.controles;

import java.util.List;

import com.autobots.automanager.entidades.Cliente;
import com.autobots.automanager.entidades.Telefone;
import com.autobots.automanager.modelos.adicionadores.TelefoneAdicionadorLink;
import com.autobots.automanager.modelos.atualizadores.TelefoneAtualizador;
import com.autobots.automanager.modelos.selecionadores.ClienteSelecionador;
import com.autobots.automanager.modelos.selecionadores.TelefoneSelecionador;
import com.autobots.automanager.repositorios.ClienteRepositorio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/telefone")
public class TelefoneControle {
  @Autowired
  private ClienteRepositorio repositorioCliente;
  @Autowired
  private ClienteSelecionador selecionadorCliente;

  @Autowired
  private TelefoneSelecionador selecionadorTelefone;

  @Autowired
  private TelefoneAdicionadorLink adicionadorLink;

  @GetMapping("/{clienteId}")
  public ResponseEntity<?> obterTelefones(@PathVariable long clienteId) {
    HttpStatus status = HttpStatus.NOT_FOUND;

    List<Cliente> clientes = repositorioCliente.findAll();
    Cliente cliente = selecionadorCliente.selecionar(clientes, clienteId);
    List<Telefone> telefones = cliente.getTelefones();

    if (telefones.size() > 0) {
      status = HttpStatus.FOUND;
      adicionadorLink.adicionarLink(telefones);

      return new ResponseEntity<List<Telefone>>(telefones, status);
    }
    return new ResponseEntity<>(status);
  }

  @GetMapping("/{clienteId}/{telefoneId}")
  public ResponseEntity<?> obterTelefone(@PathVariable long clienteId, @PathVariable long telefoneId) {
    HttpStatus status = HttpStatus.NOT_FOUND;

    List<Cliente> clientes = repositorioCliente.findAll();
    Cliente cliente = selecionadorCliente.selecionar(clientes, clienteId);

    Telefone telefoneEncontrado = selecionadorTelefone.selecionar(cliente, telefoneId);

    if (telefoneEncontrado != null) {
      status = HttpStatus.FOUND;
      adicionadorLink.adicionarLink(telefoneEncontrado);

      return new ResponseEntity<Telefone>(telefoneEncontrado, status);
    }
    return new ResponseEntity<>(status);
  }

  @PostMapping("/{clienteId}")
  public ResponseEntity<?> cadastrarTelefone(@PathVariable long clienteId, @RequestBody Telefone telefone) {
    HttpStatus status = HttpStatus.BAD_REQUEST;

    try {
      List<Cliente> clientes = repositorioCliente.findAll();
      Cliente cliente = selecionadorCliente.selecionar(clientes, clienteId);

      cliente.getTelefones().add(telefone);
      repositorioCliente.save(cliente);
      status = HttpStatus.CREATED;
      return new ResponseEntity<>(status);
    } catch (Exception e) {
      return new ResponseEntity<>(status);
    }
  }

  @PutMapping("/{clienteId}")
  public ResponseEntity<?> atualizarTelefone(@PathVariable long clienteId, @RequestBody Telefone telefone) {
    HttpStatus status = HttpStatus.BAD_REQUEST;

    try {
      List<Cliente> clientes = repositorioCliente.findAll();
      Cliente cliente = selecionadorCliente.selecionar(clientes, clienteId);

      Telefone telefoneEncontrado = selecionadorTelefone.selecionar(cliente, telefone.getId());

      if (telefoneEncontrado == null) {
        return new ResponseEntity<>(status);
      }

      TelefoneAtualizador telefoneAtualizador = new TelefoneAtualizador();
      telefoneAtualizador.atualizar(telefoneEncontrado, telefone);

      repositorioCliente.save(cliente);
      status = HttpStatus.OK;
      return new ResponseEntity<>(status);
    } catch (Exception e) {
      return new ResponseEntity<>(status);
    }
  }

  @DeleteMapping("/{clienteId}/{telefoneId}")
  public ResponseEntity<?> deletarTelefone(@PathVariable long clienteId, @PathVariable long telefoneId) {
    HttpStatus status = HttpStatus.BAD_REQUEST;

    try {
      List<Cliente> clientes = repositorioCliente.findAll();
      Cliente cliente = selecionadorCliente.selecionar(clientes, clienteId);

      Telefone telefone = selecionadorTelefone.selecionar(cliente, telefoneId);

      if (telefone == null) {
        return new ResponseEntity<>(status);
      }

      cliente.getTelefones().remove(telefone);
      repositorioCliente.save(cliente);
      status = HttpStatus.OK;

      return new ResponseEntity<>(status);
    } catch (Exception e) {
      return new ResponseEntity<>(status);
    }
  }
}