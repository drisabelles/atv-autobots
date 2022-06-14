package com.autobots.automanager.modelos;

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
}