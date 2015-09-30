package br.com.vgalima.mymoney.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.vgalima.mymoney.model.Transferencia;
import br.com.vgalima.mymoney.repository.Transferencias;
import br.com.vgalima.mymoney.util.cdi.CDIServiceLocator;

@FacesConverter(forClass = Transferencia.class)
public class TransferenciaConverter implements Converter {

    // @Inject
    private Transferencias transferencias;

    public TransferenciaConverter() {
	this.transferencias = CDIServiceLocator.getBean(Transferencias.class);
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component,
	    String value) {
	if (value != null)
	    if (!"".equals(value))
		return transferencias.porId(new Long(value));

	return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component,
	    Object value) {
	if (value != null) {
	    Transferencia transferencia = (Transferencia) value;
	    return transferencia.getId() == null ? null : transferencia.getId()
		    .toString();
	}
	return "";
    }
}
