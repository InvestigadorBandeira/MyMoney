package br.com.vgalima.mymoney.service;

import java.io.Serializable;

import javax.inject.Inject;

import br.com.vgalima.mymoney.model.Categoria;
import br.com.vgalima.mymoney.repository.Categorias;
import br.com.vgalima.mymoney.util.jpa.Transactional;

public class CadastroCategoriaService implements Serializable {
    private static final long serialVersionUID = 1L;

    @Inject
    private Categorias categorias;

    @Transactional
    public Categoria salvar(Categoria categoria) {
	Categoria existe = categorias.existeCategoria(categoria.getNome()
		.trim());

	if (existe != null)
	    throw new NegocioException(
		    "Já existe uma CATEGORIA cadastrada com esse NOME informado.");

	return categorias.guardar(categoria);
    }
}
