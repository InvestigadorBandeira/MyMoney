package br.com.vgalima.mymoney.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.vgalima.mymoney.model.Categoria;
import br.com.vgalima.mymoney.model.ItemTitulo;
import br.com.vgalima.mymoney.model.Parcela;
import br.com.vgalima.mymoney.model.Titulo;
import br.com.vgalima.mymoney.repository.Categorias;
import br.com.vgalima.mymoney.service.NegocioException;
import br.com.vgalima.mymoney.service.TituloService;
import br.com.vgalima.mymoney.util.jsf.FacesUtil;

@Named
@ViewScoped
public class CadastroTituloBean implements Serializable {
    private static final long serialVersionUID = 1L;

    @Inject
    private TituloService service;

    @Inject
    private Categorias categoriasDao;

    private List<Categoria> categorias;

    private Titulo titulo;

    private ItemTitulo item;

    private Parcela parcela;

    public CadastroTituloBean() {
	limpar();
    }

    public void inicializar() {
	if (FacesUtil.isNotPostback()) {
	    if (categorias == null)
		categorias = categoriasDao.getCategoriasReceita();
	}
    }

    private void limpar() {
	titulo = new Titulo();
	item = new ItemTitulo();
	parcela = new Parcela();
	titulo.setItens(new ArrayList<>());
	titulo.setParcelas(new ArrayList<>());
    }

    public void salvar() {
	this.titulo = service.salvar(this.titulo);
	limpar();

	FacesUtil.addInfoMessage("TÍTULO cadastrado com sucesso!");
    }

    public void adicionaItem() {
	if (item.getValor().signum() != 1)
	    throw new NegocioException(
		    "Valor não pode ser negativo ou igual a zero.");

	item.setTitulo(titulo);
	titulo.getItens().add(item);
	item = new ItemTitulo();
    }

    public void adicionaParcela() {
	if (parcela.getValor().signum() != 1)
	    throw new NegocioException(
		    "Valor não pode ser negativo ou igual a zero.");

	parcela.setTitulo(titulo);
	titulo.getParcelas().add(parcela);
	parcela = new Parcela();
    }

    public Titulo getTitulo() {
	return titulo;
    }

    public void setTitulo(Titulo titulo) {
	this.titulo = titulo;
    }

    public ItemTitulo getItem() {
	return item;
    }

    public void setItem(ItemTitulo item) {
	this.item = item;
    }

    public Parcela getParcela() {
	return parcela;
    }

    public void setParcela(Parcela parcela) {
	this.parcela = parcela;
    }

    public List<Categoria> getCategorias() {
	return categorias;
    }

    public boolean isEditando() {
	return titulo.getId() != null;
    }

}
