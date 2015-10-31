package esposende.visao.controle.converter;

import esposende.entidade.Inventario;
import esposende.service.InventarioService;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.beans.PropertyEditorSupport;

@Component
public class InventarioConverter extends PropertyEditorSupport {

    @Inject
    private InventarioService service;

    public void setAsText(String id) {
        Inventario o = (!id.isEmpty()) ? service.findById(Long.parseLong(id)) : null;
        super.setValue(o);
    }

}
