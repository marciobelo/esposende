package esposende.service;

import esposende.entidade.Origem;
import esposende.persistencia.OrigemDAO;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.List;

@Component
public class OrigemService {

	@Inject
	private OrigemDAO origemDAO;

	/**
	 * Lista todas as origens
	 *
	 * @return
	 */
	public List<Origem> findAll() {
		return origemDAO.findAll();
	}

	/**
	 * Grava uma origem no banco de dados
	 *
	 * @param origem
	 */
	public void persist(Origem origem) {
		origemDAO.persist(origem);
	}

	/**
	 * Busca origem por id no banco de dados
	 *
	 * @param idOrigem
	 * @return
	 */
	public Origem findById(Long idOrigem) {
		return origemDAO.findById(idOrigem);
	}

	/**
	 * Atualiza uma origem no banco de dados
	 *
	 * @param origem
	 */
	public void update(Origem origem) {
		origemDAO.update(origem);
	}

	/**
	 * Remove uma origem do banco
	 *
	 * @param origem
	 */
	public void delete(Origem origem) {
		origemDAO.delete(origem);
	}

}