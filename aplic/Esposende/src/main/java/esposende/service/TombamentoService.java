package esposende.service;

import esposende.entidade.Tombamento;
import esposende.persistencia.TombamentoDAO;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.io.Serializable;

@Component
public class TombamentoService {

	@Inject
	private TombamentoDAO dao;

	/**
	 * Grava um tombamento no banco de dados
	 *
	 * @param tombamento
	 */
	public void persist(Tombamento tombamento) {
		dao.persist(tombamento);
	}

	/**
	 * Atualiza um tombamento no banco de dados
	 *
	 * @param tombamento
	 */
	public void merge(Tombamento tombamento) {
		dao.merge(tombamento);
	}

	/**
	 * Busca um tombamento pelo id no banco de dados
	 *
	 * @param id
	 * @return
	 */
	public Tombamento findById(Serializable id) {
		return dao.findById(id);
	}

	/**
	 * Busca um tombamento pelo código de tombamento
	 *
	 * @param codigo
	 * @return
	 */
	public Tombamento findByCodigo(String codigo) {
		return dao.findByCodigo(codigo);
	}
}
