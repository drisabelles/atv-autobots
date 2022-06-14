package com.autobots.automanager.modelos.adicionadores;

import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.autobots.automanager.controles.ClienteControle;
import com.autobots.automanager.controles.EnderecoControle;
import com.autobots.automanager.controles.TelefoneControle;
import com.autobots.automanager.entidades.Cliente;
import com.autobots.automanager.modelos.AdicionadorLink;

@Component
public class ClienteAdicionadorLink implements AdicionadorLink<Cliente> {

  @Override
  public void adicionarLink(List<Cliente> lista) {
    for (Cliente cliente : lista) {
      long id = cliente.getId();
      Link linkProprio = WebMvcLinkBuilder
          .linkTo(WebMvcLinkBuilder
              .methodOn(ClienteControle.class)
              .obterCliente(id))
          .withSelfRel();
      cliente.add(linkProprio);
    }
  }

  @Override
  public void adicionarLink(Cliente objeto) {
    Link linkProprio = WebMvcLinkBuilder
        .linkTo(WebMvcLinkBuilder
            .methodOn(ClienteControle.class)
            .obterClientes())
        .withRel("clientes");

    Link linkTelefone = WebMvcLinkBuilder
        .linkTo(WebMvcLinkBuilder
            .methodOn(TelefoneControle.class)
            .obterTelefones(objeto.getId()))
        .withRel("telefones");

    Link linkEndereco = WebMvcLinkBuilder
        .linkTo(WebMvcLinkBuilder
            .methodOn(EnderecoControle.class)
            .obterEndereco(objeto.getEndereco().getId()))
        .withRel("endereco");

    objeto.add(linkProprio);
    objeto.add(linkTelefone);
    objeto.add(linkEndereco);
  }
}