package br.com.vgalima.mymoney.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

import javax.inject.Inject;

import br.com.vgalima.mymoney.model.ItemParcela;
import br.com.vgalima.mymoney.model.ItemTitulo;
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

	int parcelas = titulo.getParcelas().size();

	if (titulo.getId() != null)
	    titulos.removerItemParcela(titulo);

	for (Parcela p : titulo.getParcelas())
	    if (p.getItens() == null)
		p.setItens(new ArrayList<>());

	for (ItemTitulo item : titulo.getItens()) {
	    processaItem(item, titulo);
	}
    }

    private void processaItem(ItemTitulo itemTitulo, Titulo titulo) {
	int parcelas = titulo.getParcelas().size();

	BigDecimal valorItem = BigDecimal.ZERO.add(itemTitulo.getValor());
	BigDecimal qtdeParcelas = new BigDecimal(parcelas);

	BigDecimal valor = valorItem.divide(qtdeParcelas,
		RoundingMode.HALF_EVEN);

	for (Parcela parcela : titulo.getParcelas()) {
	    ItemParcela itemParcela = new ItemParcela();
	    itemParcela.setParcela(parcela);
	    itemParcela.setValor(valor);
	    itemParcela.setDescricao(itemTitulo.getDescricao());
	    itemParcela.setCategoria(itemTitulo.getCategoria());
	    parcela.getItens().add(itemParcela);
	}
    }
}
