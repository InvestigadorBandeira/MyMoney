package br.com.vgalima.mymoney.repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;

import br.com.vgalima.mymoney.model.Conta;
import br.com.vgalima.mymoney.service.NegocioException;
import br.com.vgalima.mymoney.util.jpa.Transactional;

public class Contas implements Serializable {
    private static final long serialVersionUID = 1L;

    @Inject
    private EntityManager manager;

    public Conta guardar(Conta conta) {
	return manager.merge(conta);
    }

    @Transactional
    public void remover(Conta conta) {
	try {
	    conta = porId(conta.getId());
	    manager.remove(conta);
	    manager.flush();
	} catch (PersistenceException e) {
	    throw new NegocioException("Conta não pode ser excluída.");
	}
    }

    public Conta porId(Long id) {
	return manager.find(Conta.class, id);
    }

    public List<Conta> getContas() {
	CriteriaQuery<Conta> cq = manager.getCriteriaBuilder().createQuery(
		Conta.class);
	cq.select(cq.from(Conta.class));
	return manager.createQuery(cq).getResultList();
    }

    public Conta existeConta(String nome) {
	try {
	    return manager
		    .createQuery("FROM Conta c WHERE c.nome = :nome",
			    Conta.class).setParameter("nome", nome)
		    .getSingleResult();
	} catch (NoResultException e) {
	    return null;
	}
    }

    public List<Conta> filtrados(String nome) {
	if (nome == null || nome.trim().isEmpty())
	    return null;

	if ("*".equals(nome.trim()))
	    return getContas();

	String consulta = "FROM Conta c WHERE c.nome LIKE :nome";
	TypedQuery<Conta> query = manager.createQuery(consulta, Conta.class);
	query.setParameter("nome", "%" + nome + "%");

	return query.getResultList();
    }

}
