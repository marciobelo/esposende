package esposende.persistencia.hibernate;

import esposende.entidade.CodigoContabil;
import esposende.entidade.Tombamento;
import esposende.persistencia.CodigoContabilDAO;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

@Repository
//@Qualifier("codigoContabilDao")
public class HibernateCodigoContabilDAO implements CodigoContabilDAO {

	@PersistenceContext
	private EntityManager em;

	public CodigoContabil findById(Serializable id) {
		return em.find(CodigoContabil.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<CodigoContabil> listAll() {
		return em.createQuery("select c from CodigoContabil c order by c.codigo").getResultList();
	}

	@Override
	public CodigoContabil findByCodigo(String codigo) {
		@SuppressWarnings("unchecked")
		List<CodigoContabil> listaCodigoContabil =
				em.createQuery("select c from CodigoContabil c where c.codigo = :codigo")
						.setParameter("codigo", codigo)
						.getResultList();
		return listaCodigoContabil.get(0);
	}

	@Override
	public List<Object[]> listaTotalPorCodigoContabil() {
		String query = "select b.tombamento.codigoContabil, sum(b.tombamento.valorOperacao) from BemPermanente b group by b.tombamento.codigoContabil";
		return em.createQuery(query).getResultList();
	}

	@Override
	public void persist(CodigoContabil codigoContabil) {
		em.persist(codigoContabil);
	}

	@Override
	public void merge(CodigoContabil codigoContabil) {
		em.merge(codigoContabil);
	}

	@Override
	public BigDecimal saldoInicialNaData(Calendar data, CodigoContabil codigoContabil) {
		return (BigDecimal) em.createQuery("select sum(t.valorOperacao) from Tombamento t where t.codigoContabil = :codigo " +
				"and cast(t.dataTombamento as date) <= cast(:data as date)")
				.setParameter("codigo", codigoContabil)
				.setParameter("data", data)
				.getSingleResult();
	}

	@Override
	public BigDecimal entradasNoPeriodo(Calendar inicio, Calendar fim, CodigoContabil codigoContabil) {
		return (BigDecimal) em.createQuery("select sum(t.valorOperacao) from Tombamento t " +
				"where t.codigoContabil = :codigo " +
				"and cast(t.dataTombamento as date) between cast(:inicio as date) and cast(:fim as date)")
				.setParameter("codigo", codigoContabil)
				.setParameter("inicio", inicio)
				.setParameter("fim", fim)
				.getSingleResult();
	}

	@Override
	public BigDecimal saidasNoPeriodo(Calendar inicio, Calendar fim, CodigoContabil codigoContabil) {
		List<Tombamento> tombamentos = em.createQuery("select distinct b.tombamento from BemPermanente b " +
				"where b.tombamento.codigoContabil = :codigo " +
				"and cast(b.baixa.dataBaixaContabil as date) between cast(:inicio as date) and cast(:fim as date)")
				.setParameter("codigo", codigoContabil)
				.setParameter("inicio", inicio)
				.setParameter("fim", fim)
				.getResultList();

		BigDecimal saidas = BigDecimal.ZERO;

		for (Tombamento t : tombamentos) {
			saidas = saidas.add(t.getValorOperacao());
		}

		return saidas;
	}

    @Override
    public void delete(CodigoContabil codigoContabil) {
        em.remove(codigoContabil);
    }
}