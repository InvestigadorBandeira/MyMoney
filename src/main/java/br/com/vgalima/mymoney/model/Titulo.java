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
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

@Entity
public class Titulo implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date data;

    @NotBlank
    @Size(min = 3, max = 50)
    @Column(nullable = false, length = 50)
    private String descricao;

    @OneToMany(mappedBy = "titulo", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Item> itens;

    @OneToMany(mappedBy = "titulo", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Parcela> parcelas;

    @Transient
    private BigDecimal valorTotal = BigDecimal.ZERO;

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public Date getData() {
	return data;
    }

    public void setData(Date data) {
	this.data = data;
    }

    public String getDescricao() {
	return descricao;
    }

    public void setDescricao(String descricao) {
	this.descricao = descricao;
    }

    public List<Item> getItens() {
	return itens;
    }

    public void setItens(List<Item> itens) {
	this.itens = itens;
    }

    public List<Parcela> getParcelas() {
	return parcelas;
    }

    public void setParcelas(List<Parcela> parcelas) {
	this.parcelas = parcelas;
    }

    public BigDecimal getValorTotal() {
	if (itens != null) {
	    valorTotal = BigDecimal.ZERO;
	    for (Item item : itens)
		valorTotal = valorTotal.add(item.getValor());
	}

	return valorTotal;
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
	Titulo other = (Titulo) obj;
	if (id == null) {
	    if (other.id != null)
		return false;
	} else if (!id.equals(other.id))
	    return false;
	return true;
    }

}
