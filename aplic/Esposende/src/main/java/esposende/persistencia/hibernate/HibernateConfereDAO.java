package esposende.persistencia.hibernate;

import esposende.entidade.Confere;
import esposende.persistencia.ConfereDAO;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class HibernateConfereDAO implements ConfereDAO {
    @PersistenceContext
    private EntityManager em;

    @Override
    public Confere findById(long id) {
        return em.find(Confere.class, id);
    }

    @Override
    public void update(Confere confere) {
        em.merge(confere);
    }
}
