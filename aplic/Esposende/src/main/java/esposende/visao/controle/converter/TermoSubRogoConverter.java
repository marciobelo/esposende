package esposende.visao.controle.converter;

import esposende.entidade.TermoSubRogo;
import esposende.service.TermoSubRogoService;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.beans.PropertyEditorSupport;

@Component
public class TermoSubRogoConverter extends PropertyEditorSupport {

	@Inject
	private TermoSubRogoService service;

	@Override
	public void setAsText(String id) {
		TermoSubRogo termo = (!id.isEmpty()) ? service.obterPorId(Long.parseLong(id)) : null;
		super.setValue(termo);
	}

}
