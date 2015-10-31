package esposende.visao.controle.converter;

import esposende.entidade.BemPermanente;
import esposende.service.BemPermanenteService;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.beans.PropertyEditorSupport;

@Component
public class LongBemPermanenteConverter extends PropertyEditorSupport {
    @Inject
    private BemPermanenteService service;

    @Override
    public void setAsText(String id){
        BemPermanente bem = (!id.isEmpty())? service.findById(Long.parseLong(id)):null;
        super.setValue(bem);
    }
}
