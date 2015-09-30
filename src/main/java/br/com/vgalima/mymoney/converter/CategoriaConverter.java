package br.com.vgalima.mymoney.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.vgalima.mymoney.model.Categoria;
import br.com.vgalima.mymoney.repository.Categorias;
import br.com.vgalima.mymoney.util.cdi.CDIServiceLocator;

@FacesConverter(forClass = Categoria.class)
public class CategoriaConverter implements Converter {

    // @Inject
    private Categorias categorias;

    public CategoriaConverter() {
	this.categorias = CDIServiceLocator.getBean(Categorias.class);
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component,
	    String value) {
	if (value != null)
	    if (!"".equals(value))
		return categorias.porId(new Long(value));

	return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component,
	    Object value) {
	if (value != null) {
	    Categoria categoria = (Categoria) value;
	    return categoria.getId() == null ? null : categoria.getId()
		    .toString();
	}
	return "";
    }

}
