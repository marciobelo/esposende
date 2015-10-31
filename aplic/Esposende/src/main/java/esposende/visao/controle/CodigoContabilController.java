package esposende.visao.controle;

import esposende.entidade.CodigoContabil;
import esposende.service.CodigoContabilService;
import esposende.visao.controle.converter.CodigoContabilConverter;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.util.Map;

@Controller
@RequestMapping("codigocontabil")
public class CodigoContabilController {

    @Inject
    private CodigoContabilConverter converter;

    @Inject
    private CodigoContabilService service;

    @InitBinder
    public void init(ServletRequestDataBinder binder){
        binder.registerCustomEditor(CodigoContabil.class, converter);
    }

    @RequestMapping("lista")
    public String lista(Map model){
        model.put("codigos", service.listAll());
        return "codigocontabil/lista";
    }

    @RequestMapping("edita")
    public String edita(@RequestParam(value = "codigo", required = false) CodigoContabil codigoContabil, Map model){
        if(codigoContabil == null) codigoContabil = new CodigoContabil();

        model.put("codigo", new CodigoContabilModel().setCodigoContabil(codigoContabil));
        return "codigocontabil/form";
    }

    @RequestMapping(value= "edita", method = RequestMethod.POST)
    public String salvar(@Valid @ModelAttribute("codigo") CodigoContabilModel codigo, BindingResult result, Map model){

        if(result.hasErrors()){
            model.put("codigo", codigo);
            return "codigocontabil/form";
        }

        CodigoContabil codigoContabil = codigo.codigoContabil();

        if(codigoContabil.getId() == null){
            service.persist(codigoContabil);
        }   else{
            service.merge(codigoContabil);
        }

        return "redirect:lista";
    }

    @RequestMapping(value = "excluir", method = RequestMethod.POST)
    public String excluir(@RequestParam("id") Long idCodigoContabil) {

        // TODO tratar quando tentar excluir um filho, dar uma exceção genérica
        /*
        com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException: Cannot delete or update a parent row: a foreign key constraint fails (`Esposende`.`Tombamento`, CONSTRAINT `FK_TOMB_CC` FOREIGN KEY (`codigoContabil_id`) REFERENCES `CodigoContabil` (`id`))
         */

        service.excluir( idCodigoContabil );
        return "redirect:lista";
    }


    private static class CodigoContabilModel {

        private Long id;
        @Size(min = 1, message = "O código não pode ser vazio")
        private String codigo;
        @Size(min = 1, message = "A descrição não pode ser vazia")
        private String descricao;

        public CodigoContabilModel setCodigoContabil(CodigoContabil codigoContabil) {
            this.id = codigoContabil.getId();
            this.codigo = codigoContabil.getCodigo();
            this.descricao = codigoContabil.getDescricao();
            return this;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getCodigo() {
            return codigo;
        }

        public void setCodigo(String codigo) {
            this.codigo = codigo;
        }

        public String getDescricao() {
            return descricao;
        }

        public void setDescricao(String descricao) {
            this.descricao = descricao;
        }

        public CodigoContabil codigoContabil() {
            CodigoContabil codigo = new CodigoContabil();
            codigo.setId(this.id);
            codigo.setDescricao(this.descricao);
            codigo.setCodigo(this.codigo);
            return codigo;
        }
    }
}
