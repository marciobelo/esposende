package esposende.visao.controle;

import esposende.entidade.DocumentoDigital;
import esposende.entidade.Responsavel;
import esposende.service.DocumentoDigitalService;
import esposende.service.ResponsavelService;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.inject.Inject;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

@Controller
public class ResponsavelController {

    @Inject
    private ResponsavelService responsavelService;

    @Inject
    private DocumentoDigitalService documentoDigitalService;

    @RequestMapping("responsavel/listar")
    public String listar(Map<String, List<Responsavel>> model) {
        List<Responsavel> responsaveis = responsavelService.findAll();
        model.put("responsaveis", responsaveis);
        return "responsavel/listar";
    }

    @Transactional @RequestMapping(value = "responsavel/salvar", method = RequestMethod.POST)
    public String salvar(@Valid ResponsavelModel responsavelModel,
                         BindingResult bindingResult) throws IOException {

        /*
        Esse código horroroso teve de ser embutido aqui pois não consegui configurar uma anotação
        do Beans Validation que verificasse se o arquivo de foto foi enviado.
         */
        if( responsavelModel.id == null && !responsavelModel.temFotoResponsavel() ) {
            bindingResult.addError(new FieldError("fotoResponsavel","fotoResponsavel","Deve-se colocar uma foto"));
        }

        if(bindingResult.hasErrors()) return "responsavel/form";

        Responsavel responsavel = responsavelModel.criarResponsavel(documentoDigitalService);

        if (responsavelModel.getId() == null) {
            responsavelService.persist(responsavel);
        } else {
            responsavelService.update(responsavel);
        }
        return "redirect:/responsavel/listar";
    }

    @RequestMapping(value = "responsavel/editar", method = RequestMethod.GET)
    public String editar(@RequestParam(required = false) Long id, Map<String, Object> model) {
        ResponsavelModel responsavelModel;
        if (id != null) {
            Responsavel responsavel =  responsavelService.findById(id);
            responsavelModel = ResponsavelModel.criarComo(responsavel);
        } else {
            responsavelModel = ResponsavelModel.criarVazio();
        }
        model.put("responsavelModel", responsavelModel);
        return "responsavel/form";
    }

    @RequestMapping(value = "responsavel/excluir/{id}", method = RequestMethod.GET)
    public String excluir(@PathVariable long id) {
        Responsavel responsavel = responsavelService.findById(id);
        responsavelService.delete(responsavel);
        return "redirect:/responsavel/listar";
    }

    @RequestMapping(value = "responsavel/responsaveis", method = RequestMethod.GET)
    public String listarResponsaveis(Map model){
        listar(model);
        return "responsavel/responsaveis";
    }

    @RequestMapping(value = "responsavel/gerarRelatorio", method = RequestMethod.GET)
    public void gerarRelatorio(HttpServletRequest request, HttpServletResponse response)
            throws JRException, IOException {

        Resource resource = new ClassPathResource("teste.jasper");

        InputStream relatorioStream = resource.getInputStream();
        List<Responsavel> responsaveis = responsavelService.findAll();
        JRBeanCollectionDataSource jrBean = new JRBeanCollectionDataSource(
                responsaveis);

        ServletOutputStream servletOutputStream = response.getOutputStream();
        JasperRunManager.runReportToPdfStream(relatorioStream,
                servletOutputStream, null, jrBean);
        response.setContentType("application/pdf");
        servletOutputStream.flush();
        servletOutputStream.close();
    }

    private static class ResponsavelModel {

        private Long id;

        @NotNull(message = "Nome é obrigatório")
        @Size(min=3, max= 60, message = "Nome do responsável incorreto")
        @Pattern(regexp = "\\w.*", message = "Nome deve iniciar com letra")
        private String nome;

        @Pattern(regexp = "[0-9]+", message = "Informe apenas números para a matrícula")
        private String matricula;

        @Pattern(regexp = "\\b[a-zA-Z0-9._%-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}\\b", message = "Informe um e-mail válido")
        @NotNull
        private String email;

        private Long idDocumentoDigital;

        private MultipartFile fotoResponsavel;

        private ResponsavelModel(Long id, String nome, String matricula, DocumentoDigital documentoDigital, String email) {
            this.id = id;
            this.nome = nome;
            this.matricula = matricula;
            this.idDocumentoDigital = documentoDigital.getId();
           this.email = email;
        }

        private ResponsavelModel() {

        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getNome() {
            return nome;
        }

        public void setNome(String nome) {
            this.nome = nome;
        }

        public String getMatricula() {
            return matricula;
        }

        public void setMatricula(String matricula) {
            this.matricula = matricula;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public MultipartFile getFotoResponsavel() {
            return fotoResponsavel;
        }

        public void setFotoResponsavel(MultipartFile fotoResponsavel) {
            this.fotoResponsavel = fotoResponsavel;
        }

        public Long getIdDocumentoDigital() {
            return idDocumentoDigital;
        }

        public void setIdDocumentoDigital(Long idDocumentoDigital) {
            this.idDocumentoDigital = idDocumentoDigital;
        }

        public static ResponsavelModel criarComo(Responsavel responsavel) {
            return new ResponsavelModel(responsavel.getId(),
                    responsavel.getNome(),
                    responsavel.getMatricula(),
                    responsavel.getFoto(),
                    responsavel.getEmail());
        }

        public static ResponsavelModel criarVazio() {
            return new ResponsavelModel();
        }

        public Responsavel criarResponsavel(DocumentoDigitalService documentoDigitalService) throws IOException {
            DocumentoDigital documentoDigital;
            if(this.idDocumentoDigital != null) {
                documentoDigital = documentoDigitalService.findById(this.idDocumentoDigital);
                if(this.temFotoResponsavel()) {
                    documentoDigital.setDocumento(this.fotoResponsavel.getBytes());
                }
            } else {
                documentoDigital = new DocumentoDigital("foto", fotoResponsavel.getBytes(), DocumentoDigital.TipoMime.JPEG);
            }

            Responsavel resp = new Responsavel(this.nome,this.matricula,documentoDigital,this.email);
            resp.setId(this.getId());
            return resp;
       }

        private boolean temFotoResponsavel() {
            return this.fotoResponsavel == null ? false : this.fotoResponsavel.getSize() > 0;
        }
    }

}