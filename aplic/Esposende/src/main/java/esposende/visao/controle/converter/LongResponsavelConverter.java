package esposende.visao.controle.converter;

import esposende.entidade.Responsavel;
import esposende.service.ResponsavelService;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.beans.PropertyEditorSupport;

@Component
public class LongResponsavelConverter extends PropertyEditorSupport{

    @Inject
    private ResponsavelService service;

    public void setAsText(String id){
        Responsavel responsavel = (!id.isEmpty())? service.findById(Long.parseLong(id)):null;
        super.setValue(responsavel);
    }

}
