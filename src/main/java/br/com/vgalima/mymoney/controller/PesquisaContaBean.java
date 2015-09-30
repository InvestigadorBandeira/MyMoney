package br.com.vgalima.mymoney.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.vgalima.mymoney.model.Conta;
import br.com.vgalima.mymoney.repository.Contas;
import br.com.vgalima.mymoney.util.jsf.FacesUtil;

@Named
@ViewScoped
public class PesquisaContaBean implements Serializable {
    private static final long serialVersionUID = 1L;

    @Inject
    private Contas contas;

    private String nome;
    private List<Conta> filtrados;

    private Conta selecionada;

    public void pesquisar() {
	filtrados = contas.filtrados(nome);
    }

    // verificar se existem lançamentos na conta antes de excluir
    public void excluir() {
	contas.remover(selecionada);
	filtrados.remove(selecionada);

	FacesUtil.addInfoMessage("Conta excluída com sucesso.");
    }

    public String getNome() {
	return nome;
    }

    public void setNome(String nome) {
	this.nome = nome;
    }

    public List<Conta> getFiltrados() {
	return filtrados;
    }

    public Conta getSelecionada() {
	return selecionada;
    }

    public void setSelecionada(Conta selecionada) {
	this.selecionada = selecionada;
    }

}
