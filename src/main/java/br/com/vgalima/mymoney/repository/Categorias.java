package br.com.vgalima.mymoney.repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;

import br.com.vgalima.mymoney.model.Categoria;
import br.com.vgalima.mymoney.service.NegocioException;
import br.com.vgalima.mymoney.util.jpa.Transactional;

public class Categorias implements Serializable {
    private static final long serialVersionUID = 1L;

    @Inject
    private EntityManager manager;

    public Categoria guardar(Categoria categoria) {
	return manager.merge(categoria);
    }

    @Transactional
    public void remover(Categoria categoria) {
	try {
	    categoria = porId(categoria.getId());
	    manager.remove(categoria);
	    manager.flush();
	} catch (PersistenceException e) {
	    throw new NegocioException("Categoria não pode ser excluída.");
	}
    }

    public Categoria porId(Long id) {
	return manager.find(Categoria.class, id);
    }

    public List<Categoria> getCategorias() {
	CriteriaQuery<Categoria> cq = manager.getCriteriaBuilder().createQuery(
		Categoria.class);
	cq.select(cq.from(Categoria.class));
	return manager.createQuery(cq).getResultList();
    }

    public List<Categoria> getCategoriasReceita() {
	CriteriaQuery<Categoria> cq = manager.getCriteriaBuilder().createQuery(
		Categoria.class);
	cq.select(cq.from(Categoria.class));
	return manager.createQuery(cq).getResultList();
    }

    public Categoria existeCategoria(String nome) {
	try {
	    return manager
		    .createQuery("FROM Categoria c WHERE c.nome = :nome",
			    Categoria.class).setParameter("nome", nome)
		    .getSingleResult();
	} catch (NoResultException e) {
	    return null;
	}
    }

    public List<Categoria> filtrados(String nome) {
	if (nome == null || nome.trim().isEmpty())
	    return null;

	if ("*".equals(nome.trim()))
	    return getCategorias();

	String consulta = "FROM Categoria c WHERE c.nome LIKE :nome";
	TypedQuery<Categoria> query = manager.createQuery(consulta,
		Categoria.class);
	query.setParameter("nome", "%" + nome + "%");

	return query.getResultList();
    }

}
