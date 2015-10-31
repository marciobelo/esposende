package esposende.service;

import esposende.entidade.NumeroProtocolar;
import esposende.persistencia.NumeroProtocolarDAO;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

@Component
public class NumeroProtocolarService {

	@Inject
	private NumeroProtocolarDAO numeroProtocolarDAO = null;

	/**
	 * Gera um novo número protocolar de acordo com sequência no ano
	 *
	 * @return
	 */
	public NumeroProtocolar gerarNovoProtocolo() {
		return numeroProtocolarDAO.gerarNovoProcotolo();
	}

	/**
	 * Busca um número protocolar de acordo com o id
	 *
	 * @param idNumeroProtocolar
	 * @return
	 */
	public NumeroProtocolar obterPorId(Long idNumeroProtocolar) {
		return numeroProtocolarDAO.obterPorId(idNumeroProtocolar);
	}
}
