package br.com.vgalima.mymoney.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.vgalima.mymoney.model.Titulo;
import br.com.vgalima.mymoney.repository.Titulos;
import br.com.vgalima.mymoney.util.cdi.CDIServiceLocator;

@FacesConverter(forClass = Titulo.class)
public class TituloConverter implements Converter {

    // @Inject
    private Titulos titulos;

    public TituloConverter() {
	this.titulos = CDIServiceLocator.getBean(Titulos.class);
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component,
	    String value) {
	if (value != null)
	    if (!"".equals(value))
		return titulos.porId(new Long(value));

	return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component,
	    Object value) {
	if (value != null) {
	    Titulo titulo = (Titulo) value;
	    return titulo.getId() == null ? null : titulo.getId().toString();
	}
	return "";
    }
}
