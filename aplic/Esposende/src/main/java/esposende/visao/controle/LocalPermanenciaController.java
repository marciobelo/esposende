package esposende.visao.controle;

import esposende.entidade.LocalPermanencia;
import esposende.service.LocalPermanenciaService;
import esposende.visao.controle.converter.LocalPermanenciaConverter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.Map;

@Controller
@RequestMapping("/local/")
public class LocalPermanenciaController {

	@Inject
	private LocalPermanenciaService service;
	@Inject
	private LocalPermanenciaConverter converter;

	@InitBinder
	public void init(ServletRequestDataBinder binder) {
		binder.registerCustomEditor(LocalPermanencia.class, converter);
	}

	@RequestMapping(value = "edit", method = RequestMethod.POST)
	public String edit(LocalForm localPermanencia) {
		if (localPermanencia.getId() == null) {
			service.persist(localPermanencia.geraLocal());
		} else {
			service.merge(localPermanencia.geraLocal());
		}
		return "redirect:/local/list";
	}

	@RequestMapping(value = "edit", method = RequestMethod.GET)
	public String edit(@RequestParam(required = false) LocalPermanencia localPermanencia, Map<String, Object> model) {
		if (localPermanencia == null) {
			model.put("localPermanencia", new LocalForm());
		} else {
			model.put("localPermanencia", localPermanencia);
		}
		return "/local/edit";
	}

	@RequestMapping("list")
	public String list(Map<String, Object> model) {
		model.put("locais", service.listar());
		return "/local/list";
	}

    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public String excluir(@RequestParam Long id, Map<String, Object> model) {
        service.excluir( id );
        model.put("locais", service.listar());
        return "/local/list";
    }

	private static class LocalForm {
		private String nome;
		private Long id;

		public String getNome() {
			return nome;
		}

		public void setNome(String nome) {
			this.nome = nome;
		}

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public LocalPermanencia geraLocal() {
			LocalPermanencia local = new LocalPermanencia(nome);
			local.setId(id);
			return local;
		}
	}
}
