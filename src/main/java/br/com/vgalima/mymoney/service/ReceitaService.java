package br.com.vgalima.mymoney.service;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.inject.Inject;

import br.com.vgalima.mymoney.model.Receita;
import br.com.vgalima.mymoney.model.TipoCategoria;
import br.com.vgalima.mymoney.repository.Receitas;
import br.com.vgalima.mymoney.util.jpa.Transactional;

public class ReceitaService implements Serializable {
    private static final long serialVersionUID = 1L;

    @Inject
    private Receitas receitas;

    @Transactional
    public Receita salvar(Receita receita) {
	if (receita.getCategoria().getTipoCategoria()
		.equals(TipoCategoria.Despesa))
	    throw new NegocioException(
		    "Receita não pode ter uma categoria de despesa.");

	BigDecimal valor = receita.getValor();

	if (valor.signum() != 1)
	    throw new NegocioException(
		    "Valor não pode ser negativo ou igual a zero.");

	return receitas.guardar(receita);
    }
}
