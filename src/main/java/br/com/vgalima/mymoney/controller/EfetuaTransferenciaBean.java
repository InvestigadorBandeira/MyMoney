package br.com.vgalima.mymoney.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.vgalima.mymoney.model.Conta;
import br.com.vgalima.mymoney.model.Transferencia;
import br.com.vgalima.mymoney.repository.Contas;
import br.com.vgalima.mymoney.service.TransferenciaService;
import br.com.vgalima.mymoney.util.jsf.FacesUtil;

@Named
@ViewScoped
public class EfetuaTransferenciaBean implements Serializable {
    private static final long serialVersionUID = 1L;

    @Inject
    private TransferenciaService service;

    @Inject
    private Contas contasDao;

    private Transferencia transferencia;

    private List<Conta> contas;

    public EfetuaTransferenciaBean() {
	limpar();
    }

    public void inicializar() {
	if (FacesUtil.isNotPostback())
	    contas = contasDao.getContas();
    }

    private void limpar() {
	transferencia = new Transferencia();
    }

    public void salvar() {
	this.transferencia = service.salvar(this.transferencia);
	limpar();

	FacesUtil.addInfoMessage("TRANSFERÊNCIA efetuada com sucesso!");
    }

    public Transferencia getTransferencia() {
	return transferencia;
    }

    public void setTransferencia(Transferencia transferencia) {
	this.transferencia = transferencia;
    }

    public List<Conta> getContas() {
	return contas;
    }

    public boolean isEditando() {
	return transferencia.getId() != null;
    }

}
