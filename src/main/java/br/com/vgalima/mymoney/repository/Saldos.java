package br.com.vgalima.mymoney.repository;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.inject.Inject;

import br.com.vgalima.mymoney.model.Conta;
import br.com.vgalima.mymoney.model.Receita;
import br.com.vgalima.mymoney.model.Transferencia;

public class Saldos implements Serializable {
    private static final long serialVersionUID = 1L;

    @Inject
    Transferencias transferencias;

    @Inject
    Receitas receitas;

    public BigDecimal getSaldoPorConta(Conta conta) {
	BigDecimal saldo = BigDecimal.ZERO;

	saldo = saldo.add(conta.getSaldoInicial());

	// melhorar
	for (Transferencia t : transferencias.getTransferencias())
	    if (t.getOrigem().equals(conta))
		saldo = saldo.subtract(t.getValor());
	    else if (t.getDestino().equals(conta))
		saldo = saldo.add(t.getValor());

	// melhorar
	for (Receita r : receitas.getReceitas())
	    if (r.getConta().equals(conta))
		saldo = saldo.add(r.getValor());

	return saldo;
    }

}
