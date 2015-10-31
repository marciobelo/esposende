package esposende.service;

import esposende.entidade.Responsavel;
import esposende.persistencia.ResponsavelDAO;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.io.IOException;
import java.util.List;

@Component
public class ResponsavelService {

	@Inject
	private ResponsavelDAO responsavelDAO;
	@Inject
	private ConfiguracoesService configuracoesService;

	/**
	 * Grava um responsável no banco de dados
	 *
	 * @param responsavel
	 */
	public void persist(Responsavel responsavel) {
		responsavelDAO.persist(responsavel);
	}

	/**
	 * Lista todos os responsáveis
	 *
	 * @return
	 */
	public List<Responsavel> findAll() {
		return responsavelDAO.findAll();
	}

	/**
	 * Busca um responsável por id no banco
	 *
	 * @param idResponsavel
	 * @return
	 */
	public Responsavel findById(Long idResponsavel) {
		return responsavelDAO.findById(idResponsavel);
	}

	/**
	 * Atualiza um responsável no banco de dados
	 *
	 * @param responsavel
	 */
	public void update(Responsavel responsavel) {
		responsavelDAO.update(responsavel);
	}

	/**
	 * Remove um responsável do banco de dados
	 *
	 * @param responsavel
	 */
	public void delete(Responsavel responsavel) {
		responsavelDAO.delete(responsavel);
	}

	/**
	 * Busca o responsável institucional atual
	 *
	 * @return
	 * @throws IOException
	 */
	public Responsavel obterResposavelInstitucional() throws IOException {
		return configuracoesService.getConfiguracoes().getResponsavelInstitucional();
	}

}
