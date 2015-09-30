package br.com.vgalima.mymoney.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.vgalima.mymoney.model.Receita;
import br.com.vgalima.mymoney.repository.Receitas;
import br.com.vgalima.mymoney.util.jsf.FacesUtil;

@Named
@ViewScoped
public class PesquisaReceitaBean implements Serializable {
    private static final long serialVersionUID = 1L;

    @Inject
    private Receitas receitas;

    private String nome;
    private List<Receita> filtrados;

    private Receita selecionada;

    public void pesquisar() {
	filtrados = receitas.getReceitas();
    }

    public void excluir() {
	receitas.remover(selecionada);
	filtrados.remove(selecionada);

	FacesUtil.addInfoMessage("Receita excluída com sucesso.");
    }

    public String getNome() {
	return nome;
    }

    public void setNome(String nome) {
	this.nome = nome;
    }

    public List<Receita> getFiltrados() {
	return filtrados;
    }

    public Receita getSelecionada() {
	return selecionada;
    }

    public void setSelecionada(Receita receita) {
	this.selecionada = receita;
    }

}
