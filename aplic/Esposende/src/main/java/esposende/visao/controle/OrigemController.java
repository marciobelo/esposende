package esposende.visao.controle;

import esposende.entidade.Origem;
import esposende.service.OrigemService;
import esposende.visao.controle.converter.LongOrigemConverter;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Map;

@Controller
public class OrigemController {

	@Inject
	private OrigemService origemService;
	@Inject
	private LongOrigemConverter converter;

	@InitBinder
	protected void initBinder(HttpServletRequest request,
	                          ServletRequestDataBinder binder) throws Exception {
		binder.registerCustomEditor(Origem.class, converter);
	}

	@RequestMapping("origem/listar")
	public String listar(Map<String, Object> model) {
		List<Origem> origens = origemService.findAll();
		model.put("origens", origens);
		return "origem/listar";
	}

	@RequestMapping(value = "origem/detalhes/{origem}", method = RequestMethod.GET)
	public String detalhes(@PathVariable Origem origem, Map<String, Object> model) {
		model.put("origem", origem);
		return "origem/detalhes";
	}

	@RequestMapping(value = "origem/novo", method = RequestMethod.GET)
	public String novo(Map<String, OrigemModel> model) {
		model.put("origem", new OrigemModel());
		return "origem/form";
	}

	@Transactional
	@RequestMapping(value = "origem/novo", method = RequestMethod.POST)
	public String novo(@Valid @ModelAttribute("origem") OrigemModel origemModel, BindingResult result) {
		if (result.hasErrors()){
			return "origem/form";
		}
		Origem origem = origemModel.criarOrigem();
		origemService.persist(origem);
		return "redirect:/origem/listar";
	}

	@RequestMapping(value = "origem/editar/{origem}", method = RequestMethod.GET)
	public String editar(@PathVariable Origem origem, Map<String, Origem> model) {
		model.put("origem", origem);
		return "origem/form";
	}

	@RequestMapping(value = "origem/editar/{id}", method = RequestMethod.POST)
	public String editar(@PathVariable long id, OrigemModel origem, BindingResult result) {
		if (result.hasErrors()) return "origem/form";
		Origem o = origem.criarOrigem();
		origemService.update(o);
		return "redirect:/origem/listar";
	}

	@RequestMapping(value = "origem/excluir/{origem}", method = RequestMethod.GET)
	public String excluir(@PathVariable Origem origem) {
		origemService.delete(origem);
		return "redirect:/origem/listar";
	}

	@RequestMapping(value = "origem/origens", method = RequestMethod.GET)
	public String origens(Map<String, Object> model) {
		listar(model);
		return "origem/origens";
	}

	private static class OrigemModel {
		private Long id;
		@Size(min = 1, message = "Informe o resumo")
		private String resumo;
		@Size(min = 1, message = "Informe o detalhe")
		private String detalhe;

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getResumo() {
			return resumo;
		}

		public void setResumo(String resumo) {
			this.resumo = resumo;
		}

		public String getDetalhe() {
			return detalhe;
		}

		public void setDetalhe(String detalhe) {
			this.detalhe = detalhe;
		}

		public Origem criarOrigem() {
			Origem origem = new Origem(resumo, detalhe);
			origem.setId(id);
			return origem;
		}
	}


}
