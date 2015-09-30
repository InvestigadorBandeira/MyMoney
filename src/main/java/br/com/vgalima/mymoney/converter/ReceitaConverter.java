package br.com.vgalima.mymoney.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.vgalima.mymoney.model.Receita;
import br.com.vgalima.mymoney.repository.Receitas;
import br.com.vgalima.mymoney.util.cdi.CDIServiceLocator;

@FacesConverter(forClass = Receita.class)
public class ReceitaConverter implements Converter {

    // @Inject
    private Receitas receitas;

    public ReceitaConverter() {
	this.receitas = CDIServiceLocator.getBean(Receitas.class);
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component,
	    String value) {
	if (value != null)
	    if (!"".equals(value))
		return receitas.porId(new Long(value));

	return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component,
	    Object value) {
	if (value != null) {
	    Receita receita = (Receita) value;
	    return receita.getId() == null ? null : receita.getId().toString();
	}
	return "";
    }
}
