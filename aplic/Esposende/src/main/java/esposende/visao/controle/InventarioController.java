package esposende.visao.controle;

import esposende.entidade.BemPermanente;
import esposende.entidade.Confere;
import esposende.entidade.Inventario;
import esposende.entidade.Origem;
import esposende.entidade.Responsavel;
import esposende.service.BemPermanenteService;
import esposende.service.ConfereService;
import esposende.service.InventarioService;
import esposende.service.ResponsavelService;
import esposende.visao.controle.converter.ConfereConverter;
import esposende.visao.controle.converter.InventarioConverter;
import esposende.visao.controle.converter.LongBemPermanenteConverter;
import esposende.visao.controle.converter.LongOrigemConverter;
import esposende.visao.controle.converter.LongResponsavelConverter;
import esposende.visao.controle.converter.StringDateConverter;
import esposende.visao.controle.formbean.BensEspeciais;
import esposende.visao.controle.util.MontaJasperReports;
import net.sf.jasperreports.engine.JRException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
public class InventarioController {
	@Inject
	private ResponsavelService responsavelService;
	@Inject
	private BemPermanenteService bemPermanenteService;
	@Inject
	private LongOrigemConverter origemConverter;
	@Inject
	private LongResponsavelConverter responsavelConverter;
	@Inject
	private StringDateConverter dateConverter;
	@Inject
	private LongBemPermanenteConverter bemConverter;
	@Inject
	private InventarioConverter inventarioConverter;
	@Inject
	private ConfereConverter confereConverter;
	@Inject
	private InventarioService inventarioService;
	@Inject
	private ConfereService confereService;

	@InitBinder
	protected void initBinder(ServletRequestDataBinder binder) throws Exception {
		binder.registerCustomEditor(Date.class, dateConverter);
		binder.registerCustomEditor(Origem.class, origemConverter);
		binder.registerCustomEditor(Responsavel.class, responsavelConverter);
		binder.registerCustomEditor(BemPermanente.class, bemConverter);
		binder.registerCustomEditor(Inventario.class, inventarioConverter);
		binder.registerCustomEditor(Confere.class, confereConverter);
	}

	@RequestMapping("inventario/responsavel")
	public String inventario(Map model) {
		model.put("responsaveis", responsavelService.findAll());
		return "inventario/responsavel";
	}

	@RequestMapping("inventario/bens/{responsavel}")
	public String inventarioResponsavel(@PathVariable Responsavel responsavel, Map model) {
		List<BemPermanente> bens = bemPermanenteService.listarPorResponsavel(responsavel);
		model.put("bens", bens);
		model.put("situacoes", Confere.TipoSituacaoConfere.values());
		return "inventario/bens";
	}

	@RequestMapping("inventario/cria/{responsavel}")
	public String criaInventario(@PathVariable Responsavel responsavel, Map model) {
		Inventario i = inventarioService.criaNovoInventario(responsavel);
		return "redirect:/inventario/" + i.getId();
	}

	@RequestMapping("inventario/opcoes/{responsavel}")
	public String opcoesDoResponsavel(@PathVariable Responsavel responsavel, Map model) {
		model.put("responsavel", responsavel);
		List<Inventario> inventarios = inventarioService.listarPorResponsavel(responsavel);
		boolean temInventarioEmAberto = false;

		boolean temBensSobResponsabilidade = bemPermanenteService.listarPorResponsavel(responsavel).size() > 0;

		for (Inventario i : inventarios) {
			if (i.getDataFechamento() == null) {
				temInventarioEmAberto = true;
				break;
			}
		}

		model.put("inventarios", inventarios);
		model.put("inventarioAberto", temInventarioEmAberto);
		model.put("temBensSobResponsabilidade", temBensSobResponsabilidade);

		return "inventario/opcoes";
	}

	@RequestMapping("inventario/exibe/{inventario}")
	public String exibeInventario(@PathVariable Inventario inventario, Map model) {
		model.put("inventario", inventario);
		return "inventario/inventario";
	}

	@RequestMapping("inventario/{inventario}")
	public String detalhesInventarios(@PathVariable Inventario inventario, Map model) {
		model.put("inventario", inventario);
		model.put("situacoes", Confere.TipoSituacaoConfere.values());
		return "inventario/detalhes";
	}

	@ResponseBody
	@RequestMapping("inventario/confere/{confere}/{situacao}")
	public String mudaStatusConfere(@PathVariable Confere confere, @PathVariable Confere.TipoSituacaoConfere situacao) {
		confere.setSituacao(situacao);
		confereService.update(confere);
		return "";
	}

	@RequestMapping(value = "inventario/encerrar", method = RequestMethod.POST)
	public String encerra(@RequestParam Inventario inventario, Map model) {
		try {
			inventarioService.encerraInventario(inventario);
			return "redirect:" + inventario.getId();
		} catch (Exception e) {
			model.put("aviso", "O inventário não pode ser fechado. " + e.getMessage());
			return detalhesInventarios(inventario, model);
		}
	}

	@RequestMapping("inventario/relatorio/{inventario}")
	public void geraRelatorio(@PathVariable Inventario inventario, HttpServletResponse response) throws IOException, JRException {

        Responsavel responsavel = responsavelService.obterResposavelInstitucional();

		ServletOutputStream out = response.getOutputStream();
		new MontaJasperReports()
				.setInputStream("inventario")
				.setDataSource(new ArrayList(inventario.getConferidos()))
				.setOutputStream(out)
				.addParametro("responsavelInstitucionalNome", responsavel.getNome())
				.addParametro("responsavelInstitucionalMatricula", responsavel.getMatricula())
				.addParametro("responsavelInstitucionalFoto", new ByteArrayInputStream(responsavel.getFoto().getDocumento()) )
				.addParametro("responsavelNome", inventario.getResponsavel().getNome())
				.addParametro("responsavelMatricula", inventario.getResponsavel().getMatricula())
				.addParametro("responsavelFoto", new ByteArrayInputStream(inventario.getResponsavel().getFoto().getDocumento()))
				.addParametro("protocolo", inventario.getProtocolo().toString())
				.addParametro("dataEmissao", inventario.getDataEmissao())
				.addParametro("dataFechamento", inventario.getDataFechamento())
				.montaRelatorioJasper();


		response.setContentType("application/pdf");
		out.flush();
		out.close();

	}

	@RequestMapping("inventario/naolocalizados")
	public String listaNaoLocalizados(Map model){
		List<BensEspeciais> bens = inventarioService.listaPorSituacao(Confere.TipoSituacaoConfere.NAO_LOCALIZADO);
		model.put("bens", bens);
		model.put("tipo", "Situação");
		model.put("data", new Date());
		model.put("titulo", "Bens Não Localizados");
		return "inicial/benssituacao";
	}

	@RequestMapping("inventario/danificados")
	public String listaDanificados(Map model){
		List<BensEspeciais> bens = inventarioService.listaPorSituacao(Confere.TipoSituacaoConfere.DANIFICADO);
		model.put("bens", bens);
		model.put("tipo", "Situação");
		model.put("data", new Date());
		model.put("titulo", "Bens Danificados");
		return "inicial/benssituacao";
	}
	@RequestMapping("inventario/emdesuso")
	public String listaEmDesuso(Map model){
		List<BensEspeciais> bens = inventarioService.listaPorSituacao(Confere.TipoSituacaoConfere.EM_DESUSO);
		model.put("bens", bens);
		model.put("tipo", "Situação");
		model.put("data", new Date());
		model.put("titulo", "Bens Em Desuso");
		return "inicial/benssituacao";
	}
}
