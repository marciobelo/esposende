package esposende.visao.controle.converter;

import esposende.entidade.Origem;
import esposende.service.OrigemService;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.beans.PropertyEditorSupport;

@Component
public class LongOrigemConverter extends PropertyEditorSupport {
    @Inject
    private OrigemService service;

    public void setAsText(String id){
        Origem origem = (!id.isEmpty())? service.findById(Long.parseLong(id)):null;
        super.setValue(origem);
    }

}
