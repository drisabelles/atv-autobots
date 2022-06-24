package com.autobots.automanager.modelos;

import java.util.Date;

import com.autobots.automanager.entidades.Endereco;
import com.autobots.automanager.enumeracoes.TipoDocumento;

public class StringVerificadorNulo {

	public boolean verificar(String dado) {
		boolean nulo = true;
		if (!(dado == null)) {
			if (!dado.isBlank()) {
				nulo = false;
			}
		}
		return nulo;
	}

	public boolean verificar(TipoDocumento tipo) {
		return false;
	}

	public boolean verificar(Date cadastro) {
		return false;
	}

	public boolean verificar(Endereco endereco) {
		return false;
	}
}