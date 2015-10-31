package esposende.visao.controle.converter;

import esposende.entidade.Baixa;
import esposende.service.BaixaService;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.beans.PropertyEditorSupport;

@Component
public class BaixaConverter extends PropertyEditorSupport{

    @Inject
    private BaixaService service;

    @Override
    public void setAsText(String id){
        Baixa termo = (!id.isEmpty())? service.findById(Long.parseLong(id)):null;
        super.setValue(termo);
    }

}
