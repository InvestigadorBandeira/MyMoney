package br.com.vgalima.mymoney.repository;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;

import br.com.vgalima.mymoney.model.Conta;
import br.com.vgalima.mymoney.model.Receita;
import br.com.vgalima.mymoney.service.NegocioException;
import br.com.vgalima.mymoney.util.jpa.Transactional;

public class Receitas implements Serializable {
    private static final long serialVersionUID = 1L;

    @Inject
    private EntityManager manager;

    public Receita guardar(Receita receita) {
	return manager.merge(receita);
    }

    @Transactional
    public void remover(Receita receita) {
	try {
	    receita = porId(receita.getId());
	    manager.remove(receita);
	    manager.flush();
	} catch (PersistenceException e) {
	    throw new NegocioException("Receita não pode ser excluída.");
	}
    }

    public List<Receita> getReceitas() {
	CriteriaQuery<Receita> cq = manager.getCriteriaBuilder().createQuery(
		Receita.class);
	cq.select(cq.from(Receita.class));
	return manager.createQuery(cq).getResultList();
    }

    public Receita porId(Long id) {
	return manager.find(Receita.class, id);
    }

    public BigDecimal somaPorConta(Conta conta) {
	BigDecimal total = BigDecimal.ZERO;

	Query query = manager.createQuery(
		"SELECT SUM(r.valor) FROM Receita r WHERE r.conta = :conta")
		.setParameter("conta", conta);

	try {
	    Number retorno = (Number) query.getSingleResult();
	    if (retorno != null)
		total = total.add(new BigDecimal(retorno.doubleValue()));
	} catch (NoResultException e) {
	}

	return total;
    }

}
