package br.com.vgalima.mymoney.controller;

import java.io.Serializable;

import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.vgalima.mymoney.model.Conta;
import br.com.vgalima.mymoney.model.TipoConta;
import br.com.vgalima.mymoney.service.CadastroContaService;
import br.com.vgalima.mymoney.util.jsf.FacesUtil;

@Named
@ViewScoped
public class CadastroContaBean implements Serializable {
    private static final long serialVersionUID = 1L;

    @Inject
    private CadastroContaService service;

    private Conta conta;

    public CadastroContaBean() {
	limpar();
    }

    private void limpar() {
	conta = new Conta();
    }

    public void salvar() {
	this.conta = service.salvar(this.conta, isEditando());
	limpar();

	FacesUtil.addInfoMessage("CONTA salva com sucesso!");
    }

    public Conta getConta() {
	return conta;
    }

    public void setConta(Conta conta) {
	this.conta = conta;
    }

    public TipoConta[] getTipos() {
	return TipoConta.values();
    }

    public boolean isEditando() {
	return conta.getId() != null;
    }

}
