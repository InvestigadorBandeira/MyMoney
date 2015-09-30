package br.com.vgalima.mymoney.controller;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.vgalima.mymoney.model.Conta;
import br.com.vgalima.mymoney.repository.Contas;
import br.com.vgalima.mymoney.repository.Saldos;

@Named
@ViewScoped
public class SaldoBean implements Serializable {
    private static final long serialVersionUID = 1L;

    @Inject
    private Saldos saldos;

    @Inject
    Contas contas;

    public List<Entry<Conta, BigDecimal>> getSaldoContas() {
	Map<Conta, BigDecimal> saldoContas = new HashMap<>();

	for (Conta conta : contas.getContas())
	    saldoContas.put(conta, saldos.getSaldoPorConta(conta));

	return new ArrayList(saldoContas.entrySet());
    }
}
