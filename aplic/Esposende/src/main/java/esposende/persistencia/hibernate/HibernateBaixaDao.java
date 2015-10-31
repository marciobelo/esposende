package esposende.persistencia.hibernate;

import esposende.entidade.Baixa;
import esposende.entidade.BemPermanente;
import esposende.persistencia.BaixaDao;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class HibernateBaixaDao implements BaixaDao {

	@PersistenceContext
	private EntityManager em;

	@Override
	@Transactional
	public void persist(Baixa baixa) {
		em.persist(baixa);
	}

	@Override
	@Transactional
	public void merge(Baixa baixa) {
		em.merge(baixa);
		for (BemPermanente bem : baixa.getBens()) {
			bem.setBaixa(baixa);
			em.merge(bem);
		}

		em.createQuery("update BemPermanente b " +
				"set b.baixa = null " +
				"where b.baixa = :baixa " +
				"and b not in (:bens)")
				.setParameter("baixa", baixa)
				.setParameter("bens", baixa.getBens())
				.executeUpdate();
	}

	@Override
	public Baixa findById(Long id) {
		return em.find(Baixa.class, id);
	}

	@Override
	public List<Baixa> listarTodos() {
		return em.createQuery("select b from Baixa b order by b.dataCriacao").getResultList();
	}

	@Override
	public List<BemPermanente> listaEmBaixa() {
		return em.createQuery("select b from BemPermanente b " +
				"where b.baixa is not null " +
				"and b.baixa.dataBaixaContabil is null").getResultList();
	}

	@Override
	public List<BemPermanente> listaBaixados() {
		return em.createQuery("select b from BemPermanente b " +
				"where b.baixa is not null " +
				"and b.baixa.dataBaixaContabil is not null").getResultList();
	}
}
