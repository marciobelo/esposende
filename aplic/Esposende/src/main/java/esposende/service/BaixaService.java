package esposende.service;

import esposende.entidade.Baixa;
import esposende.entidade.BemPermanente;
import esposende.persistencia.BaixaDao;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.List;

@Component
public class BaixaService {

	@Inject
	private BaixaDao baixaDao;

	/**
	 * Grava um documento de baixa no banco de dados
	 *
	 * @param baixa
	 */
	public void persist(Baixa baixa) {
		baixaDao.persist(baixa);
	}

	/**
	 * Atualiza um documento de baixa no banco de dados
	 *
	 * @param baixa
	 */
	public void merge(Baixa baixa) {
		baixaDao.merge(baixa);
	}

	/**
	 * Busca um documento de baixa de acordo com o id no banco de dados
	 *
	 * @param id
	 * @return Baixa
	 */
	public Baixa findById(Long id) {
		return baixaDao.findById(id);
	}

	/**
	 * Lista todos os documentos de baixas já feitos
	 *
	 * @return
	 */
	public List<Baixa> listarTodos() {
		return baixaDao.listarTodos();
	}

	/**
	 * Lista todos os bens que compõem documentos de baixa sem data de baixa contábil
	 *
	 * @return
	 */
	public List<BemPermanente> listaEmBaixa() {
		return baixaDao.listaEmBaixa();
	}

	/**
	 * Lista todos os bens que compo~em documentos de baixa com data de baixa contábil definida
	 *
	 * @return
	 */
	public List<BemPermanente> listaBaixados() {
		return baixaDao.listaBaixados();
	}
}
