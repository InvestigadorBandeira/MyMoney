package br.com.vgalima.mymoney.service;

import java.io.Serializable;

import javax.inject.Inject;

import br.com.vgalima.mymoney.model.Conta;
import br.com.vgalima.mymoney.repository.Contas;
import br.com.vgalima.mymoney.util.jpa.Transactional;

public class CadastroContaService implements Serializable {
    private static final long serialVersionUID = 1L;

    @Inject
    private Contas contas;

    @Transactional
    public Conta salvar(Conta conta, boolean editando) {
	if (!editando) {
	    Conta existe = contas.existeConta(conta.getNome().trim());

	    if (existe != null)
		throw new NegocioException(
			"Já existe uma CONTA cadastrada com esse NOME informado.");
	}

	return contas.guardar(conta);
    }
}
