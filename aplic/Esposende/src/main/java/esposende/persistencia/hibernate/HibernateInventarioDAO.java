package esposende.persistencia.hibernate;

import esposende.entidade.BemPermanente;
import esposende.entidade.Confere;
import esposende.entidade.Inventario;
import esposende.entidade.Responsavel;
import esposende.persistencia.InventarioDAO;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Calendar;
import java.util.List;

@Repository
public class HibernateInventarioDAO implements InventarioDAO {
    @PersistenceContext
    private EntityManager em;

    @Override
    public Inventario findById(Long id) {
        return em.find(Inventario.class, id);
    }

    @Override
    public Inventario buscarPorResponsavel(Responsavel responsavel) {
        String query = "select i from Inventario i " +
                "where i.responsavel = :responsavel " +
                "order by i.protocolo.ano desc, i.protocolo.seq desc";
        List<Inventario> list = em.createQuery(query)
                .setParameter("responsavel", responsavel)
                .setMaxResults(1)
                .getResultList();
        return list.size() == 1 ? list.get(0) : null;
    }

    @Override
    public List<Inventario> listarPorResponsavel(Responsavel responsavel) {
        String query = "select i from Inventario i " +
                "where i.responsavel = :responsavel " +
                "order by i.protocolo.ano desc, i.protocolo.seq desc";
        return em.createQuery(query)
                .setParameter("responsavel", responsavel)
                .getResultList();
    }

    @Override
    public void persist(Inventario inventario) {
        em.persist(inventario);
    }

    @Override
    public void update(Inventario inventario) {
        em.merge(inventario);
    }

    @Override
    public List<Inventario> listaEmAberto() {
        return em.createQuery("select i from Inventario i where i.dataFechamento is null").getResultList();
    }

    @Override
    public List<BemPermanente> bensInventarioAtraso() {
        String query = "select b from BemPermanente b where b not in (select c.bemPermanente from Confere c where c.inventario.dataFechamento > :data and c.bemPermanente.baixa is null)";
        Calendar data = Calendar.getInstance();
        data.add(Calendar.MONTH, -6);
	    List data1 = em.createQuery(query)
			    .setParameter("data", data.getTime())
			    .getResultList();
	    return data1;
    }

    @Override
    public List<BemPermanente> listaPorSituacao(Confere.TipoSituacaoConfere situacao) {
        return em.createQuery("select distinct c.bemPermanente from Confere c where c.situacao = :situacao and c.inventario.dataFechamento is not null and c.bemPermanente.baixa is null")
                .setParameter("situacao", situacao)
                .getResultList();
    }

    @Override
    public Inventario buscaUltimoInventario(BemPermanente bemPermanente) {
        List<Confere> c = em.createQuery("select c from Confere c where c.bemPermanente = :bem " +
                "and c.inventario.dataFechamento is not null order by c.inventario.dataFechamento desc")
                .setParameter("bem", bemPermanente).setMaxResults(1)
                .getResultList();
        return c.size() == 1 ? c.get(0).getInventario():null;
    }
}
