package br.com.vgalima.mymoney.service;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.inject.Inject;

import br.com.vgalima.mymoney.model.Transferencia;
import br.com.vgalima.mymoney.repository.Transferencias;
import br.com.vgalima.mymoney.util.jpa.Transactional;

public class TransferenciaService implements Serializable {
    private static final long serialVersionUID = 1L;

    @Inject
    private Transferencias transferencias;

    @Transactional
    public Transferencia salvar(Transferencia transferencia) {
	if (transferencia.getOrigem().equals(transferencia.getDestino()))
	    throw new NegocioException(
		    "Conta de Destino não pode ser a mesma da Origem.");

	BigDecimal valor = transferencia.getValor();

	if (valor.signum() != 1)
	    throw new NegocioException(
		    "Valor não pode ser negativo ou igual a zero.");

	return transferencias.guardar(transferencia);
    }
}
