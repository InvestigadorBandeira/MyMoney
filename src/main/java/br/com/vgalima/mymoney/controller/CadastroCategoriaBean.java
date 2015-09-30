package br.com.vgalima.mymoney.controller;

import java.io.Serializable;

import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.vgalima.mymoney.model.Categoria;
import br.com.vgalima.mymoney.model.TipoCategoria;
import br.com.vgalima.mymoney.service.CadastroCategoriaService;
import br.com.vgalima.mymoney.util.jsf.FacesUtil;

@Named
@ViewScoped
public class CadastroCategoriaBean implements Serializable {
    private static final long serialVersionUID = 1L;

    @Inject
    private CadastroCategoriaService service;

    private Categoria categoria;

    public CadastroCategoriaBean() {
	limpar();
    }

    private void limpar() {
	categoria = new Categoria();
    }

    public void salvar() {
	this.categoria = service.salvar(this.categoria);
	limpar();

	FacesUtil.addInfoMessage("CATEGORIA salva com sucesso!");
    }

    public Categoria getCategoria() {
	return categoria;
    }

    public void setCategoria(Categoria categoria) {
	this.categoria = categoria;
    }

    public TipoCategoria[] getTipos() {
	return TipoCategoria.values();
    }

    public boolean isEditando() {
	return categoria.getId() != null;
    }

}
