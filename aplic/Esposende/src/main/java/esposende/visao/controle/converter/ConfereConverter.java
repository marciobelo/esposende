package esposende.visao.controle.converter;

import esposende.entidade.Confere;
import esposende.service.ConfereService;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.beans.PropertyEditorSupport;

@Component
public class ConfereConverter extends PropertyEditorSupport {
    @Inject
    private ConfereService service;

    public void setAsText(String id) {
        Confere o = (!id.isEmpty()) ? service.findById(Long.parseLong(id)) : null;
        super.setValue(o);
    }
}
