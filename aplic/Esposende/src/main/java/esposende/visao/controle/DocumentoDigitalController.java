package esposende.visao.controle;

import esposende.entidade.DocumentoDigital;
import esposende.entidade.TermoSubRogo;
import esposende.service.*;
import net.sf.jmimemagic.Magic;
import net.sf.jmimemagic.MagicException;
import net.sf.jmimemagic.MagicMatchNotFoundException;
import net.sf.jmimemagic.MagicParseException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Controller
public class DocumentoDigitalController {

	@Inject
	private DocumentoDigitalService documentoDigitalService;
	@Inject
	private TermoSubRogoService termoSubRogoService;
	@Inject
	private BemPermanenteService bemPermanenteService;
	@Inject
	private InventarioService inventarioService;
	@Inject
	private TombamentoService tombamentoService;
	@Inject
	private BaixaService baixaService;

	@RequestMapping("documentoDigital/{idDocumento}")
	public void exibeImagem(@PathVariable Long idDocumento, HttpServletResponse response) throws MagicParseException, MagicException, MagicMatchNotFoundException, IOException {
		byte[] imagem = documentoDigitalService.findById(idDocumento).getDocumento();

		if (imagem != null && imagem.length > 0) {
			String mimeType = Magic.getMagicMatch(imagem).getMimeType();
			response.setContentType(mimeType);
			response.getOutputStream().write(imagem);
		}
	}

	@RequestMapping("documentoDigital/formUpload/{classeDestino}/{id}")
	public String formUpload(@PathVariable String classeDestino, @PathVariable Long id, Map<String, Object> model) {
		model.put("documentoDigitalModel", new DocumentoDigitalModel(classeDestino, id));
		return "documentoDigital/formUpload";
	}

	@RequestMapping("documentoDigital/excluir/{classeDestino}/{id}/{idDocumentoDigital}")
	public String excluir(@PathVariable String classeDestino, @PathVariable Long id,
	                      @PathVariable Long idDocumentoDigital) {
		documentoDigitalService.excluir(classeDestino, id, idDocumentoDigital);
		return "documentoDigital/vazio"; // não entendi porque preciso passar uma view vazia. Caso contrário ele retornava erro 404.
	}

	@RequestMapping("documentoDigital/upload")
	public String upload(@Valid DocumentoDigitalModel documentoDigitalModel,
	                     Map<String, Object> model) throws IOException {

		documentoDigitalService.adicionarImagem(documentoDigitalModel.getClasseDestino(),
				documentoDigitalModel.getId(),
				documentoDigitalModel.getDocumento().getBytes());

		model.put("arquivoRecebido", documentoDigitalModel.getDocumento().getOriginalFilename());
		return "documentoDigital/uploadSucesso";
	}

	@RequestMapping("documentoDigital/listar/{classeDestino}/{id}")
	public String listar(Map<String, Object> model, @PathVariable("classeDestino") String classeDestino, @PathVariable("id") Long id) {
		Set<DocumentoDigital> documentos;
		if ("TermoSubRogo".equals(classeDestino)) {
			TermoSubRogo termoSubRogo = termoSubRogoService.obterPorId(id);
			documentos = termoSubRogo.getTermosAssinados();
		} else if ("BemPermanente".equals(classeDestino)) {
			documentos = new HashSet<DocumentoDigital>(bemPermanenteService.findById(id).getFotos());
		} else if ("Inventario".equals(classeDestino)) {
			documentos = inventarioService.findById(id).getRelatorioAssinado();
		} else if ("Tombamento".equals(classeDestino)) {
			documentos = tombamentoService.findById(id).getComprovantesOperacao();
		} else if ("Baixa".equals(classeDestino)) {
			documentos = baixaService.findById(id).getComprovantes();
		} else {
			throw new IllegalStateException(String.format("Nao existe classe %s", classeDestino));
		}
		model.put("documentosDigital", documentos);
		model.put("classe", classeDestino);
		return "documentoDigital/listar";
	}

	@RequestMapping("documentoDigital/visualizar/{documento}")
	public String visualizar(@PathVariable Long documento, Map model) {
		model.put("documento", documento);
		return "documentoDigital/visualizar";
	}

	public static class DocumentoDigitalModel {

		private MultipartFile documento;
		private String classeDestino;
		private Long id;

		public DocumentoDigitalModel(String classeDestino, Long id) {
			this.classeDestino = classeDestino;
			this.id = id;
		}

		public DocumentoDigitalModel() {
		}

		public MultipartFile getDocumento() {
			return documento;
		}

		public void setDocumento(MultipartFile documento) {
			this.documento = documento;
		}

		public String getClasseDestino() {
			return classeDestino;
		}

		public void setClasseDestino(String classeDestino) {
			this.classeDestino = classeDestino;
		}

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}
	}

}
