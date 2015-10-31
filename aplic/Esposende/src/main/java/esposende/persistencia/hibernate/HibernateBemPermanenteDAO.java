package esposende.persistencia.hibernate;

import esposende.entidade.BemPermanente;
import esposende.entidade.Confere.TipoSituacaoConfere;
import esposende.entidade.LocalPermanencia;
import esposende.entidade.Origem;
import esposende.entidade.Responsavel;
import esposende.persistencia.BemPermanenteDAO;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Repository
public class HibernateBemPermanenteDAO implements BemPermanenteDAO {

	@PersistenceContext
	private EntityManager em;

	@Transactional
	public void persist(BemPermanente bem) {
		em.persist(bem);
	}

	@Transactional
	public void update(BemPermanente bem) {
		em.merge(bem);
	}

	@Transactional
	public void delete(BemPermanente bem) {
		BemPermanente bemAttached = em.find(BemPermanente.class, bem.getId());
		em.remove(bemAttached);
	}

	@SuppressWarnings("unchecked")
	public List<BemPermanente> listar() {
		Query query = em
				.createQuery("select bem from BemPermanente bem order by bem.descricao");
		List<BemPermanente> result = query.getResultList();
		return Collections.unmodifiableList(result);
	}

	@SuppressWarnings("unchecked")
	public List<BemPermanente> listarPorResponsavel(Responsavel responsavel) {
		Query query = em.createQuery("select bem from BemPermanente bem where bem.responsavel = :responsavel and bem.baixa is null")
				.setParameter("responsavel", responsavel);
		List<BemPermanente> result = query.getResultList();
		return Collections.unmodifiableList(result);
	}

	@SuppressWarnings("unchecked")
	public List<BemPermanente> listarPorDocumentoAquisicao(String documento) {
		Query query = em
				.createQuery(
						"select bem from BemPermanente bem where bem.tombamento.documentoHabil = :documento")
				.setParameter("documento", documento);
		List<BemPermanente> result = query.getResultList();
		return Collections.unmodifiableList(result);
	}

	@SuppressWarnings("unchecked")
	public List<BemPermanente> listarPorSituacao(TipoSituacaoConfere situacao) {
		Query q = em.createQuery("select confere.bemPermanente from Confere confere where confere.situacao = :situacao");
		q.setParameter("situacao", situacao);
		List<BemPermanente> bens = q.getResultList();
		return Collections.unmodifiableList(bens);
	}

	public List<BemPermanente> listarPorDataAquisicao(Date dataAquisicao) {
		return null;
	}

	public BemPermanente findById(long idBemPermanente) {
		return em.find(BemPermanente.class, idBemPermanente);
	}

	@SuppressWarnings("unchecked")
	public List<String> listarDescricoes(String parteDescricao) {
		Query q = em.createQuery("select bem.descricao "
				+ "from BemPermanente bem "
				+ "where bem.descricao like :parteDescricao");
		q.setParameter("parteDescricao", parteDescricao + "%");
		List<String> descricoes = q.getResultList();

		return Collections.unmodifiableList(descricoes);
	}

	@SuppressWarnings("unchecked")
	public List<BemPermanente> listarPorCriterios(String parteDescricao,
	                                              Origem origem, Responsavel responsavel, String codigoTombamento, LocalPermanencia local) {
		StringBuilder query = new StringBuilder(
				"select bem from BemPermanente bem");

		List<String> criterios = new ArrayList<String>();

		if (!parteDescricao.isEmpty()) {
			criterios.add(" and (bem.descricao like :parteDescricao1 "
					+ "or bem.descricao like :parteDescricao2 "
					+ "or bem.descricao like :parteDescricao3)");
		}

		if (responsavel != null)
			criterios.add(" and bem.responsavel = :responsavel");

		if (origem != null)
			criterios.add(" and bem.origem = :origem");

		if (!codigoTombamento.isEmpty())
			criterios.add(" and (bem.tombamento.codTombamento like :codigoTombamento1 "
					+ "or bem.tombamento.codTombamento like :codigoTombamento2 "
					+ "or bem.tombamento.codTombamento like :codigoTombamento3)");

		if (local != null)
			criterios.add(" and bem.localPermanencia = :local");

		if (criterios.size() > 0)
			query.append(" where ");

		for (int i = 0; i < criterios.size(); i++) {
			if (i == 0) {
				query.append(criterios.get(i).replace(" and ", " "));
			} else {
				query.append(criterios.get(i));
			}
		}

		Query q = em.createQuery(query.toString());

		if (!parteDescricao.isEmpty())
			q.setParameter("parteDescricao1", parteDescricao + "%")
					.setParameter("parteDescricao2", "%" + parteDescricao + "%")
					.setParameter("parteDescricao3", "%" + parteDescricao);

		if (responsavel != null)
			q.setParameter("responsavel", responsavel);

		if (origem != null)
			q.setParameter("origem", origem);

		if (!codigoTombamento.isEmpty())
			q.setParameter("codigoTombamento1", codigoTombamento + "%")
					.setParameter("codigoTombamento2",
							"%" + codigoTombamento + "%")
					.setParameter("codigoTombamento3", "%" + codigoTombamento);

		if (local != null)
			q.setParameter("local", local);

		return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<String> listarEmpresas(String empresa) {
		Query q = em.createQuery("select bem.tombamento.documentoHabil "
				+ "from BemPermanente bem "
				+ "where bem.tombamento.documentoHabil like :nomeEmpresa");
		q.setParameter("nomeEmpresa", empresa + "%");
		List<String> empresas = q.getResultList();

		return Collections.unmodifiableList(empresas);
	}

	@SuppressWarnings("unchecked")
	public List<String> listarDocumentos(String documento) {
		Query q = em
				.createQuery("select bem.tombamento.documentoHabil "
						+ "from BemPermanente bem "
						+ "where bem.tombamento.documentoHabil like :codigoDocumentoAquisicao");
		q.setParameter("codigoDocumentoAquisicao", documento + "%");
		List<String> documentos = q.getResultList();

		return Collections.unmodifiableList(documentos);
	}

	@SuppressWarnings("unchecked")
	public List<String> listarTombamentos(String tombamento) {
		Query q = em.createQuery("select bem.tombamento.codTombamento "
				+ "from BemPermanente bem "
				+ "where bem.tombamento.codTombamento like :codigoTombamento");
		q.setParameter("codigoTombamento", tombamento + "%");
		List<String> tombamentos = q.getResultList();

		return Collections.unmodifiableList(tombamentos);
	}

	@Override
	public List<BemPermanente> listaPorDataTombamento(Integer ano, Integer mes) {
		String query = "select b from BemPermanente b where year(b.tombamento.dataTombamento) = :ano" +
				" and month(b.tombamento.dataTombamento) = :mes";
		return Collections.unmodifiableList(em.createQuery(query)
				.setParameter("ano", ano)
				.setParameter("mes", mes)
				.getResultList()
		);
	}

	@Override
	public Long totalBens() {
		return (Long) em.createQuery("select count(b) from BemPermanente b").getSingleResult();
	}

	@Override
	public Long contarBensPatrimoniados() {
		return (Long) em.createQuery("select count(b) from BemPermanente b " +
                "where b.tombamento is not null " +
                "and b.baixa is null").getSingleResult();
	}

	@Override
	public Long contarBensNaoPatrimoniados() {
		return (Long) em.createQuery("select count(b) from BemPermanente b " +
				"where b.tombamento is null " +
				"and b.baixa is null").getSingleResult();
	}

	@Override
	public List<Object[]> listaNaoLocalizados() {
		String query = "select distinct c.bemPermanente, c.inventario from Confere c " +
                "where c.situacao = :situacao " +
                "and c.bemPermanente.baixa is null";
		Query q = em.createQuery(query);
		q.setParameter("situacao", TipoSituacaoConfere.NAO_LOCALIZADO);
		return q.getResultList();
	}

	@Override
	public Long contaPorSituacaoConfere(TipoSituacaoConfere situacaoConfere) {
		String query = "select count(distinct c) from Confere c " +
                "where c.situacao = :situacao " +
                "and c.inventario.dataFechamento is not null " +
                "and c.bemPermanente.baixa is null";
		return (Long) em.createQuery(query)
				.setParameter("situacao", situacaoConfere)
				.getSingleResult();
	}

	@Override
	public List<BemPermanente> listarBensSemFotos() {
		String query = "select b from BemPermanente b where b.fotos is empty and b.baixa is null";
		return em.createQuery(query).getResultList();
	}

	@Override
	public List<BemPermanente> listarBensSemDocumentosAquisicao() {
		//String query = "select b from BemPermanente b where b.documentoAquisicao is null";
		return new ArrayList<BemPermanente>(); //em.createQuery(query).getResultList();
	}

	@Override
	public List<BemPermanente> listarNaoPatrimoniados() {
		return em.createQuery("select b from BemPermanente b where b.tombamento is null and b.baixa is null").getResultList();
	}

    @Override
    public Long contarBensEmBaixa() {
        return (Long) em.createQuery("select count(b) from BemPermanente b " +
                "where b.baixa is not null " +
                "and b.baixa.dataBaixaContabil is null").getSingleResult();
    }

    @Override
    public Long contarBensBaixados() {
        return (Long) em.createQuery("select count(b) from BemPermanente b " +
                "where b.baixa is not null " +
                "and b.baixa.dataBaixaContabil is not null").getSingleResult();
    }
}
