package esposende.service;

import esposende.entidade.Confere;
import esposende.persistencia.ConfereDAO;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

@Component
public class ConfereService {
	@Inject
	private ConfereDAO confereDAO;

	/**
	 * Atualiza um confere de bem no banco de dados
	 *
	 * @param confere
	 */
	@Transactional
	public void update(Confere confere) {
		confereDAO.update(confere);
	}

	/**
	 * Busca confere de acordo com o id no banco de dados
	 *
	 * @param id
	 * @return
	 */
	public Confere findById(Long id) {
		return confereDAO.findById(id);
	}
}
