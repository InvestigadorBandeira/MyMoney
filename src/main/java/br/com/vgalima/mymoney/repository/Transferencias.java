package br.com.vgalima.mymoney.repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.criteria.CriteriaQuery;

import br.com.vgalima.mymoney.model.Transferencia;
import br.com.vgalima.mymoney.service.NegocioException;
import br.com.vgalima.mymoney.util.jpa.Transactional;

public class Transferencias implements Serializable {
    private static final long serialVersionUID = 1L;

    @Inject
    private EntityManager manager;

    public Transferencia guardar(Transferencia transferencia) {
	return manager.merge(transferencia);
    }

    @Transactional
    public void remover(Transferencia transferencia) {
	try {
	    transferencia = porId(transferencia.getId());
	    manager.remove(transferencia);
	    manager.flush();
	} catch (PersistenceException e) {
	    throw new NegocioException("Transferência não pode ser excluída.");
	}
    }

    public List<Transferencia> getTransferencias() {
	CriteriaQuery<Transferencia> cq = manager.getCriteriaBuilder()
		.createQuery(Transferencia.class);
	cq.select(cq.from(Transferencia.class));
	return manager.createQuery(cq).getResultList();
    }

    public Transferencia porId(Long id) {
	return manager.find(Transferencia.class, id);
    }

}
