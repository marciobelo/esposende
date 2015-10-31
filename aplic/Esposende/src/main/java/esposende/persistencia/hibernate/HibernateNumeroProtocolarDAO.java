package esposende.persistencia.hibernate;

import esposende.entidade.NumeroProtocolar;
import esposende.persistencia.NumeroProtocolarDAO;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Calendar;

@Repository
public class HibernateNumeroProtocolarDAO implements NumeroProtocolarDAO {

    @PersistenceContext
    private EntityManager em = null;

    @Override
    public NumeroProtocolar gerarNovoProcotolo() {
        Calendar cal = Calendar.getInstance();
        int ano = cal.get(Calendar.YEAR);
        Query query = em.createNativeQuery("select max(seq) from NumeroProtocolar where ano = :ano").
                setParameter("ano", ano);
        Integer seq = (Integer) query.getSingleResult();
        if( seq == null ) {
            seq = 1;
        } else {
            seq++;
        }
        return new NumeroProtocolar(seq,ano);
    }

    @Override
    public NumeroProtocolar obterPorId(Long idNumeroProtocolar) {
        return em.find(NumeroProtocolar.class,idNumeroProtocolar);
    }

}
