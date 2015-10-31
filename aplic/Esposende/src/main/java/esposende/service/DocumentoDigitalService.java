package esposende.service;

import esposende.entidade.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;

@Service
public class DocumentoDigitalService {

	@PersistenceContext
	private EntityManager em = null;

	/**
	 * Busca um documento digital de acordo com id no banco de dados
	 *
	 * @param id
	 * @return
	 */
	public DocumentoDigital findById(Serializable id) {
		return em.find(DocumentoDigital.class, id);
	}

	/**
	 * Grava documento no banco de dados
	 *
	 * @param foto
	 */
	@Transactional
	public void persist(DocumentoDigital foto) {
		em.persist(foto);
	}

	/**
	 * Adiciona um documento a um bem permanente, termo de subrogo, inventário, tombamento ou baixa
	 *
	 * @param classeDestino
	 * @param id
	 * @param documento
	 */
	@Transactional
	public void adicionarImagem(String classeDestino, Long id, byte[] documento) {

		// Mal cheiro!
		if ("BemPermanente".equals(classeDestino)) {

			BemPermanente bem = em.find(BemPermanente.class, id);
			bem.adicionarFoto(new DocumentoDigital("Foto", documento, DocumentoDigital.TipoMime.JPEG));
			em.persist(bem);
		} else if ("TermoSubRogo".equals(classeDestino)) {
			TermoSubRogo termoSubRogo = em.find(TermoSubRogo.class, id);
			termoSubRogo.adicionarTermoAssinado(new DocumentoDigital("Foto", documento, DocumentoDigital.TipoMime.JPEG));
			em.persist(termoSubRogo);

		} else if ("Inventario".equals(classeDestino)) {
			Inventario inventario = em.find(Inventario.class, id);
			inventario.adicionaRelatorioAssinado(new DocumentoDigital("RelatorioAssinado", documento, DocumentoDigital.TipoMime.JPEG));
			em.persist(inventario);
		} else if ("Tombamento".equals(classeDestino)) {
			Tombamento tombamento = em.find(Tombamento.class, id);
			tombamento.getComprovantesOperacao().add(new DocumentoDigital("Foto", documento, DocumentoDigital.TipoMime.JPEG));
			em.persist(tombamento);
		} else if ("Baixa".equals(classeDestino)) {
			Baixa baixa = em.find(Baixa.class, id);
			baixa.adicionaComprovante(new DocumentoDigital("Foto", documento, DocumentoDigital.TipoMime.JPEG));
			em.persist(baixa);
		} else {
			throw new IllegalStateException(String.format("Nao existe classe %s", classeDestino));
		}

	}

	/**
	 * Remove um documento de um bem permanente, termo de subrogo, inventário, tombamento ou baixa
	 *
	 * @param classeDestino
	 * @param id
	 * @param idDocumentoDigital
	 */
	@Transactional
	public void excluir(String classeDestino, Long id, Long idDocumentoDigital) {
		if ("BemPermanente".equals(classeDestino)) {
			BemPermanente bem = em.find(BemPermanente.class, id);
			for (DocumentoDigital documentoDigital : bem.getFotos()) {
				if (documentoDigital.getId().equals(idDocumentoDigital)) {
					bem.excluirFoto(documentoDigital);
					em.persist(bem);
					break;
				}
			}
		} else if ("TermoSubRogo".equals(classeDestino)) {
			TermoSubRogo termoSubRogo = em.find(TermoSubRogo.class, id);
			for (DocumentoDigital documentoDigital : termoSubRogo.getTermosAssinados()) {
				if (documentoDigital.getId().equals(idDocumentoDigital)) {
					termoSubRogo.excluirTermoAssinado(documentoDigital);
					em.persist(termoSubRogo);
					break;
				}
			}
		} else if ("Inventario".equals(classeDestino)) {
			Inventario inventario = em.find(Inventario.class, id);
			for (DocumentoDigital documentoDigital : inventario.getRelatorioAssinado()) {
				if (documentoDigital.getId().equals(idDocumentoDigital)) {
					inventario.removeRelatorioAssinado(documentoDigital);
					em.persist(inventario);
					break;
				}
			}
		} else if ("Tombamento".equals(classeDestino)) {
			Tombamento tombamento = em.find(Tombamento.class, id);

			for (DocumentoDigital documentoDigital : tombamento.getComprovantesOperacao()) {
				if (documentoDigital.getId().equals(idDocumentoDigital)) {
					tombamento.getComprovantesOperacao().remove(documentoDigital);
					em.persist(tombamento);
					break;
				}
			}

		} else if ("Baixa".equals(classeDestino)) {
			Baixa baixa = em.find(Baixa.class, id);

			for (DocumentoDigital documentoDigital : baixa.getComprovantes()) {
				if (documentoDigital.getId().equals(idDocumentoDigital)) {
					baixa.removeComprovante(documentoDigital);
					em.persist(baixa);
					break;
				}
			}

		} else {
			throw new IllegalStateException(String.format("Nao existe classe %s", classeDestino));
		}
	}
}
