package esposende.visao.controle;

import esposende.entidade.*;
import esposende.entidade.util.BemPermanenteDiff;
import esposende.entidade.util.CalendarUtil;
import esposende.service.*;
import esposende.visao.controle.converter.*;
import esposende.visao.controle.formbean.BemPermanenteModel;
import esposende.visao.controle.formbean.BensEspeciais;
import esposende.visao.controle.formbean.Modelo12;
import esposende.visao.controle.util.MontaJasperReports;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jmimemagic.Magic;
import org.hibernate.cfg.PropertyInferredData;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.inject.Inject;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class BemPermanenteController {

	@Inject
	private BemPermanenteService bemPermanenteService;

	@Inject
	private ResponsavelService responsavelService;

	@Inject
	private OrigemService origemService;

	@Inject
	private TombamentoService tombamentoService;

	@Inject
	private CodigoContabilService codigoContabilService;

	@Inject
	private MontaJasperReports jasper;

	@Inject
	private LongOrigemConverter origemConverter;

	@Inject
	private LongResponsavelConverter responsavelConverter;

	@Inject
	private StringDateConverter dateConverter;

	@Inject
	private LongBemPermanenteConverter bemConverter;

	@Inject
	private CodigoContabilConverter codigoContabilConverter;

	@Inject
	private LocalPermanenciaConverter localPermanenciaConverter;

	@Inject
	private LocalPermanenciaService localPermanenciaService;

	@Inject
	private StringBigDecimalConverter stringBigDecimalConverter;

	@Inject
	private InventarioService inventarioService;

	@Inject
	private TermoSubRogoService termoSubRogoService;

	@Inject
	private ConfiguracoesService configuracoesService;

	@InitBinder
	protected void initBinder(HttpServletRequest request,
	                          ServletRequestDataBinder binder) throws Exception {
		binder.registerCustomEditor(Date.class, dateConverter);
		binder.registerCustomEditor(Origem.class, origemConverter);
		binder.registerCustomEditor(Responsavel.class, responsavelConverter);
		binder.registerCustomEditor(BemPermanente.class, bemConverter);
		binder.registerCustomEditor(CodigoContabil.class, codigoContabilConverter);
		binder.registerCustomEditor(LocalPermanencia.class, localPermanenciaConverter);
		binder.registerCustomEditor(BigDecimal.class, stringBigDecimalConverter);
	}

	@RequestMapping("bempermanente/listar")
	public String listar(Map<String, Object> model) {
		List<Responsavel> responsaveis = responsavelService.findAll();
		List<Origem> origens = origemService.findAll();
		List<LocalPermanencia> locais = localPermanenciaService.listar();
		model.put("locais", locais);
		model.put("responsaveis", responsaveis);
		model.put("origens", origens);
		return "bemPermanente/listar";
	}

	@RequestMapping("bempermanente/listarPorResponsavel")
	public String listarPorResponsavel(Map<String, Object> model) {
		List<Responsavel> responsaveis = responsavelService.findAll();
		model.put("responsaveis", responsaveis);
		return "bemPermanente/listarPorResponsavel";
	}

	@RequestMapping(value = "bempermanente/listarBensDoResponsavel/{responsavel}", method = RequestMethod.GET)
	public String listarBensDoResponsavel(@PathVariable Responsavel responsavel,
	                                      Map<String, Object> model) {
		//Responsavel responsavel = responsavelService.findById(responsavel);
		List<BemPermanente> bens = bemPermanenteService
				.listarPorResponsavel(responsavel);
		model.put("bens", bens);
		return "bemPermanente/listaBens";
	}

	@RequestMapping("bempermanente/novo")
	public String novo(Map<String, Object> model) {
		BemPermanenteModel bem = new BemPermanenteModel();
		bem.setResponsavel(configuracoesService.getConfiguracoes().getResponsavelInstitucional());
		bem.setCodigosContabeis(codigoContabilService.listAll());
		bem.setLocaisPermanencia(localPermanenciaService.listar());
		bem.setResponsaveis(responsavelService.findAll());
		bem.setOrigens(origemService.findAll());

		model.put("bem", bem);
		return "bemPermanente/form";
	}

	@RequestMapping(value = "bempermanente/pesquisa", method = RequestMethod.POST)
	public String pesquisa(@RequestParam String descricaoBem, @RequestParam String codigoTombamento,
	                       @RequestParam Origem origem, @RequestParam Responsavel responsavel,
	                       @RequestParam LocalPermanencia local,
	                       Map<String, Object> model) {

		List<BemPermanente> bens = bemPermanenteService.listarPorCriterios(descricaoBem, origem, responsavel, codigoTombamento, local);
		List<Object[]> saida = new ArrayList<Object[]>();

		for (BemPermanente b : bens) {
			Object[] o = new Object[3];
			o[0] = b;
			o[1] = inventarioService.buscaUltimoInventario(b);
			o[2] = termoSubRogoService.buscaSubRogoVigente(b);
			saida.add(o);
		}

		model.put("bens", saida);
		return "bemPermanente/listaBens";
	}


	@RequestMapping(value = "bempermanente/pesquisa", method = RequestMethod.GET)
	public String impressao(@RequestParam String descricaoBem, @RequestParam String codigoTombamento,
	                        @RequestParam Origem origem, @RequestParam Responsavel responsavel,
	                        @RequestParam LocalPermanencia local,
	                        Map<String, Object> model) {
		pesquisa(descricaoBem, codigoTombamento, origem, responsavel, local, model);
		model.put("data", new Date());
		model.put("descricao", descricaoBem.isEmpty() ? "Todos" : descricaoBem);
		model.put("tombamento", codigoTombamento.isEmpty() ? "Todos" : codigoTombamento);
		model.put("origem", origem == null ? "Todas" : origem.getResumo());
		model.put("responsavel", responsavel == null ? "Todos" : responsavel.getNome());
		model.put("local", local == null ? "Todos" : local.getNome());
		return "bemPermanente/print";
	}

	@RequestMapping(value = "bempermanente/novo", method = RequestMethod.POST)
	public String novo(@ModelAttribute("bem") BemPermanenteModel bem, BindingResult result) throws Exception {

		if (result.hasErrors()) {
			preencheListasAuxiliares(bem);
			return "bemPermanente/form";
		}

		BemPermanente bemPermanente = bem.getBemPermanenteModel();

		bemPermanenteService.persist(bemPermanente);
		return "redirect:/bempermanente/editar?bemPermanente=" + bemPermanente.getId();
	}

	@RequestMapping(value = "bempermanente/editar", method = RequestMethod.GET)
	public String editar(@RequestParam BemPermanente bemPermanente,
	                     Map<String, Object> model) {
		BemPermanenteModel bem = new BemPermanenteModel( bemPermanente );
		preencheListasAuxiliares(bem);
		model.put("bem", bem);
		return "bemPermanente/form";
	}

	@RequestMapping(value = "bempermanente/editar", method = RequestMethod.POST)
	public String editar(@Valid BemPermanenteModel bem, BindingResult result)
			throws Exception {

		if( result.hasErrors() ) {
			preencheListasAuxiliares(bem);
			return "bemPermanente/form";
		}

		BemPermanente bemPermanente = bem.getBemPermanenteModel();

		BemPermanente bemOriginal = bemPermanenteService.findById(bem.getId());

		bemPermanente.setBaixa(bemOriginal.getBaixa());

		for (DocumentoDigital d : bemOriginal.getFotos()) {
			bemPermanente.adicionarFoto(d);
		}

		if (bemPermanente.getRegistrosOcorrencia().isEmpty()) {
			for (RegistroOcorrencia ocorrencia : bemOriginal.getRegistrosOcorrencia()) {
				bemPermanente.adicionarRegistroOcorrencia(ocorrencia);
			}
		}

		Map<String, TipoRegistroOcorrencia> campos = new HashMap<String, TipoRegistroOcorrencia>();
		campos.put("descricao", TipoRegistroOcorrencia.ALTERACAO_DESCRICAO);
		campos.put("origem", TipoRegistroOcorrencia.ALTERACAO_ORIGEM);
		campos.put("responsavel", TipoRegistroOcorrencia.ALTERACAO_RESPONSAVEL);
		campos.put("localPermanencia", TipoRegistroOcorrencia.ALTERACAO_LOCAL);

		Map<TipoRegistroOcorrencia, String> diffs = new BemPermanenteDiff()
				.diff(bemOriginal, bemPermanente, campos);

		for (TipoRegistroOcorrencia diff : diffs.keySet()) {
			RegistroOcorrencia registro = new RegistroOcorrencia(diff, diffs.get(diff));
			bemPermanente.adicionarRegistroOcorrencia(registro);
		}

		bemPermanenteService.update(bemPermanente);
		return "redirect:/bempermanente/listar";
	}

	private void preencheListasAuxiliares(BemPermanenteModel bem) {
		bem.setResponsaveis(responsavelService.findAll());
		bem.setCodigosContabeis(codigoContabilService.listAll());
		bem.setOrigens(origemService.findAll());
		bem.setLocaisPermanencia(localPermanenciaService.listar());
	}

	@RequestMapping("bempermanente/busca")
	public String busca(Map<String, Object> model) {

		return "bemPermanente/busca";
	}

	@RequestMapping("bempermanente/detalhes/{idBemPermanente}")
	public String detalhes(@PathVariable long idBemPermanente,
	                       Map<String, Object> model) {
		BemPermanente bem = bemPermanenteService.findById(idBemPermanente);
		model.put("bem", bem);
		return "bemPermanente/detalhes";
	}

	@RequestMapping(value = "bempermanente/autocomplete/descricao/", method = RequestMethod.GET)
	public String descricoes(
			@RequestParam(value = "term") String parteDescricao,
			Map<String, Object> model) {
		List<String> descricoes = bemPermanenteService
				.listaDescricoes(parteDescricao);
		model.put("valores", descricoes);
		return "bemPermanente/autoComplete";
	}

	@RequestMapping(value = "bempermanente/autocomplete/empresa/", method = RequestMethod.GET)
	public String empresas(@RequestParam(value = "term") String empresa,
	                       Map<String, Object> model) {
		List<String> empresas = bemPermanenteService.listaEmpresas(empresa);
		model.put("valores", empresas);
		return "bemPermanente/autoComplete";
	}

	@RequestMapping(value = "bempermanente/autocomplete/documento/", method = RequestMethod.GET)
	public String documentos(@RequestParam(value = "term") String documento,
	                         Map<String, Object> model) {
		List<String> documentos = bemPermanenteService
				.listaDocumentos(documento);
		model.put("valores", documentos);
		return "bemPermanente/autoComplete";
	}

	@RequestMapping(value = "bempermanente/autocomplete/tombamento/", method = RequestMethod.GET)
	public String tombamento(@RequestParam(value = "term") String tombamento,
	                         Map<String, Object> model) {
		List<String> tombamentos = bemPermanenteService
				.listaTombamentos(tombamento);
		model.put("valores", tombamentos);
		return "bemPermanente/autoComplete";
	}

	@RequestMapping(value = "bempermanente/pesquisa/relatorio", method = RequestMethod.POST)
	public void pesquisa(
			@RequestParam String descricaoBem,
			@RequestParam String codigoTombamento,
			@RequestParam String nomeEmpresa,
			@RequestParam String documentoAquisicao,
			@RequestParam Origem origem,
			@RequestParam Responsavel responsavel,
			@RequestParam Date dataTombamento,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		List<BemPermanente> bens = bemPermanenteService.listarPorCriterios(
				descricaoBem, origem, responsavel, codigoTombamento, null);

		Resource resource = new ClassPathResource("bem-permanente.jasper");

		InputStream relatorioStream = resource.getInputStream();
		JRBeanCollectionDataSource jrBean = new JRBeanCollectionDataSource(bens);

		ServletOutputStream servletOutputStream = response.getOutputStream();

		Map<String, Object> parametros = new HashMap<String, Object>();

		parametros.put("descricao", descricaoBem);
		parametros.put("codigoTombamento", codigoTombamento);
		parametros.put("dataTombamento", dataTombamento);
		parametros.put("origem", (origem == null) ? "" : origem.getResumo());
		parametros.put("responsavel", (responsavel == null) ? "" : responsavel.getNome());
		parametros.put("empresa", nomeEmpresa);
		parametros.put("documentoAquisicao", documentoAquisicao);

		JasperRunManager.runReportToPdfStream(relatorioStream,
				servletOutputStream, parametros, jrBean);

		// OutputStream os = relServico.

		response.setContentType("application/pdf");
		servletOutputStream.flush();
		servletOutputStream.close();
	}

	@RequestMapping(value = "bempermanente/pesquisa/relatorio2", method = RequestMethod.POST)
	public void pesquisa(
			@RequestParam String descricaoBem,
			@RequestParam String codigoTombamento,
			@RequestParam String nomeEmpresa,
			@RequestParam String documentoAquisicao,
			@RequestParam Origem origem,
			@RequestParam Responsavel responsavel,
			@RequestParam String dataTombamento,
			HttpServletResponse response)
			throws Exception {

		Date data = (!dataTombamento.isEmpty()) ? new SimpleDateFormat(
				"dd/MM/yyyy").parse(dataTombamento) : null;

		List<BemPermanente> bens = bemPermanenteService.listarPorCriterios(
				descricaoBem, origem, responsavel, codigoTombamento, null);

		ServletOutputStream servletOutputStream = response.getOutputStream();
		this.jasper.setInputStream("bem-permanente").setDataSource(bens).setOutputStream(servletOutputStream)
				.addParametro("descricao", descricaoBem).addParametro("codigoTombamento", codigoTombamento)
				.addParametro("dataTombamento", data).addParametro("origem", (origem != null) ? origem.getResumo() : "")
				.addParametro("responsavel", (responsavel != null) ? responsavel.getNome() : "")
				.addParametro("empresa", nomeEmpresa).addParametro("documentoAquisicao", documentoAquisicao)
				.montaRelatorioJasper();

		response.setContentType("application/pdf");
		servletOutputStream.flush();
		servletOutputStream.close();
	}

	@RequestMapping("/bempermanente/bem/foto/{bem}")
	public void exibeFoto(@PathVariable BemPermanente bem, HttpServletResponse response)
			throws Exception {

		byte[] imagem = bem.getFoto();

		if (imagem != null && imagem.length > 0) {
			String mimeType = Magic.getMagicMatch(imagem).getMimeType();
			response.setContentType(mimeType);
			response.getOutputStream().write(imagem);
		}
	}

	@RequestMapping(value = "bempermanente/bem/adicionafoto/{bem}", method = RequestMethod.POST)
	public String upload(@PathVariable BemPermanente bem, @RequestParam MultipartFile foto) throws IOException {
		bem.setFoto(foto.getBytes());
		bemPermanenteService.update(bem);
		return "redirect:/bempermanente/listar";
	}

	@RequestMapping(value = "bempermanente/ficha/{bem}")
	public void fichaBemPermanente(@PathVariable BemPermanente bem, HttpServletResponse response) throws IOException, JRException {
		ServletOutputStream servletOutputStream = response.getOutputStream();
		this.jasper.setInputStream("ficha-individual")
				.setOutputStream(servletOutputStream)
				.setDataSource(new ArrayList<BemPermanente>())
				.addParametro("nome", bem.getDescricao())
				.addParametro("dia", Calendar.getInstance().getTime())
				.addParametro("valor", bem.getTombamento().getValorOperacao())
				.addParametro("dataTombamento", bem.getTombamento() != null ? bem.getTombamento().getDataTombamento() : null)
				.addParametro("tombamento", bem.getTombamento() != null ? bem.getTombamento().getCodTombamento() : "")
				.addParametro("origem", (bem.getOrigem() != null) ? bem.getOrigem().getResumo() : "")
				.addParametro("dataAquisicao", bem.getTombamento().getDataTombamento())
				.addParametro("documentoHabil", bem.getTombamento().getDocumentoHabil())
				.addParametro("historicoOperacao", bem.getTombamento().getHistoricoOperacao())
				.montaRelatorioJasper();
		response.setContentType("application/pdf");
		servletOutputStream.flush();
		servletOutputStream.close();
	}

	@RequestMapping(value = "bempermanente/modelo11")
	public String modelo11(Integer ano, Integer mes, Map model) throws IOException, JRException {
		model.put("bens", bemPermanenteService.listaPorDataTombamento(ano, mes));
		model.put("ano", ano);
		model.put("mes", mes);
		return "bemPermanente/modelo11";
	}

	@RequestMapping(value = "bempermanente/modelo11/download")
	public void geraModelo11(Integer ano, Integer mes, HttpServletResponse response) throws IOException, JRException {
		ServletOutputStream servletOutputStream = response.getOutputStream();

		Calendar data = Calendar.getInstance();
		data.set(Calendar.YEAR, ano);
		data.set(Calendar.MONTH, mes - 1);
		data.set(Calendar.DAY_OF_MONTH, data.getActualMaximum(Calendar.DAY_OF_MONTH));

		this.jasper.setInputStream("modelo11")
				.setOutputStream(servletOutputStream)
				.setDataSource(bemPermanenteService.listaPorDataTombamento(ano, mes))
				.addParametro("mes", mes)
				.addParametro("ano", ano)
				.addParametro("data", data.getTime())
				.montaRelatorioJasper();

		response.setContentType("application/pdf");
		servletOutputStream.flush();
		servletOutputStream.close();
	}

	@RequestMapping(value = "bempermanente/modelo12")
	public String modelo12(Integer ano, Integer mes, Map model) throws IOException, JRException {
		ano = ano != null ? ano : Calendar.getInstance().get(Calendar.YEAR);
		mes = mes != null ? mes : Calendar.getInstance().get(Calendar.MONTH) + 1;
		List<Modelo12> modelo12 = codigoContabilService.listaParaModelo12(ano, mes);

		BigDecimal totalInicial = BigDecimal.ZERO;
		BigDecimal totalFinal = BigDecimal.ZERO;

		for (Modelo12 m : modelo12) {
			totalInicial = totalInicial.add(m.getSaldoInicial());
			totalFinal = totalFinal.add(m.getSaldoFinal());
		}

		model.put("anterior", new CalendarUtil().ultimoDiaMesAnterior(ano, mes));
		model.put("atual", new CalendarUtil().ultimoDiaMes(ano, mes));
		model.put("totalinicial", totalInicial);
		model.put("totalfinal", totalFinal);
		model.put("ano", ano);
		model.put("mes", mes);
		model.put("codigos", modelo12);

		return "bemPermanente/modelo12";
	}

	@RequestMapping(value = "bempermanente/modelo12/download")
	public void geraModelo12(Integer ano, Integer mes, Map model, HttpServletResponse response) throws IOException, JRException {
		ServletOutputStream servletOutputStream = response.getOutputStream();

		modelo12(ano, mes, model);

		this.jasper.setInputStream("modelo12")
				.addParametro("anterior", ((Calendar) model.get("anterior")).getTime())
				.addParametro("atual", ((Calendar) model.get("atual")).getTime())
				.addParametro("totalinicial", model.get("totalinicial"))
				.addParametro("totalfinal", model.get("totalfinal"))
				.setOutputStream(servletOutputStream)
				.setDataSource((Collection) model.get("codigos"))
				.montaRelatorioJasper();

		response.setContentType("application/pdf");
		servletOutputStream.flush();
		servletOutputStream.close();
	}

	@RequestMapping("bempermanente/naopatrimoniados")
	public String listaNaoPatrimoniados(Map model) {
		List<BemPermanente> bens = bemPermanenteService.listarNaoPatrimoniados();
		List<BensEspeciais> especiais = new ArrayList<BensEspeciais>();

		for (BemPermanente bem : bens) {
			especiais.add(new BensEspeciais(bem, null, null));
		}

		model.put("bens", especiais);
		model.put("tipo", "Não Patrimoniado");
		model.put("data", new Date());
		model.put("titulo", "Bens Não Patrimoniados");
		return "inicial/benssituacao";
	}

	@RequestMapping(value = "bempermanente/fibp/{bem}", method = RequestMethod.GET)
	public void criaFibp(@PathVariable BemPermanente bem, HttpServletResponse response) throws IOException, JRException {

        InputStream is = this.getClass().getResourceAsStream("/logo.gif");

        // TODO exportar isso para um singleton injetável
        Properties properties = new Properties();
        properties.load(this.getClass().getResourceAsStream("/esposende.properties"));
        String nomeCurtoInstituicao = properties.get("nomeCurtoInstituicao").toString();

        ServletOutputStream servletOutputStream = response.getOutputStream();
		this.jasper.setInputStream("fibp")
				.setOutputStream(servletOutputStream)
				.addParametro("bem", bem)
				.addParametro("data", bem.getTombamento().getDataTombamento())
				.addParametro("operacao", bem.getTombamento().getTipoOperacao().name())
				.addParametro("documentoHabil", bem.getTombamento().getDocumentoHabil())
				.addParametro("historicoOperacao", bem.getTombamento().getHistoricoOperacao())
				.addParametro("valor", bem.getTombamento().getValorOperacao())
                .addParametro("logo", is)
                .addParametro("nomeCurtoInstituicao", nomeCurtoInstituicao)
				.montaRelatorioJasper();

		response.setContentType("application/pdf");
		servletOutputStream.flush();
		servletOutputStream.close();
	}

}
