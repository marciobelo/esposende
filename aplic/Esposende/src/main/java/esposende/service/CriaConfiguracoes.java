package esposende.service;

import esposende.entidade.Configuracoes;
import esposende.persistencia.ConfiguracoesDao;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

@Component
public class CriaConfiguracoes implements ApplicationListener<ContextRefreshedEvent> {

	@Inject
	private ConfiguracoesDao dao;

	/**
	 * Cria registros de configurações na inicialização do sistema
	 *
	 * @param contextRefreshedEvent
	 */
	@Override
	@Transactional
	public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
		Configuracoes c = dao.load();

		if (c == null) {
			c = new Configuracoes();
			c.setId(1L);
			dao.persist(c);
		}
	}
}
