package esposende.visao.controle;

import com.thoughtworks.xstream.XStream;
import esposende.entidade.Tombamento;
import esposende.service.TombamentoService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

@Controller
public class TombamentoController{
	@Inject
	private TombamentoService tombamentoService;

	@RequestMapping("tombamento/busca/{codigo}")
	@ResponseBody
	public String busca(@PathVariable String codigo, HttpServletResponse response){
		Tombamento tombamento = tombamentoService.findByCodigo(codigo);

		response.setContentType("text/xml");
		XStream xml = new XStream();
		xml.alias("tombamento", Tombamento.class);

		return xml.toXML(tombamento);
	}
}
