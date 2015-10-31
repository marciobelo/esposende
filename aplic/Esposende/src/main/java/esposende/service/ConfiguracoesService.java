package esposende.service;

import esposende.entidade.Configuracoes;
import esposende.persistencia.ConfiguracoesDao;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

@Component
public class ConfiguracoesService {

	@Inject
	private ConfiguracoesDao dao;

	/**
	 * Busca configurações de sistema no banco de dados
	 *
	 * @return
	 */
	public Configuracoes getConfiguracoes() {
		return dao.load();
	}

	/**
	 * Atualiza configurações no banco de dados
	 *
	 * @param configuracoes
	 */
	@Transactional
	public void merge(Configuracoes configuracoes) {
		dao.merge(configuracoes);
	}

}