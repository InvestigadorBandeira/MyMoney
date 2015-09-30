package br.com.vgalima.mymoney.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.vgalima.mymoney.model.Transferencia;
import br.com.vgalima.mymoney.repository.Transferencias;
import br.com.vgalima.mymoney.util.jsf.FacesUtil;

@Named
@ViewScoped
public class PesquisaTransferenciaBean implements Serializable {
    private static final long serialVersionUID = 1L;

    @Inject
    private Transferencias transferencias;

    private String nome;
    private List<Transferencia> filtrados;

    private Transferencia selecionada;

    public void pesquisar() {
	filtrados = transferencias.getTransferencias();
    }

    public void excluir() {
	transferencias.remover(selecionada);
	filtrados.remove(selecionada);

	FacesUtil.addInfoMessage("Transferência excluída com sucesso.");
    }

    public String getNome() {
	return nome;
    }

    public void setNome(String nome) {
	this.nome = nome;
    }

    public List<Transferencia> getFiltrados() {
	return filtrados;
    }

    public Transferencia getSelecionada() {
	return selecionada;
    }

    public void setSelecionada(Transferencia selecionada) {
	this.selecionada = selecionada;
    }

}
