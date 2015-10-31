package esposende.persistencia.hibernate;

import esposende.entidade.Origem;
import esposende.persistencia.OrigemDAO;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Collections;
import java.util.List;

@Repository
public class HibernateOrigemDAO implements OrigemDAO {

	@PersistenceContext
	private EntityManager em;

	@Transactional
	public void persist(Origem origem) {
		em.persist(origem);
	}

	public List<Origem> findAll() {
		Query query = em
				.createQuery("select origem from Origem origem order by resumo");
		@SuppressWarnings("unchecked")
		List<Origem> result = (List<Origem>) query.getResultList();
		return Collections.unmodifiableList(result);
	}

	public Origem findById(Long idOrigem) {
		return em.find(Origem.class, idOrigem);
	}

	@Transactional
	public void update(Origem origem) {
		em.merge(origem);
	}

	@Transactional
	public void delete(Origem origem) {
		Origem origemAttached = em.find(Origem.class, origem.getId());
		em.remove(origemAttached);
	}

}
