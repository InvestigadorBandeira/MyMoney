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
import br.com.vgalima.mymoney.util.jsf.FacesUtil;

@Named
@ViewScoped
public class SaldoBean implements Serializable {
    private static final long serialVersionUID = 1L;

    @Inject
    private Saldos saldos;

    @Inject
    Contas contas;

    private Map<Conta, BigDecimal> saldoContas;

    public void inicializar() {
	if (FacesUtil.isNotPostback()) {
	    if (saldoContas == null) {
		saldoContas = new HashMap<>();

		for (Conta conta : contas.getContas())
		    saldoContas.put(conta, saldos.getSaldoPorConta(conta));
	    }
	}
    }

    public List<Entry<Conta, BigDecimal>> getSaldoContas() {
	return new ArrayList<>(saldoContas.entrySet());
    }

    public BigDecimal getTotalSaldos() {
	BigDecimal total = BigDecimal.ZERO;

	for (Map.Entry<Conta, BigDecimal> dados : getSaldoContas())
	    total = total.add(dados.getValue());

	return total;
    }
}
