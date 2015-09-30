package br.com.vgalima.mymoney.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.vgalima.mymoney.model.Categoria;
import br.com.vgalima.mymoney.repository.Categorias;
import br.com.vgalima.mymoney.util.jsf.FacesUtil;

@Named
@ViewScoped
public class PesquisaCategoriaBean implements Serializable {
    private static final long serialVersionUID = 1L;

    @Inject
    private Categorias categorias;

    private String nome;
    private List<Categoria> filtrados;

    private Categoria selecionada;

    public void pesquisar() {
	filtrados = categorias.filtrados(nome);
    }

    // verificar se existem lançamentos na Categoria antes de excluir
    public void excluir() {
	categorias.remover(selecionada);
	filtrados.remove(selecionada);

	FacesUtil.addInfoMessage("Categoria excluída com sucesso.");
    }

    public String getNome() {
	return nome;
    }

    public void setNome(String nome) {
	this.nome = nome;
    }

    public List<Categoria> getFiltrados() {
	return filtrados;
    }

    public Categoria getSelecionada() {
	return selecionada;
    }

    public void setSelecionada(Categoria selecionada) {
	this.selecionada = selecionada;
    }

}
