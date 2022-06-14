package com.autobots.automanager.modelos.adicionadores;

import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.autobots.automanager.controles.TelefoneControle;
import com.autobots.automanager.entidades.Telefone;
import com.autobots.automanager.modelos.AdicionadorLink;

@Component
public class TelefoneAdicionadorLink implements AdicionadorLink<Telefone> {

  @Override
  public void adicionarLink(List<Telefone> lista) {
    for (Telefone telefone : lista) {
      long id = telefone.getId();
      Link linkProprio = WebMvcLinkBuilder.linkTo(
          WebMvcLinkBuilder
              .methodOn(TelefoneControle.class)
              .obterTelefone(telefone.getClienteId(), id))
          .withSelfRel();
      telefone.add(linkProprio);
    }
  }

  @Override
  public void adicionarLink(Telefone telefone) {
    Link linkProprio = WebMvcLinkBuilder
        .linkTo(
            WebMvcLinkBuilder
                .methodOn(TelefoneControle.class)
                .obterTelefones(telefone.getClienteId()))
        .withRel("telefones");

    telefone.add(linkProprio);
  }
}