package esposende.visao.controle.converter;

import esposende.service.CodigoContabilService;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.beans.PropertyEditorSupport;

/**
 * Created with IntelliJ IDEA.
 * User: rodrigo
 * Date: 12/27/12
 * Time: 7:39 PM
 * To change this template use File | Settings | File Templates.
 */
@Component
public class CodigoContabilConverter extends PropertyEditorSupport {

    @Inject
	private CodigoContabilService service;

	@Override
	public void setAsText(String text) {
        super.setValue( (!text.isEmpty()) ? service.findById( Long.valueOf(text) ) : null);
	}
}
