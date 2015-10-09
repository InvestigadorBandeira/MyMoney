package br.com.vgalima.mymoney.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.vgalima.mymoney.model.Titulo;
import br.com.vgalima.mymoney.repository.Titulos;
import br.com.vgalima.mymoney.util.jsf.FacesUtil;

@Named
@ViewScoped
public class PesquisaTituloBean implements Serializable {
    private static final long serialVersionUID = 1L;

    @Inject
    private Titulos titulos;

    private String nome;
    private List<Titulo> filtrados;

    private Titulo selecionado;

    public void pesquisar() {
	filtrados = titulos.getTitulos();
    }

    public void excluir() {
	titulos.remover(selecionado);
	filtrados.remove(selecionado);

	FacesUtil.addInfoMessage("Título excluído com sucesso.");
    }

    public String getNome() {
	return nome;
    }

    public void setNome(String nome) {
	this.nome = nome;
    }

    public List<Titulo> getFiltrados() {
	return filtrados;
    }

    public Titulo getSelecionado() {
	return selecionado;
    }

    public void setSelecionado(Titulo titulo) {
	this.selecionado = titulo;
    }

}
