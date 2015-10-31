package esposende.visao.controle;

import esposende.entidade.*;
import esposende.service.BaixaService;
import esposende.service.BemPermanenteService;
import esposende.service.NumeroProtocolarService;
import esposende.service.TermoSubRogoService;
import esposende.visao.controle.converter.BaixaConverter;
import esposende.visao.controle.converter.LongBemPermanenteConverter;
import esposende.visao.controle.converter.StringDateConverter;
import esposende.visao.controle.formbean.BaixaModel;
import esposende.visao.controle.formbean.BensEspeciais;
import esposende.visao.controle.util.MontaJasperReports;
import net.sf.jasperreports.engine.JRException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("baixa")
public class BaixaController {

	@Inject
	private BaixaService BaixaService;
	@Inject
	private BemPermanenteService bemPermanenteService;
	@Inject
	private NumeroProtocolarService numeroProtocolarService;
	@Inject
	private BaixaConverter converter;
	@Inject
	private StringDateConverter dateConverter;
	@Inject
	private LongBemPermanenteConverter bemConverter;
	@Inject
	private TermoSubRogoService termoSubRogoService;

	@InitBinder
	public void init(ServletRequestDataBinder binder) {
		binder.registerCustomEditor(Baixa.class, converter);
		binder.registerCustomEditor(Date.class, dateConverter);
		binder.registerCustomEditor(BemPermanente.class, bemConverter);
	}

	@RequestMapping("/")
	public String listaBaixas(Map model) {
		model.put("baixas", BaixaService.listarTodos());
		return "baixa/lista";
	}

	@RequestMapping("termo")
	public String termo(@RequestParam(required = false) Baixa baixa, Map model, HttpSession session) {
		baixa = baixa != null ? baixa : new Baixa();
		if (baixa.getProtocolo().getId() == null) {
			NumeroProtocolar protocolo = numeroProtocolarService.gerarNovoProtocolo();
			baixa.setProtocolo(protocolo);
		}

		model.put("baixaModel", new BaixaModel(baixa));
		session.setAttribute("selecionados", new ArrayList<BemPermanente>(baixa.getBens()));

		return "baixa/criabaixa";
	}

	@RequestMapping(value = "termo", method = RequestMethod.POST)
	public String gravaTermo(BaixaModel baixa, BindingResult result, Map model, HttpSession session) {
		Baixa termoBaixa;
		try {
			termoBaixa = baixa.getBaixa();
		} catch (Exception e) {
			model.put("erro", e.getMessage());
			return "baixa/criabaixa";
		}

		if (result.hasErrors()) {
			return "baixa/criabaixa";
		}

		termoBaixa.setDataCriacao(new Date());

		List<BemPermanente> bens = (List<BemPermanente>) session.getAttribute("selecionados");

		if (bens.size() == 0) {
			model.put("erro", "O termo precisa ter pelo menos um bem");
			return "baixa/criabaixa";
		}

		for (BemPermanente bem : bens) {
			TipoRegistroOcorrencia tipo = null;
			String mensagem = "Documento de baixa: ";
			if (termoBaixa.getDataBaixaContabil() != null) {
				tipo = TipoRegistroOcorrencia.BAIXADO;
			} else if (bem.getBaixa() == null) {
				tipo = TipoRegistroOcorrencia.EM_BAIXA;
			}

			if (tipo != null) {
				bem.adicionarRegistroOcorrencia(new RegistroOcorrencia(tipo, mensagem + termoBaixa.getProtocolo().toString()));
				bemPermanenteService.update(bem);
			}
		}

		termoBaixa.setBens(new HashSet<BemPermanente>(bens));


		if (termoBaixa.getId() == null)
			BaixaService.persist(termoBaixa);
		else
			BaixaService.merge(termoBaixa);

		return "redirect:/";
	}

	@RequestMapping("selecionados")
	public String selecionados(Map model, HttpSession session) {
		model.put("selecionados", session.getAttribute("selecionados"));
		return "baixa/selecionados";
	}

	@RequestMapping("busca")
	public String busca(String busca, Map model, HttpSession session) {
		List<BemPermanente> bensPermanentes = bemPermanenteService.listarPorCriterios(busca != null ? busca : "", null, null, "", null);
		bensPermanentes.removeAll((List<BemPermanente>) session.getAttribute("selecionados"));
		List<Object[]> bens = new ArrayList<Object[]>();

		for (BemPermanente b : bensPermanentes) {
			bens.add(new Object[]{b, termoSubRogoService.subRogoVigenteBem(b)});
		}

		model.put("encontrados", bens);
		return "baixa/encontrados";
	}

	@RequestMapping("adiciona")
	@ResponseBody
	public String adiciona(@RequestParam("bem") BemPermanente bem, HttpSession session) {
		List<BemPermanente> selecionados = (List<BemPermanente>) session.getAttribute("selecionados");

		if (bemPodeSerAdicionado(selecionados, bem)) {
			selecionados.add(bem);
			return "";
		} else {
			return "Não é permitido incluir bens patrimoniados e não patrimoniados na mesma baixa";
		}
	}

	private boolean bemPodeSerAdicionado(List<BemPermanente> selecionados, BemPermanente bem) {
		if (selecionados.isEmpty()) {
			return true;
		} else {
			return !(selecionados.get(0).getTombamento() == null ^ bem.getTombamento() == null);
		}
	}

	@RequestMapping("remove")
	@ResponseBody
	public String remove(@RequestParam("bem") BemPermanente bem, @RequestParam(value = "baixa", required = false) Baixa baixa, HttpSession session) {
		if (baixa != null) {
			((List<BemPermanente>) session.getAttribute("selecionados")).remove(bem);
			bem.adicionarRegistroOcorrencia(new RegistroOcorrencia(TipoRegistroOcorrencia.REMOVIDO_BAIXA, "Removido do documento " + baixa.getProtocolo().toString()));
			bemPermanenteService.update(bem);
		}
		return "";
	}

	@RequestMapping("embaixa")
	public String relatorioEmBaixa(Map model) {
		List<BemPermanente> baixados = BaixaService.listaEmBaixa();
		List<BensEspeciais> bens = new ArrayList<BensEspeciais>();

		for (BemPermanente bem : baixados) {
			bens.add(new BensEspeciais(bem, bem.getBaixa()));
		}

		model.put("bens", bens);
		model.put("tipo", "Em Baixa Em");
		model.put("data", new Date());
		model.put("titulo", "Bens Em Baixa");
		return "inicial/benssituacao";
	}

	@RequestMapping("baixados")
	public String relatorioBaixados(Map model) {
		List<BemPermanente> baixados = BaixaService.listaBaixados();
		List<BensEspeciais> bens = new ArrayList<BensEspeciais>();

		for (BemPermanente bem : baixados) {
			bens.add(new BensEspeciais(bem, bem.getBaixa()));
		}

		model.put("bens", bens);
		model.put("tipo", "Baixado Em");
		model.put("data", new Date());
		model.put("titulo", "Bens Baixados");
		return "inicial/benssituacao";
	}

	@RequestMapping("download/{baixa}")
	public void downlaod(@PathVariable Baixa baixa, HttpServletResponse response) throws IOException, JRException {
		ServletOutputStream servletOutputStream = response.getOutputStream();
		new MontaJasperReports()
				.setDataSource(baixa.getBens())
				.setOutputStream(servletOutputStream)
				.addParametro("baixa", baixa)
				.setInputStream("baixa").montaRelatorioJasper();
		response.setContentType("application/pdf");
		servletOutputStream.flush();
		servletOutputStream.close();
	}

}
