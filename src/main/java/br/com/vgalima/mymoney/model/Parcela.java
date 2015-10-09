package br.com.vgalima.mymoney.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

@Entity
public class Parcela implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @Temporal(TemporalType.DATE)
    @Column(name = "data_vencimento", nullable = false)
    private Date dataVencimento;

    @NotNull
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal valor = BigDecimal.ZERO;

    @NotNull
    @Column(precision = 12, scale = 2)
    private BigDecimal acrescimo = BigDecimal.ZERO;

    @NotNull
    @Column(precision = 12, scale = 2)
    private BigDecimal desconto = BigDecimal.ZERO;

    @Column(nullable = false)
    private Boolean paga = false;

    @OneToMany(mappedBy = "parcela", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Item> itens;

    @ManyToOne
    private Titulo titulo;

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public Date getDataVencimento() {
	return dataVencimento;
    }

    public void setDataVencimento(Date dataVencimento) {
	this.dataVencimento = dataVencimento;
    }

    public BigDecimal getValor() {
	return valor;
    }

    public void setValor(BigDecimal valor) {
	this.valor = valor;
    }

    public BigDecimal getAcrescimo() {
	return acrescimo;
    }

    public void setAcrescimo(BigDecimal acrescimo) {
	this.acrescimo = acrescimo;
    }

    public BigDecimal getDesconto() {
	return desconto;
    }

    public void setDesconto(BigDecimal desconto) {
	this.desconto = desconto;
    }

    public Boolean getPaga() {
	return paga;
    }

    public void setPaga(Boolean paga) {
	this.paga = paga;
    }

    public List<Item> getItens() {
	return itens;
    }

    public void setItens(List<Item> itens) {
	this.itens = itens;
    }

    public Titulo getTitulo() {
	return titulo;
    }

    public void setTitulo(Titulo titulo) {
	this.titulo = titulo;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((id == null) ? 0 : id.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	Parcela other = (Parcela) obj;
	if (id == null) {
	    if (other.id != null)
		return false;
	} else if (!id.equals(other.id))
	    return false;
	return true;
    }

}