package br.com.vgalima.mymoney.repository;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.inject.Inject;

import br.com.vgalima.mymoney.model.Conta;

public class Saldos implements Serializable {
    private static final long serialVersionUID = 1L;

    @Inject
    Transferencias transferencias;

    @Inject
    Receitas receitas;

    public BigDecimal getSaldoPorConta(Conta conta) {
	BigDecimal saldo = BigDecimal.ZERO;

	saldo = saldo.add(conta.getSaldoInicial());

	// Transferencias
	saldo = saldo.subtract(transferencias.somaPorContaOrigem(conta));
	saldo = saldo.add(transferencias.somaPorContaDestino(conta));

	// receitas
	saldo = saldo.add(receitas.somaPorConta(conta));

	return saldo;
    }

}
