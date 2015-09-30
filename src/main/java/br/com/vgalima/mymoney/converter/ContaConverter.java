package br.com.vgalima.mymoney.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.vgalima.mymoney.model.Conta;
import br.com.vgalima.mymoney.repository.Contas;
import br.com.vgalima.mymoney.util.cdi.CDIServiceLocator;

@FacesConverter(forClass = Conta.class)
public class ContaConverter implements Converter {

    // @Inject
    private Contas contas;

    public ContaConverter() {
	this.contas = CDIServiceLocator.getBean(Contas.class);
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component,
	    String value) {
	if (value != null)
	    if (!"".equals(value))
		return contas.porId(new Long(value));

	return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component,
	    Object value) {
	if (value != null) {
	    Conta conta = (Conta) value;
	    return conta.getId() == null ? null : conta.getId().toString();
	}
	return "";
    }

}
