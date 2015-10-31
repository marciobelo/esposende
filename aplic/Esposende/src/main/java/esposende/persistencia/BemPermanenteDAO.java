package esposende.persistencia;

import esposende.entidade.*;

import java.util.Date;
import java.util.List;

public interface BemPermanenteDAO {
	void persist(BemPermanente bem);

	void update(BemPermanente bem);

	void delete(BemPermanente bem);

	BemPermanente findById(long idBemPermanente);

	List<BemPermanente> listar();

	List<BemPermanente> listarPorResponsavel(Responsavel responsavel);

	List<BemPermanente> listarPorDocumentoAquisicao(String documento);

	List<BemPermanente> listarPorSituacao(Confere.TipoSituacaoConfere situacao);

	List<BemPermanente> listarPorDataAquisicao(Date dataAquisicao);

	List<BemPermanente> listarPorCriterios(String parteDescricao,
	                                       Origem origem, Responsavel responsavel, String codigoTombamento, LocalPermanencia local);

	List<String> listarDescricoes(String parteDescricao);

	List<String> listarEmpresas(String empresa);

	List<String> listarDocumentos(String documento);

	List<String> listarTombamentos(String tombamento);

	List<BemPermanente> listaPorDataTombamento(Integer ano, Integer mes);

	Long totalBens();

	Long contarBensPatrimoniados();

	Long contarBensNaoPatrimoniados();

	List<Object[]> listaNaoLocalizados();

	Long contaPorSituacaoConfere(Confere.TipoSituacaoConfere situacaoConfere);

	List<BemPermanente> listarBensSemFotos();

	List<BemPermanente> listarBensSemDocumentosAquisicao();

	List<BemPermanente> listarNaoPatrimoniados();

    Long contarBensEmBaixa();

    Long contarBensBaixados();
}
