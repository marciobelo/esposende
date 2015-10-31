package esposende.persistencia.hibernate;

import esposende.entidade.BemPermanente;
import esposende.entidade.Responsavel;
import esposende.entidade.TermoSubRogo;
import esposende.persistencia.TermoSubRogoDAO;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Collections;
import java.util.List;

@Repository
public class HibernateTermoSubRogoDAO implements TermoSubRogoDAO {

	@PersistenceContext
	private EntityManager em = null;

	@Override
	public void salvar(TermoSubRogo termoSubRogo) {
		if (termoSubRogo.getId() == null) {
			em.persist(termoSubRogo);
		} else {
			em.merge(termoSubRogo);
		}
	}

	@Override
	public List<TermoSubRogo> obterPorResponsavel(Responsavel responsavel) {
		Query query = em.createQuery("select termo from TermoSubRogo termo where termo.subrogado = :responsavel")
				.setParameter("responsavel", responsavel);
		@SuppressWarnings("unchecked")
		List<TermoSubRogo> result = (List<TermoSubRogo>) query.getResultList();
		return Collections.unmodifiableList(result);
	}

	@Override
	public List<TermoSubRogo> obterTodos() {
		Query query = em.createQuery("select termo from TermoSubRogo termo");
		@SuppressWarnings("unchecked")
		List<TermoSubRogo> result = (List<TermoSubRogo>) query.getResultList();
		return Collections.unmodifiableList(result);
	}

	@Override
	public TermoSubRogo obterPorId(Long idTermoSubRogo) {
		return em.find(TermoSubRogo.class, idTermoSubRogo);
	}

	@Override
	public Long numeroSubRogados() {
		return Long.valueOf(listaSubRogados().size());
	}

	@Override
	public List<BemPermanente> listaSubRogados() {
		return em.createQuery("select distinct t.arrolados from TermoSubRogo t where t.dataEncerramento is null and t.dataSubRogo is not null").getResultList();
	}

	@Override
	public List<TermoSubRogo> listaTermosAbertos() {
		return em.createQuery("select t from TermoSubRogo t where t.dataEncerramento is null and t.dataSubRogo is not null").getResultList();
	}

	@Override
	public TermoSubRogo buscaSubRogoVigente(BemPermanente bemPermanente) {
		List<TermoSubRogo> termo = em.createQuery("select t from TermoSubRogo t " +
				"where :bem member of t.arrolados and t.dataEncerramento is null order by t.dataEmissao desc")
				.setParameter("bem", bemPermanente)
				.setMaxResults(1).getResultList();
		return termo.size() == 1 ? termo.get(0) : null;
	}

	@Override
	public TermoSubRogo subRogoVigenteBem(BemPermanente bemPermanente) {

		List<TermoSubRogo> subrogos = em.createQuery("select t from TermoSubRogo t " +
				"where t.dataEncerramento is null " +
				"and :bem member of t.arrolados").setParameter("bem", bemPermanente).getResultList();

		TermoSubRogo termo = subrogos.size() > 0 ? subrogos.get(0) : null;

		return termo;
	}
}
