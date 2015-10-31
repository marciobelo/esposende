package esposende.service;

import esposende.entidade.BemPermanente;
import esposende.entidade.Responsavel;
import esposende.entidade.TermoSubRogo;
import esposende.persistencia.TermoSubRogoDAO;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;

/**
 * Serviços do Termo de SubRogo
 */
@Component
public class TermoSubRogoService {

	@Inject
	private TermoSubRogoDAO termoSubRogoDAO = null;

	@Inject
	private BemPermanenteService bemPermanenteService;

	/**
	 * Grava um termo de subrogo no banco de dados
	 *
	 * @param termoSubRogo
	 */
	@Transactional
	public void salvar(TermoSubRogo termoSubRogo) {
		termoSubRogoDAO.salvar(termoSubRogo);
	}

	/**
	 * Lista os termos de subrogo de um responsável
	 *
	 * @param responsavel
	 * @return
	 */
	public List<TermoSubRogo> obterPorResponsavel(Responsavel responsavel) {
		return termoSubRogoDAO.obterPorResponsavel(responsavel);
	}

	/**
	 * Lista todos os termos de subrogo
	 *
	 * @return
	 */
	public List<TermoSubRogo> obterTodos() {
		return termoSubRogoDAO.obterTodos();
	}

	/**
	 * Busca um termo de subrogo de acordo com id no banco de dados
	 *
	 * @param idTermoSubRogo
	 * @return
	 */
	public TermoSubRogo obterPorId(Long idTermoSubRogo) {
		return termoSubRogoDAO.obterPorId(idTermoSubRogo);
	}

	/**
	 * Efetiva o registro de um termo de subrogo
	 *
	 * @param id
	 * @param dataSubRogo
	 * @param dataPrevistaEncerramento
	 */
	@Transactional
	public void registrarSubRogo(Long id, Date dataSubRogo, Date dataPrevistaEncerramento) {
		TermoSubRogo termoSubRogo = termoSubRogoDAO.obterPorId(id);
		termoSubRogo.setDataSubRogo(dataSubRogo);
		termoSubRogo.setDataPrevistaEncerramento(dataPrevistaEncerramento);
		this.salvar(termoSubRogo);

		for (BemPermanente bem : termoSubRogo.getArrolados()) {
			bem.setResponsavel(termoSubRogo.getSubrogado());
			bemPermanenteService.update(bem);
		}
	}

	/**
	 * Efetiva o encerramento de um termo de subrogo
	 *
	 * @param id
	 * @param dataEncerramento
	 */
	@Transactional
	public void encerrar(Long id, Date dataEncerramento) {
		TermoSubRogo termoSubRogo = termoSubRogoDAO.obterPorId(id);
		termoSubRogo.setDataEncerramento(dataEncerramento);
		termoSubRogoDAO.salvar(termoSubRogo);
	}

	/**
	 * Informa a quantidade de bens que estão em situação de subrogo
	 *
	 * @return
	 */
	public Long numeroSubRogados() {
		return termoSubRogoDAO.numeroSubRogados();
	}

	/**
	 * Lista todos os bens em situação de subrogo
	 *
	 * @return
	 */
	public List<BemPermanente> listaSubRogados() {
		return termoSubRogoDAO.listaSubRogados();
	}

	/**
	 * Lista todos os termos de subrogo não encerrados
	 *
	 * @return
	 */
	public List<TermoSubRogo> listaTermosAbertos() {
		return termoSubRogoDAO.listaTermosAbertos();
	}

	/**
	 * Busca o termo de subrogo ainda vigente de um bem informado
	 *
	 * @param bemPermanente
	 * @return
	 */
	public TermoSubRogo buscaSubRogoVigente(BemPermanente bemPermanente) {
		return termoSubRogoDAO.buscaSubRogoVigente(bemPermanente);
	}

	/**
	 * Busca o termo de subrogo ainda vigente de um bem informado
	 *
	 * @param bemPermanente
	 * @return
	 */
	public TermoSubRogo subRogoVigenteBem(BemPermanente bemPermanente) {
		return termoSubRogoDAO.subRogoVigenteBem(bemPermanente);
	}
}
