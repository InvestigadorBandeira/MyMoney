package br.com.vgalima.mymoney.repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.criteria.CriteriaQuery;

import br.com.vgalima.mymoney.model.Item;
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

	    for (Item i : titulo.getItens())
		manager.remove(i);

	    for (Parcela p : titulo.getParcelas()) {
		for (Item i : p.getItens())
		    manager.remove(i);

		manager.remove(p);
	    }

	    manager.remove(titulo);
	    manager.flush();
	} catch (PersistenceException e) {
	    throw new NegocioException("Título não pode ser excluído.");
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
