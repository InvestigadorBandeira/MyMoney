package br.com.vgalima.mymoney.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.vgalima.mymoney.model.Categoria;
import br.com.vgalima.mymoney.model.Conta;
import br.com.vgalima.mymoney.model.Receita;
import br.com.vgalima.mymoney.repository.Categorias;
import br.com.vgalima.mymoney.repository.Contas;
import br.com.vgalima.mymoney.service.ReceitaService;
import br.com.vgalima.mymoney.util.jsf.FacesUtil;

@Named
@ViewScoped
public class CadastroReceitaBean implements Serializable {
    private static final long serialVersionUID = 1L;

    @Inject
    private ReceitaService service;

    @Inject
    private Contas contasDao;

    @Inject
    private Categorias categoriasDao;

    private Receita receita;

    private List<Conta> contas;

    private List<Categoria> categorias;

    public CadastroReceitaBean() {
	limpar();
    }

    public void inicializar() {
	if (FacesUtil.isNotPostback()) {
	    contas = contasDao.getContas();
	    categorias = categoriasDao.getCategoriasReceita();
	}
    }

    private void limpar() {
	receita = new Receita();
    }

    public void salvar() {
	this.receita = service.salvar(this.receita);
	limpar();

	FacesUtil.addInfoMessage("RECEITA cadastrada com sucesso!");
    }

    public Receita getReceita() {
	return receita;
    }

    public void setReceita(Receita receita) {
	this.receita = receita;
    }

    public List<Conta> getContas() {
	return contas;
    }

    public List<Categoria> getCategorias() {
	return categorias;
    }

    public boolean isEditando() {
	return receita.getId() != null;
    }

}
