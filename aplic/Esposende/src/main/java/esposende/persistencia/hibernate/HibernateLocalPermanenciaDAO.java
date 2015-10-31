package esposende.persistencia.hibernate;

import esposende.entidade.LocalPermanencia;
import esposende.persistencia.LocalPermanenciaDAO;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class HibernateLocalPermanenciaDAO implements LocalPermanenciaDAO {

	@PersistenceContext
	private EntityManager em;

	@Transactional
	public void persist(LocalPermanencia localPermanencia) {
        em.persist(localPermanencia);
	}

	@Transactional
	public void merge(LocalPermanencia localPermanencia) {
        em.merge(localPermanencia);
	}

	public LocalPermanencia findById(Long id) {
        return em.find(LocalPermanencia.class, id);
	}

	public List<LocalPermanencia> listAll() {
		return em.createQuery("select l from LocalPermanencia l order by l.nome").getResultList();
	}

    @Override
    public void excluir( Long id ) {
        em.remove( this.findById(id) );
    }
}
