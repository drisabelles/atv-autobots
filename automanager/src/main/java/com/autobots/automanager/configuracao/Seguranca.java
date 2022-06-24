package com.autobots.automanager.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.autobots.automanager.configuracao.seguranca.CustomUserDetailsService;
import com.autobots.automanager.configuracao.token.TokenFiltro;
import com.autobots.automanager.configuracao.token.TokenServico;
import com.autobots.automanager.repositorios.UsuarioRepositorio;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class Seguranca extends WebSecurityConfigurerAdapter {

  @Autowired
  TokenServico tokenServico;

  @Autowired
  UsuarioRepositorio usuarioRepositorio;

  @Autowired
  CustomUserDetailsService userDetailsService;

  private static final String[] rotasPublicas = {
      "/dashboard/**",
  };

  @Override
  @Bean
  protected AuthenticationManager authenticationManager() throws Exception {
    return super.authenticationManager();
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.cors().and().csrf().disable();

    http.authorizeRequests()
        .antMatchers(rotasPublicas).permitAll();

    http.authorizeRequests().antMatchers(HttpMethod.POST,
        "/auth/authentication").permitAll().anyRequest()
        .authenticated().and()
        .addFilterBefore(
            new TokenFiltro(
                tokenServico,
                usuarioRepositorio),
            UsernamePasswordAuthenticationFilter.class);

    http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
  }
}