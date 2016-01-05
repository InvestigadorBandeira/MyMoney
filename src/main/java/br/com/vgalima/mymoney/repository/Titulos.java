package br.com.vgalima.mymoney.repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.criteria.CriteriaQuery;

import br.com.vgalima.mymoney.model.ItemParcela;
import br.com.vgalima.mymoney.model.ItemTitulo;
import br.com.vgalima.mymoney.model.Parcela;
import br.com.vgalima.mymoney.model.Titulo;
import br.com.vgalima.mymoney.service.NegocioException;
import br.com.vgalima.mymoney.util.jpa.Transactional;

public class Titulos implements Serializable {
    private static final long serialVersionUID = 1L;

    @Inject
    private EntityManager manager;

    public Titulo guardar(Titulo titulo) {
	return manager.merge(titulo);
	// return titulo;
    }

    @Transactional
    public void remover(Titulo titulo) {
	try {
	    titulo = porId(titulo.getId());

	    for (ItemTitulo item : titulo.getItens())
		manager.remove(item);

	    for (Parcela parcela : titulo.getParcelas()) {
		for (ItemParcela item : parcela.getItens())
		    manager.remove(item);

		manager.remove(parcela);
	    }

	    manager.remove(titulo);
	    manager.flush();
	} catch (PersistenceException e) {
	    throw new NegocioException("Título não pode ser excluído.");
	}
    }

    @Transactional
    public void removerItemParcela(Titulo titulo) {
	try {
	    titulo = porId(titulo.getId());

	    for (Parcela parcela : titulo.getParcelas()) {
		for (int i = 0; i < parcela.getItens().size(); i++) {
		    ItemParcela item = parcela.getItens().get(i);
		    parcela.getItens().remove(item);
		    item.setParcela(null);
		    manager.remove(item);
		}
		parcela.setItens(null);
		manager.merge(parcela);
	    }

	    manager.merge(titulo);
	    manager.flush();
	} catch (PersistenceException e) {
	    throw new NegocioException("Erro removendo Item de Parcelas.");
	}
    }

    public List<Titulo> getTitulos() {
	CriteriaQuery<Titulo> cq = manager.getCriteriaBuilder().createQuery(
		Titulo.class);
	cq.select(cq.from(Titulo.class));
	return manager.createQuery(cq).getResultList();
    }

    public Titulo porId(Long id) {
	return manager.find(Titulo.class, id);
    }

}
