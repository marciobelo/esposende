package esposende.persistencia.hibernate;

import esposende.entidade.Configuracoes;
import esposende.persistencia.ConfiguracoesDao;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class HibernateConfiguracoesDao implements ConfiguracoesDao {

	@PersistenceContext
	private EntityManager em;

	@Override
	public Configuracoes load() {
		return em.find(Configuracoes.class, 1L);
	}

	@Override
	public void merge(Configuracoes configuracoes) {
		em.merge(configuracoes);
	}

	@Override
	public void persist(Configuracoes configuracoes) {
		em.persist(configuracoes);
	}
}
