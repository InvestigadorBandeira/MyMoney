package br.com.vgalima.mymoney.service;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.inject.Inject;

import br.com.vgalima.mymoney.model.Parcela;
import br.com.vgalima.mymoney.model.Titulo;
import br.com.vgalima.mymoney.repository.Titulos;
import br.com.vgalima.mymoney.util.jpa.Transactional;

public class TituloService implements Serializable {
    private static final long serialVersionUID = 1L;

    @Inject
    private Titulos titulos;

    @Transactional
    public Titulo salvar(Titulo titulo) {

	if (titulo.getItens() == null || titulo.getItens().isEmpty())
	    throw new NegocioException(
		    "Não existem ITENS cadastrados nesse TÍTULO.");

	if (titulo.getParcelas() == null || titulo.getParcelas().isEmpty())
	    throw new NegocioException(
		    "Não existem PARCELAS cadastrados nesse TÍTULO.");

	BigDecimal totalItens = titulo.getValorTotal();

	BigDecimal TotalParcelas = BigDecimal.ZERO;

	for (Parcela p : titulo.getParcelas())
	    TotalParcelas = TotalParcelas.add(p.getValor());

	if (!totalItens.equals(TotalParcelas))
	    throw new NegocioException(
		    "Total das PARCELAS diferente do total dos ITENS.");

	processaTitulo(titulo);

	return titulos.guardar(titulo);
    }

    private void processaTitulo(Titulo titulo) {
	// ratear itens do titulo para parcela caso persistido e tiver grandes
	// alterações tem que excluir todos os itens das parcelas pra facilitar

	System.out.println();
    }

}
