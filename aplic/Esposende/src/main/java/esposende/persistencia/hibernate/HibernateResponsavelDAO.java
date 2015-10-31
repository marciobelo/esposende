package esposende.persistencia.hibernate;

import esposende.entidade.Responsavel;
import esposende.persistencia.ResponsavelDAO;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Collections;
import java.util.List;

@Repository
public class HibernateResponsavelDAO implements ResponsavelDAO {

	@PersistenceContext
	private EntityManager em = null;

	@Transactional
	public void persist(Responsavel responsavel) {
		em.persist(responsavel);
	}

	public List<Responsavel> findAll() {
		Query query = em.createQuery("select r from Responsavel r order by matricula");
		@SuppressWarnings("unchecked")
		List<Responsavel> result = (List<Responsavel>) query.getResultList();
		return Collections.unmodifiableList(result);
	}

	public Responsavel findById(Long idResponsavel) {
		return em.find(Responsavel.class, idResponsavel);
	}

	@Transactional
	public void update(Responsavel responsavel) {
		em.merge(responsavel);
	}

	@Transactional
	public void delete(Responsavel responsavel) {
		Responsavel resp = em.find(Responsavel.class,
				responsavel.getId());
		em.remove(resp);
	}

}
