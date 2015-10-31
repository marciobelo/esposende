package esposende.visao.controle.converter;

import esposende.entidade.LocalPermanencia;
import esposende.service.LocalPermanenciaService;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.beans.PropertyEditorSupport;

@Component
public class LocalPermanenciaConverter extends PropertyEditorSupport {
	@Inject
	private LocalPermanenciaService service;

	public void setAsText(String id) {
		LocalPermanencia local = (!id.isEmpty()) ? service.findById(Long.parseLong(id)) : null;
		super.setValue(local);
	}

}
