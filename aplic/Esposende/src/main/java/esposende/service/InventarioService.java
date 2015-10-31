package esposende.service;

import esposende.entidade.*;
import esposende.persistencia.InventarioDAO;
import esposende.persistencia.NumeroProtocolarDAO;
import esposende.visao.controle.formbean.BensEspeciais;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Component
public class InventarioService {

	@Inject
	private InventarioDAO inventarioDAO = null;

	@Inject
	private NumeroProtocolarDAO numeroProtocolarDAO = null;

	@Inject
	private BemPermanenteService bemPermanenteService = null;

	@Inject
	private NumeroProtocolarService numeroProtocolarService = null;

	/**
	 * Busca inventário por id no banco de dados
	 *
	 * @param id
	 * @return
	 */
	public Inventario findById(Long id) {
		return inventarioDAO.findById(id);
	}

	/**
	 * Busca o último inventário de um responsável
	 *
	 * @param responsavel
	 * @return
	 */
	public Inventario buscarPorResponsavel(Responsavel responsavel) {
		return inventarioDAO.buscarPorResponsavel(responsavel);
	}

	/**
	 * Lista todos os inventário de um responsável
	 *
	 * @param responsavel
	 * @return
	 */
	public List<Inventario> listarPorResponsavel(Responsavel responsavel) {
		return inventarioDAO.listarPorResponsavel(responsavel);
	}

	/**
	 * Grava um inventário no banco de dados
	 *
	 * @param inventario
	 */
	@Transactional
	public void persist(Inventario inventario) {
		inventarioDAO.persist(inventario);
	}

	/**
	 * Atualiza um inventário no banco de dados
	 *
	 * @param inventario
	 */
	@Transactional
	public void update(Inventario inventario) {
		inventarioDAO.update(inventario);
	}

	/**
	 * Cria um novo inventário com número protocolar
	 *
	 * @param responsavel
	 * @return
	 */
	@Transactional
	public Inventario criaNovoInventario(Responsavel responsavel) {

		Calendar criacao = Calendar.getInstance();
		NumeroProtocolar numeroProtocolar = numeroProtocolarService.gerarNovoProtocolo();

		Inventario inventario = new Inventario(numeroProtocolar, criacao.getTime(),
				responsavel, bemPermanenteService.listarPorResponsavel(responsavel));

		inventarioDAO.persist(inventario);

		return inventario;
	}

	/**
	 * Encerra um inventário
	 *
	 * @param inventario
	 * @throws Exception
	 */
	@Transactional
	public void encerraInventario(Inventario inventario) throws Exception {
		if (!inventario.todosConferesFeitos()) throw new Exception("Todos os bens precisam ser conferidos");
		if (!inventario.temRelatorioAssinado()) throw new Exception("Precisa ter relatório assinado");
		if (inventario.getEncerrado()) throw new Exception("Já está encerrado");

		inventario.setDataFechamento(new Date());
		inventarioDAO.update(inventario);

		for (Confere confere : inventario.getConferidos()) {
			BemPermanente bem = confere.getBemPermanente();
			RegistroOcorrencia registro = new RegistroOcorrencia(TipoRegistroOcorrencia.INVENTARIO, inventario.getProtocolo() + " - " + confere.getSituacao().getDescricao());
			bem.adicionarRegistroOcorrencia(registro);
			bemPermanenteService.update(bem);
		}

	}

	/**
	 * Lista todos os inventários em aberto
	 *
	 * @return
	 */
	public List<Inventario> listaEmAberto() {
		return inventarioDAO.listaEmAberto();
	}

	/**
	 * Lista todos os bens com inventário em atraso
	 *
	 * @return
	 */
	public List<BemPermanente> bensInventarioAtraso() {
		return inventarioDAO.bensInventarioAtraso();
	}

	/**
	 * Lista bens de acordo com situação de inventário para relatório
	 *
	 * @param naoLocalizado
	 * @return
	 */
	public List<BensEspeciais> listaPorSituacao(Confere.TipoSituacaoConfere naoLocalizado) {
		List<BemPermanente> bens = inventarioDAO.listaPorSituacao(naoLocalizado);
		List<BensEspeciais> bensEspeciais = new ArrayList<BensEspeciais>();
		for (BemPermanente b : bens) {
			bensEspeciais.add(new BensEspeciais(b, null, naoLocalizado));
		}
		return bensEspeciais;
	}

	/**
	 * Busca o último inventário em que está o bem informado
	 *
	 * @param bemPermanente
	 * @return
	 */
	public Inventario buscaUltimoInventario(BemPermanente bemPermanente) {
		return inventarioDAO.buscaUltimoInventario(bemPermanente);
	}
}
