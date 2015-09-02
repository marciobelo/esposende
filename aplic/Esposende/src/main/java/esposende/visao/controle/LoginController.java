package esposende.visao.controle;

import esposende.entidade.Confere;
import esposende.entidade.Configuracoes;
import esposende.entidade.Responsavel;
import esposende.service.*;
import esposende.visao.controle.converter.LongResponsavelConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.inject.Inject;
import java.util.Map;

@Controller
public class LoginController {

    private static final Logger LOG = LoggerFactory.getLogger( LoginController.class );

	@Inject
	private BemPermanenteService bemPermanenteService;
	@Inject
	private TermoSubRogoService termoSubRogoService;
	@Inject
	private InventarioService inventarioService;
	@Inject
	private ConfiguracoesService configuracoesService;
	@Inject
	private ResponsavelService responsavelService;
	@Inject
	private LongResponsavelConverter responsavelConverter;

	@InitBinder
	protected void initBinder(ServletRequestDataBinder binder) throws Exception {
		binder.registerCustomEditor(Responsavel.class, responsavelConverter);
	}

	@RequestMapping({"/login"})
	public String mostrarTelaLogin() {
		return "inicial/login";
	}

	@RequestMapping({"/"})
	public String mostrarTelaInicial(Map model) {

        LOG.info("iniciando a exibiação do painel principal");

		Long totalbens = bemPermanenteService.getTotalbens();
		model.put("totalBens", totalbens);

		model.put("subRogados", termoSubRogoService.numeroSubRogados());

		Long bensPatrimoniados = bemPermanenteService.contarBensPatrimoniados();
		model.put("patrimoniados", bensPatrimoniados);

		model.put("naoPatrimoniados", bemPermanenteService.contarBensNaoPatrimoniados());

		model.put("naoLocalizados", bemPermanenteService.contaPorSitucaoConfere(Confere.TipoSituacaoConfere.NAO_LOCALIZADO));
		model.put("danificados", bemPermanenteService.contaPorSitucaoConfere(Confere.TipoSituacaoConfere.DANIFICADO));
		model.put("emdesuso", bemPermanenteService.contaPorSitucaoConfere(Confere.TipoSituacaoConfere.EM_DESUSO));

		model.put("bensNaoLocalizados", bemPermanenteService.listaNaoLocalizados());

		model.put("inventariosAbertos", inventarioService.listaEmAberto());

		model.put("inventariosAtraso", inventarioService.bensInventarioAtraso());

		model.put("semFotos", bemPermanenteService.listarBensSemFotos());

		model.put("semDocumentoAquisicao", bemPermanenteService.listarBensSemDocumentosAquisicao());

		model.put("embaixa", bemPermanenteService.contarBensEmBaixa());

		model.put("baixados", bemPermanenteService.contarBensBaixados());

		return "inicial/inicial";
	}

	@RequestMapping("/configuracoes")
	public String configuracoes(Map model) {
		model.put("responsaveis", responsavelService.findAll());
		model.put("configuracoes", configuracoesService.getConfiguracoes());
		return "inicial/configuracoes";
	}

	@RequestMapping(value = "/configuracoes", method = RequestMethod.POST)
	public String salva(@RequestParam Responsavel responsavel) {
		Configuracoes c = configuracoesService.getConfiguracoes();
		c.setResponsavelInstitucional(responsavel);
		configuracoesService.merge(c);
		return "redirect:configuracoes";
	}

}
