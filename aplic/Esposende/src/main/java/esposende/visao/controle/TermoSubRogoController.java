package esposende.visao.controle;

import esposende.entidade.*;
import esposende.service.BemPermanenteService;
import esposende.service.NumeroProtocolarService;
import esposende.service.ResponsavelService;
import esposende.service.TermoSubRogoService;
import esposende.visao.controle.converter.LongBemPermanenteConverter;
import esposende.visao.controle.converter.StringDateConverter;
import esposende.visao.controle.converter.TermoSubRogoConverter;
import esposende.visao.controle.formbean.BensEspeciais;
import esposende.visao.controle.util.MontaJasperReports;
import net.sf.jasperreports.engine.JRException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.Future;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/termoSubRogo")
public class TermoSubRogoController {

	@Inject
	private ResponsavelService responsavelService;

	@Inject
	private BemPermanenteService bemPermanenteService;

	@Inject
	private TermoSubRogoService termoSubRogoService;

	@Inject
	private NumeroProtocolarService numeroProtocolarService;

	@Inject
	private StringDateConverter dateConverter;

	@Inject
	private LongBemPermanenteConverter bemPermanenteConverter;

	@Inject
	private TermoSubRogoConverter converter;

	public TermoSubRogoController(ResponsavelService responsavelService,
	                              BemPermanenteService bemPermanenteService,
	                              TermoSubRogoService termoSubRogoService,
	                              NumeroProtocolarService numeroProtocolarService) {
		this.responsavelService = responsavelService;
		this.bemPermanenteService = bemPermanenteService;
		this.termoSubRogoService = termoSubRogoService;
		this.numeroProtocolarService = numeroProtocolarService;
	}

	public TermoSubRogoController() {
	}

	@InitBinder
	protected void initBinder(ServletRequestDataBinder binder) throws Exception {
		binder.registerCustomEditor(Date.class, dateConverter);
		binder.registerCustomEditor(BemPermanente.class, bemPermanenteConverter);
		binder.registerCustomEditor(TermoSubRogo.class, converter);
	}

	@RequestMapping(value = "/editar", method = RequestMethod.GET)
	public String editar(@RequestParam(required = false) TermoSubRogo termo, Map<String, Object> model) {

		TermoSubRogoModel termoSubRogoModel;
		if (termo == null) {
			termoSubRogoModel = TermoSubRogoModel.criarVazio();
		} else {
			termoSubRogoModel = new TermoSubRogoModel(termo);
		}
		popularListaAuxiliares(termoSubRogoModel);
		model.put("termoSubRogoModel", termoSubRogoModel);
		return "termoSubRogo/form";
	}

	@RequestMapping(value = "/adicionarBem", method = RequestMethod.POST)
	public String adicionarBem(TermoSubRogoModel termoSubRogoModel,
	                           BindingResult bindingResult) {

		popularListaAuxiliares(termoSubRogoModel);

		// Verifica se bem já está selecionado
		if (termoSubRogoModel.bens.containsKey(termoSubRogoModel.getIdBemAdicionar())) {
			bindingResult.addError(new ObjectError("bem", "Item já adicionado"));
			return "termoSubRogo/form";
		}

		if (bemPermanenteService.findById(termoSubRogoModel.getIdBemAdicionar()) == null) {
			bindingResult.addError(new ObjectError("bem", "Bem não existe"));
			return "termoSubRogo/form";
		}

		termoSubRogoModel.adicionarBem(bemPermanenteService.findById(termoSubRogoModel.getIdBemAdicionar()));
		return "termoSubRogo/form";
	}

	@RequestMapping(value = "/salvar", method = RequestMethod.POST)
	public String salvar(@Valid TermoSubRogoModel termoSubRogoModel, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			popularListaAuxiliares(termoSubRogoModel);
			return "termoSubRogo/form";
		}

		TermoSubRogo termoSubRogo = termoSubRogoModel.criarTermoSubRogo(numeroProtocolarService, responsavelService, bemPermanenteService);
		termoSubRogoService.salvar(termoSubRogo);

		return "redirect:exibir?termo=" + termoSubRogo.getId();
	}

	@RequestMapping(value = "/listar")
	public String listar(@RequestParam(required = false) Long idResponsavel, Map<String, Object> model) {
		TermoSubRogoModel termoSubRogoModel = TermoSubRogoModel.criarVazio();
		termoSubRogoModel.setResponsaveis(responsavelService.findAll());
		if (idResponsavel == null) {
			termoSubRogoModel.setTermosPesquisados(termoSubRogoService.obterTodos());
		} else {
			Responsavel responsavel = responsavelService.findById(idResponsavel);
			termoSubRogoModel.setTermosPesquisados(termoSubRogoService.obterPorResponsavel(responsavel));
			termoSubRogoModel.setIdResponsavel(idResponsavel);
		}
		model.put("termoSubRogoModel", termoSubRogoModel);
		return "termoSubRogo/listar";
	}

	@RequestMapping(value = "/exibir", method = RequestMethod.GET)
	public String exibir(@RequestParam TermoSubRogo termo, Map<String, Object> model) {
		model.put("termoSubRogoModel", new TermoSubRogoModel(termo));
		return "termoSubRogo/exibir";
	}


	@RequestMapping(value = "/listarBens", method = RequestMethod.GET)
	public String listarBens(Map<String, Object> model, @RequestParam("trechoDescricao") String trechoDescricao) {
		List<BemPermanente> bens = bemPermanenteService.listarPorCriterios(trechoDescricao, null, null, "", null);
		List<BemPermanente> arrolados = termoSubRogoService.listaSubRogados();

		List<Object[]> encontrados = new ArrayList<Object[]>();
		for (BemPermanente bem : bens) {
			boolean contains = arrolados.contains(bem);
			encontrados.add(new Object[]{bem, contains});
		}

		model.put("bens", encontrados);
		return "termoSubRogo/listarBens";
	}

	@RequestMapping(value = "/registrar", method = RequestMethod.GET)
	public String prepararFormRegistrar(@RequestParam TermoSubRogo termo, Map<String, Object> model) {
		model.put("termoSubRogoModel", new TermoSubRogoModel(termo));
		return "termoSubRogo/formRegistrar";
	}

	@RequestMapping(value = "/registrar", method = RequestMethod.POST)
	public String registrar(@Valid TermoSubRogoModel termoSubRogoModel, BindingResult bindingResult) {

		TermoSubRogo termoSubRogo = termoSubRogoService.obterPorId(termoSubRogoModel.getId());
		if (termoSubRogo.getTermosAssinados().size() == 0) {
			bindingResult.addError(new FieldError("termosAssinados", "termosAssinados", "Deve-se colocar ao menos um termo digitalizado"));
		}

		if (bindingResult.hasErrors()) {
			Date dataSubRogo = termoSubRogoModel.dataSubRogo;
			termoSubRogoModel.atualizaTermoSubRogo(termoSubRogoService);
			termoSubRogoModel.setDataSubRogo(dataSubRogo);
			return "termoSubRogo/formRegistrar";
		}
		try {
			termoSubRogoService.registrarSubRogo(termoSubRogoModel.getId(), termoSubRogoModel.getDataSubRogo(),
					termoSubRogoModel.getDataPrevistaEncerramento());
		} catch (IllegalArgumentException e) {
			bindingResult.addError(new FieldError("dataSubRogo", "dataSubRogo", e.getMessage()));
			return "termoSubRogo/formRegistrar";
		}
		return "redirect:/termoSubRogo/listar?idResponsavel=" + termoSubRogoModel.getIdResponsavel();
	}

	@RequestMapping("/encerrar")
	public String prepararFormEncerrar(@RequestParam TermoSubRogo termo, Map<String, Object> model) {
		model.put("termoSubRogoEncerramentoModel", new TermoSubRogoEncerramentoModel(termo));
		return "termoSubRogo/formEncerrar";
	}

	@RequestMapping(value = "/encerrar", method = RequestMethod.POST)
	public String encerrar(@Valid TermoSubRogoEncerramentoModel termoSubRogoEncerramentoModel,
	                       BindingResult bindingResult) {

		TermoSubRogo termoSubRogo = termoSubRogoService.obterPorId(termoSubRogoEncerramentoModel.getIdTermoSubRogo());

		if (bindingResult.hasErrors()) {
			termoSubRogoEncerramentoModel.atualizar(termoSubRogo);
			return "termoSubRogo/formEncerrar";
		}

		termoSubRogoService.encerrar(termoSubRogoEncerramentoModel.getIdTermoSubRogo(),
				termoSubRogoEncerramentoModel.getDataEncerramento());
		return "redirect:/termoSubRogo/listar?idResponsavel=" + termoSubRogoEncerramentoModel.getIdResponsavel();
	}

	@RequestMapping(value = "/download")
	public void downalod(@RequestParam TermoSubRogo termo, HttpServletResponse response) throws IOException, JRException {
		ServletOutputStream servletOutputStream = response.getOutputStream();
		new MontaJasperReports()
				.setDataSource(termo.getArrolados())
				.setOutputStream(servletOutputStream)
				.addParametro("nrProtocolo", termo.getProtocolo().toString())
				.addParametro("responsavel", termo.getSubrogado().getNome())
				.addParametro("dataEmissao", termo.getDataEmissao())
				.addParametro("proposito", termo.getProposito())
				.addParametro("dataPrevistaEncerramento", termo.getDataPrevistaEncerramento())
				.addParametro("dataEncerramento", termo.getDataEncerramento())
				.setInputStream("termo-sub-rogo").montaRelatorioJasper();
		response.setContentType("application/pdf");
		servletOutputStream.flush();
		servletOutputStream.close();
	}

	private void popularListaAuxiliares(TermoSubRogoModel termoSubRogoModel) {
		termoSubRogoModel.setResponsaveis(responsavelService.findAll());
		termoSubRogoModel.setBens(bemPermanenteService);
	}

	@RequestMapping("/benssubrogados")
	public String listaBensSubRogados(Map model) {
		List<TermoSubRogo> subRogos = termoSubRogoService.listaTermosAbertos();
		List<BensEspeciais> bens = new ArrayList<BensEspeciais>();

		for (TermoSubRogo termo : subRogos) {
			for (BemPermanente bem : termo.getArrolados()) {
				bens.add(new BensEspeciais(bem, termo, null));
			}
		}
		model.put("bens", bens);
		model.put("tipo", "Sub rogo");
		model.put("data", new Date());
		model.put("titulo", "Bens Sub rogados");
		return "inicial/benssituacao";
	}

	public static class TermoSubRogoModel {

		private Long id;
		private String protocolo;
		private Long idNumeroProtocolar;
		private Long idResponsavel;
		private String nomeResponsavel;
		private List<Responsavel> responsaveis;
		private Date dataEmissao;

		@Past(message = "Data de Subrogo não pode ser no futuro.")
		private Date dataSubRogo;

		@Future(message = "Previsão de encerramento deve ser no futuro")
		private Date dataPrevistaEncerramento;

		private Date dataEncerramento;

		@Size(min = 1, message = "Informe o propósito do termo")
		private String proposito;
		private Long idBemAdicionar;
		private Map<Long, BemPermanente> bens = new HashMap<Long, BemPermanente>();

		private Set<DocumentoDigital> termosAssinados;

		private String situacaoPesquisa;
		private List<TermoSubRogo> termosPesquisados;

		public TermoSubRogoModel(TermoSubRogo termoSubRogo) {

			this.id = termoSubRogo.getId();
			this.idNumeroProtocolar = termoSubRogo.getProtocolo().getId();
			this.protocolo = termoSubRogo.getProtocolo().toString();
			this.idResponsavel = termoSubRogo.getSubrogado().getId();
			this.nomeResponsavel = termoSubRogo.getSubrogado().getNome();
			this.dataEmissao = termoSubRogo.getDataEmissao();
			this.proposito = termoSubRogo.getProposito();

			this.dataSubRogo = termoSubRogo.getDataSubRogo();

			this.dataPrevistaEncerramento = termoSubRogo.getDataPrevistaEncerramento();
			this.dataEncerramento = termoSubRogo.getDataEncerramento();
			this.termosAssinados = termoSubRogo.getTermosAssinados();
			for (BemPermanente bem : termoSubRogo.getArrolados()) {
				bens.put(bem.getId(), bem);
			}
		}

		public TermoSubRogo criarTermoSubRogo(NumeroProtocolarService numeroProtocolarService,
		                                      ResponsavelService responsavelService,
		                                      BemPermanenteService bemPermanenteService) {

			NumeroProtocolar protocolo;
			if (this.idNumeroProtocolar == null) {
				protocolo = numeroProtocolarService.gerarNovoProtocolo();
			} else {
				protocolo = numeroProtocolarService.obterPorId(this.idNumeroProtocolar);
			}

			Set<BemPermanente> arrolados = new HashSet<BemPermanente>();
			for (Long idBem : this.bens.keySet()) {
				arrolados.add(bemPermanenteService.findById(idBem));
			}

			Responsavel responsavel = responsavelService.findById(this.idResponsavel);

			TermoSubRogo termoSubRogo = new TermoSubRogo(protocolo, dataEmissao, responsavel, this.proposito, arrolados);
			termoSubRogo.setId(this.id);
			termoSubRogo.setDataPrevistaEncerramento(this.dataPrevistaEncerramento);

			return termoSubRogo;
		}

		public void atualizaTermoSubRogo(TermoSubRogoService termoSubRogoService) {
			TermoSubRogo termoSubRogo = termoSubRogoService.obterPorId(this.getId());
			this.id = termoSubRogo.getId();
			this.idNumeroProtocolar = termoSubRogo.getProtocolo().getId();
			this.protocolo = termoSubRogo.getProtocolo().toString();
			this.idResponsavel = termoSubRogo.getSubrogado().getId();
			this.nomeResponsavel = termoSubRogo.getSubrogado().getNome();
			this.dataEmissao = termoSubRogo.getDataEmissao();
			this.proposito = termoSubRogo.getProposito();

			this.dataSubRogo = termoSubRogo.getDataSubRogo();

			this.dataPrevistaEncerramento = termoSubRogo.getDataPrevistaEncerramento();
			this.dataEncerramento = termoSubRogo.getDataEncerramento();
			this.termosAssinados = termoSubRogo.getTermosAssinados();
			for (BemPermanente bem : termoSubRogo.getArrolados()) {
				bens.put(bem.getId(), bem);
			}
		}

		public TermoSubRogoModel() {

		}

		public String getSituacaoPesquisa() {
			return situacaoPesquisa;
		}

		public void setSituacaoPesquisa(String situacaoPesquisa) {
			this.situacaoPesquisa = situacaoPesquisa;
		}

		public List<TermoSubRogo> getTermosPesquisados() {
			return termosPesquisados;
		}

		public void setTermosPesquisados(List<TermoSubRogo> termosPesquisados) {
			this.termosPesquisados = termosPesquisados;
		}

		public Set<Long> getIdBensSelecionados() {
			return bens.keySet();
		}

		public void setIdBensSelecionados(Set<Long> idBensSelecionados) {
			for (Long id : idBensSelecionados) {
				bens.put(id, null);
			}
		}

		public static TermoSubRogoModel criarVazio() {
			TermoSubRogoModel model = new TermoSubRogoModel();
			return model;
		}

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getProtocolo() {
			return protocolo;
		}

		public void setProtocolo(String protocolo) {
			this.protocolo = protocolo;
		}

		public Long getIdNumeroProtocolar() {
			return idNumeroProtocolar;
		}

		public void setIdNumeroProtocolar(Long idNumeroProtocolar) {
			this.idNumeroProtocolar = idNumeroProtocolar;
		}

		public Long getIdResponsavel() {
			return idResponsavel;
		}

		public void setIdResponsavel(Long idResponsavel) {
			this.idResponsavel = idResponsavel;
		}

		public List<Responsavel> getResponsaveis() {
			return responsaveis;
		}

		public void setResponsaveis(List<Responsavel> responsaveis) {
			this.responsaveis = responsaveis;
		}

		public Date getDataEmissao() {
			return dataEmissao;
		}

		public void setDataEmissao(Date dataEmissao) {
			this.dataEmissao = dataEmissao;
		}

		public String getProposito() {
			return proposito;
		}

		public void setProposito(String proposito) {
			this.proposito = proposito;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (!(o instanceof TermoSubRogoModel)) return false;

			TermoSubRogoModel that = (TermoSubRogoModel) o;

			if (protocolo != null ? !protocolo.equals(that.protocolo) : that.protocolo != null)
				return false;

			return true;
		}

		@Override
		public int hashCode() {
			return protocolo != null ? protocolo.hashCode() : 0;
		}

		public Long getIdBemAdicionar() {
			return idBemAdicionar;
		}

		public void setIdBemAdicionar(Long idBemAdicionar) {
			this.idBemAdicionar = idBemAdicionar;
		}

		public void adicionarBem(BemPermanente bem) {
			bens.put(bem.getId(), bem);
		}

		public void setBens(BemPermanenteService bemPermanenteService) {
			for (Long id : bens.keySet()) {
				bens.put(id, bemPermanenteService.findById(id));
			}
		}

		public Map<Long, BemPermanente> getBens() {
			return bens;
		}

		public Date getDataSubRogo() {
			return dataSubRogo;
		}

		public void setDataSubRogo(Date dataSubRogo) {
			this.dataSubRogo = dataSubRogo;
		}

		public Date getDataPrevistaEncerramento() {
			return dataPrevistaEncerramento;
		}

		public void setDataPrevistaEncerramento(Date dataPrevistaEncerramento) {
			this.dataPrevistaEncerramento = dataPrevistaEncerramento;
		}

		public Date getDataEncerramento() {
			return dataEncerramento;
		}

		public void setDataEncerramento(Date dataEncerramento) {
			this.dataEncerramento = dataEncerramento;
		}

		public String getNomeResponsavel() {
			return nomeResponsavel;
		}

		public void setNomeResponsavel(String nomeResponsavel) {
			this.nomeResponsavel = nomeResponsavel;
		}

		public Set<DocumentoDigital> getTermosAssinados() {
			return termosAssinados;
		}
	}

	public static class TermoSubRogoEncerramentoModel {

		@Past(message = "Data de encerramento não pode ser no futuro.")
		private Date dataEncerramento;

		private Long idTermoSubRogo;
		private Long idResponsavel;

		private NumeroProtocolar protocolo;
		private Date dataSubRogo;

		public TermoSubRogoEncerramentoModel(TermoSubRogo termoSubRogo) {
			this.idTermoSubRogo = termoSubRogo.getId();
			this.idResponsavel = termoSubRogo.getSubrogado().getId();
			this.protocolo = termoSubRogo.getProtocolo();
			this.dataSubRogo = termoSubRogo.getDataSubRogo();
		}

		public TermoSubRogoEncerramentoModel() {

		}

		public void atualizar(TermoSubRogo termoSubRogo) {
			this.protocolo = termoSubRogo.getProtocolo();
			this.dataSubRogo = termoSubRogo.getDataSubRogo();
		}

		public NumeroProtocolar getProtocolo() {
			return protocolo;
		}

		public Long getIdTermoSubRogo() {
			return idTermoSubRogo;
		}

		public void setIdTermoSubRogo(Long idTermoSubRogo) {
			this.idTermoSubRogo = idTermoSubRogo;
		}

		public Long getIdResponsavel() {
			return idResponsavel;
		}

		public void setIdResponsavel(Long idResponsavel) {
			this.idResponsavel = idResponsavel;
		}

		public Date getDataSubRogo() {
			return dataSubRogo;
		}

		public Date getDataEncerramento() {
			return dataEncerramento;
		}

		public void setDataEncerramento(Date dataEncerramento) {
			this.dataEncerramento = dataEncerramento;
		}
	}
}
