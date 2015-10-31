package esposende.entidade;

import javax.persistence.*;
import java.util.*;

/**
 * Documento que oficializa a cessão de bens patrimoniais,
 * que ficarão sob a guarda de um responsável, a partir da
 * data de subrogo com o termo devidamente assinado. Na data
 * de fechamento, o contrato perde a validade e permanece
 * por histórico.
 */
@Entity
public class TermoSubRogo {

	@Id
	@GeneratedValue
	private Long id;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private NumeroProtocolar protocolo;

	@Temporal(TemporalType.DATE)
	private Date dataEmissao;

	@Column(nullable = true)
	private String proposito;

	@Temporal(TemporalType.DATE)
	private Date dataSubRogo;

	@Temporal(TemporalType.DATE)
	private Date dataEncerramento;

	@Temporal(TemporalType.DATE)
	private Date dataPrevistaEncerramento;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<DocumentoDigital> termosAssinados = new HashSet<DocumentoDigital>(0);

	@ManyToOne(fetch = FetchType.EAGER)
	private Responsavel subrogado;

	@ManyToMany(fetch = FetchType.EAGER)
	private Set<BemPermanente> arrolados;

	public TermoSubRogo() {
	}

	public TermoSubRogo(NumeroProtocolar protocolo, Date dataEmissao, Responsavel subrogado,
	                    String proposito, Set<BemPermanente> arrolados) {
		setProtocolo(protocolo);
		setDataEmissao(dataEmissao);
		setSubrogado(subrogado);
		setProposito(proposito);
		setArrolados(arrolados);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	private void setArrolados(Set<BemPermanente> arrolados) {
		if (arrolados == null || arrolados.isEmpty()) {
			throw new IllegalArgumentException("Deve haver ao menos um Bem arrolado.");
		}
		this.arrolados = arrolados;
	}

	private void setSubrogado(Responsavel subrogado) {
		if (subrogado == null) {
			throw new IllegalArgumentException("Responsável não pode ser nulo.");
		}
		this.subrogado = subrogado;
	}

	private void setDataEmissao(Date dataEmissao) {
		this.dataEmissao = dataEmissao;
	}

	private void setProtocolo(NumeroProtocolar protocolo) {
		this.protocolo = protocolo;
	}

	/**
	 * Número sequencial gerado pelo sistema para identificar os termos.
	 *
	 * @return
	 */
	public NumeroProtocolar getProtocolo() {
		return protocolo;
	}

	/**
	 * Data em que um sub rogo é emitido.
	 * Não é, necessariamente, a data de início do sub rogo.
	 *
	 * @return
	 */
	public Date getDataEmissao() {
		return dataEmissao != null ? (Date) dataEmissao.clone() : null;
	}

	/**
	 * Texto que explicita o propósito do sub rogo.
	 * Informa o motivo para que o sub rogo aconteça.
	 *
	 * @return
	 */
	public String getProposito() {
		return proposito;
	}

	/**
	 * Data de início do sub rogo.
	 * A partir desta data os bens estarão sob responsabilidade de um novo funcionário.
	 *
	 * @return
	 */
	public Date getDataSubRogo() {
		return dataSubRogo != null ? (Date) dataSubRogo.clone() : null;
	}

	/**
	 * Data efetiva de encerramento de um sub rogo.
	 *
	 * @return
	 */
	public Date getDataEncerramento() {
		return dataEncerramento != null ? (Date) dataEncerramento.clone() : null;
	}

	/**
	 * Termos impresso, assinados e,então, digitalizados e anexados ao Termo de Sub Rogo.
	 *
	 * @return
	 */
	public Set<DocumentoDigital> getTermosAssinados() {
		return termosAssinados;
	}

	public void setDataSubRogo(Date dataSubRogo) {
		if (dataSubRogo == null || dataSubRogo.before(dataEmissao)) {
			throw new IllegalArgumentException("Data de Subrogo não pode ser anterior à data de emissão.");
		}
		this.dataSubRogo = dataSubRogo;
	}

	public void setDataEncerramento(Date dataEncerramento) {
		if (dataEncerramento != null && dataSubRogo == null) {
			throw new IllegalArgumentException("Data de Subrogo deve estar preenchida para registrar a de encerramento.");
		}
		if (dataEncerramento != null && dataEncerramento.before(dataSubRogo)) {
			throw new IllegalArgumentException("Data de Encerramento não pode ser anterior à data de Subrogo.");
		}
		this.dataEncerramento = dataEncerramento;
	}

	public void setProposito(String proposito) {
		this.proposito = proposito;
	}

	public void setDataPrevistaEncerramento(Date dataPrevistaEncerramento) {
		this.dataPrevistaEncerramento = dataPrevistaEncerramento;
	}

	/**
	 * Data informada no cadastro do termo que informa o provável fim do período de sub rogo.
	 *
	 * @return
	 */
	public Date getDataPrevistaEncerramento() {
		return dataPrevistaEncerramento != null ? (Date) dataPrevistaEncerramento.clone() : null;
	}

	public void adicionarTermoAssinado(DocumentoDigital documentoDigital) {
		this.termosAssinados.add(documentoDigital);
	}

	public Responsavel getSubrogado() {
		return subrogado.clone();
	}

	public List<BemPermanente> getArrolados() {
		List<BemPermanente> clone = new ArrayList<BemPermanente>(arrolados.size());
		for (BemPermanente bem : arrolados) {
			clone.add(bem.clone());
		}
		return clone;
	}

	public void excluirTermoAssinado(DocumentoDigital documentoDigital) {
		this.termosAssinados.remove(documentoDigital);
	}
}
