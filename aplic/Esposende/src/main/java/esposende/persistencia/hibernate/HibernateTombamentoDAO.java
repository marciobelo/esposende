package esposende.persistencia.hibernate;

import esposende.entidade.Tombamento;
import esposende.persistencia.TombamentoDAO;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.util.List;

@Repository
public class HibernateTombamentoDAO implements TombamentoDAO {

	@PersistenceContext
	private EntityManager em;

	@Transactional
	public void persist(Tombamento tombamento) {
		em.persist(tombamento);
	}

	@Transactional
	public void merge(Tombamento tombamento) {
		em.merge(tombamento);
	}

	public Tombamento findById(Serializable id) {
		return em.find(Tombamento.class, id);
	}

	public Tombamento findByCodigo(String codigo) {
		String query = "from Tombamento where codTombamento = :codigo";
        @SuppressWarnings("unchecked")
        List<Tombamento> tombamentos = em.createQuery(query)
				.setParameter("codigo", codigo)
				.getResultList();

		Tombamento t = null;

		if(tombamentos.size() == 1) {
			t = tombamentos.get(0);
		}

		return t;
	}
}
