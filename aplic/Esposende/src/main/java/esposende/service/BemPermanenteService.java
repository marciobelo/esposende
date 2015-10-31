package esposende.service;

import esposende.entidade.*;
import esposende.entidade.Confere.TipoSituacaoConfere;
import esposende.persistencia.BemPermanenteDAO;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;

@Component
public class BemPermanenteService {

	@Inject
	private BemPermanenteDAO bemPermanenteDao;

	/**
	 * Grava um bem no banco de dados
	 *
	 * @param bem
	 */
	public void persist(BemPermanente bem) {
		RegistroOcorrencia registro = new RegistroOcorrencia(TipoRegistroOcorrencia.CRIACAO, "Cadastrado no Sistema");
		bem.adicionarRegistroOcorrencia(registro);
		bemPermanenteDao.persist(bem);
	}

	/**
	 * Atualiza um bem no banco de dados
	 *
	 * @param bem
	 */
    @Transactional
	public void update(BemPermanente bem) {
		bemPermanenteDao.update(bem);
	}

	/**
	 * Remove um bem do banco de dados
	 *
	 * @param bem
	 */
	public void delete(BemPermanente bem) {
		bemPermanenteDao.delete(bem);
	}

	/**
	 * Busca um bem de acordo com o id no banco de dados
	 *
	 * @param idBemPermanente
	 * @return
	 */
	public BemPermanente findById(long idBemPermanente) {
		return bemPermanenteDao.findById(idBemPermanente);
	}

	/**
	 * Lista todos os bens cadastrados
	 *
	 * @return
	 */
	public List<BemPermanente> listar() {
		return bemPermanenteDao.listar();
	}

	/**
	 * Lista os bens do responsável informado
	 *
	 * @param responsavel
	 * @return Lista de bens
	 */
	public List<BemPermanente> listarPorResponsavel(Responsavel responsavel) {
		return bemPermanenteDao.listarPorResponsavel(responsavel);
	}

	/**
	 * Lista os bens de acordo com o código do documento de aquisição
	 *
	 * @param documento
	 * @return
	 */
	public List<BemPermanente> listarPorDocumentoAquisicao(String documento) {
		return bemPermanenteDao.listarPorDocumentoAquisicao(documento);
	}

	/**
	 * Lista os bens por situação de inventário
	 *
	 * @param situacao
	 * @return
	 */
	public List<BemPermanente> listarPorSituacao(TipoSituacaoConfere situacao) {
		return bemPermanenteDao.listarPorSituacao(situacao);
	}

	/**
	 * Lista bens de acordo com a data de aquisição
	 *
	 * @param dataAquisicao
	 * @return
	 */
	public List<BemPermanente> listarPorDataAquisicao(Date dataAquisicao) {
		return bemPermanenteDao.listarPorDataAquisicao(dataAquisicao);
	}

	/**
	 * Lista os bens por parte da descrição do bem
	 *
	 * @param parteDescricao
	 * @return
	 */
	public List<String> listaDescricoes(String parteDescricao) {
		return bemPermanenteDao.listarDescricoes(parteDescricao);
	}

	/**
	 * Lista os bens de acordo com os critérios definidos
	 *
	 * @param parteDescricao
	 * @param origem
	 * @param responsavel
	 * @param codigoTombamento
	 * @param local
	 * @return
	 */
	public List<BemPermanente> listarPorCriterios(String parteDescricao,
	                                              Origem origem, Responsavel responsavel, String codigoTombamento, LocalPermanencia local) {
		return bemPermanenteDao.listarPorCriterios(parteDescricao, origem,
				responsavel, codigoTombamento, local);
	}

	/**
	 * Lista os nomes das empresas identificadas em documentos de aquisição
	 * de acordo com a parte do nome informado
	 *
	 * @param empresa
	 * @return
	 */
	public List<String> listaEmpresas(String empresa) {
		return bemPermanenteDao.listarEmpresas(empresa);
	}

	/**
	 * Lista números de documentos de aquisição
	 * de acordo com trecho do número informado
	 *
	 * @param documento
	 * @return
	 */
	public List<String> listaDocumentos(String documento) {
		return bemPermanenteDao.listarDocumentos(documento);
	}

	/**
	 * Lista códigos de tombamento de acordo com o trecho do código informado
	 *
	 * @param tombamento
	 * @return
	 */
	public List<String> listaTombamentos(String tombamento) {
		return bemPermanenteDao.listarTombamentos(tombamento);
	}

	/**
	 * Lista os bens de acordo com mês e ano de tombamento informados
	 *
	 * @param ano
	 * @param mes
	 * @return
	 */
	public List<BemPermanente> listaPorDataTombamento(Integer ano, Integer mes) {
		return bemPermanenteDao.listaPorDataTombamento(ano, mes);
	}

	/**
	 * Informa a quantidade de bens cadastrados
	 *
	 * @return
	 */
	public Long getTotalbens() {
		return bemPermanenteDao.totalBens();
	}

	/**
	 * Informa a quantidade de bens que tem código de tombamento
	 *
	 * @return
	 */
	public Long contarBensPatrimoniados() {
		return bemPermanenteDao.contarBensPatrimoniados();
	}

	/**
	 * Lista os bens marcados como não localizados e seus respectivos inventários
	 *
	 * @return
	 */
	public List<Object[]> listaNaoLocalizados() {
		return bemPermanenteDao.listaNaoLocalizados();
	}

	/**
	 * Quantidade de bens de acordo com situação no último inventário
	 *
	 * @param situacao
	 * @return
	 */
	public Long contaPorSitucaoConfere(TipoSituacaoConfere situacao) {
		return bemPermanenteDao.contaPorSituacaoConfere(situacao);
	}

	/**
	 * Lista bens que não possuem fotos anexadas
	 *
	 * @return
	 */
	public List<BemPermanente> listarBensSemFotos() {
		return bemPermanenteDao.listarBensSemFotos();
	}

	/**
	 * Lista bens que não tem documentos de aquisição associados
	 *
	 * @return
	 */
	public List<BemPermanente> listarBensSemDocumentosAquisicao() {
		return bemPermanenteDao.listarBensSemDocumentosAquisicao();
	}

	/**
	 * Lista os bens sem código de tombamento
	 *
	 * @return
	 */
	public List<BemPermanente> listarNaoPatrimoniados() {
		return bemPermanenteDao.listarNaoPatrimoniados();
	}

	/**
	 * Quantidade de bens que estão em documentos de baixa sem data de baixa contábil
	 *
	 * @return
	 */
	public Long contarBensEmBaixa() {
		return bemPermanenteDao.contarBensEmBaixa();
	}

	/**
	 * Quantidade de bens que estão em documentos de baixa com data de baixa contábil
	 *
	 * @return
	 */
	public Long contarBensBaixados() {
		return bemPermanenteDao.contarBensBaixados();
	}

	/**
	 * Quantidade de bens sem código de tombamento
	 *
	 * @return
	 */
	public Long contarBensNaoPatrimoniados() {
		return bemPermanenteDao.contarBensNaoPatrimoniados();
	}
}
