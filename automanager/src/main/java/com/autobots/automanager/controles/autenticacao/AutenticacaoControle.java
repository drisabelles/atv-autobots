package com.autobots.automanager.controles.autenticacao;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.autobots.automanager.configuracao.token.TokenServico;
import com.autobots.automanager.controles.dto.FormLogin;
import com.autobots.automanager.controles.dto.Token;
import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.repositorios.UsuarioRepositorio;

@RestController
@RequestMapping("/autent")
public class AutenticacaoControle {

  @Autowired
  private AuthenticationManager authManager;

  @Autowired
  private TokenServico tokenServico;

  @Autowired
  private UsuarioRepositorio repositorioUsuario;

  @PostMapping("/autenticacao")
  public ResponseEntity<Token> authenticate(@RequestBody FormLogin form) {
    UsernamePasswordAuthenticationToken payloadLogin = form.converter();

    try {
      Authentication authentication = authManager.authenticate(payloadLogin);

      Optional<Usuario> usuario = repositorioUsuario.findByEmail(authentication.getName());

      String token = tokenServico.generateToken(authentication.getName());

      return ResponseEntity.ok(new Token(token, "Bearer", usuario.get()));
    } catch (AuthenticationException e) {
      return new ResponseEntity<Token>(HttpStatus.UNAUTHORIZED);
    }
  }
}