package esposende.entidade;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

/**
 * Representa um processo de tomada de contas dos bens patrimoniais.
 */
@Entity
public class Inventario implements Serializable {
	private static final long serialVersionUID = 7539830852103276098L;

	@Id
	@GeneratedValue
	private Long id;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private NumeroProtocolar protocolo;

	@Column(nullable = false)
	@Temporal(TemporalType.DATE)
	private Date dataEmissao;

	@Column(nullable = true)
	@Temporal(TemporalType.DATE)
	private Date dataFechamento;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<DocumentoDigital> relatorioAssinado = new HashSet<DocumentoDigital>(0);

	@ManyToOne
	private Responsavel responsavel;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
	private Set<Confere> conferidos = new HashSet<Confere>();

	public Inventario() {
	}

	public Inventario(NumeroProtocolar protocolo, Date dataEmissao, Responsavel responsavel,
	                  List<BemPermanente> bensPermanentes) {
		setProtocolo(protocolo);
		setDataEmissao(dataEmissao);
		setResponsavel(responsavel);
		this.adicionarConferidos(bensPermanentes);
	}

	private void adicionarConferidos(List<BemPermanente> bensPermanentes) {
		if (bensPermanentes == null || bensPermanentes.size() == 0) {
			throw new IllegalArgumentException("A lista de bens conferidos deve ter ao menos um elemento.");
		}
		for (BemPermanente bem : bensPermanentes) {
			this.adicionarConferido(bem);
		}
	}

	public void adicionarConferido(BemPermanente bem) {
		this.conferidos.add(new Confere(bem, this, null));
	}

	public boolean removerConferido(BemPermanente bem) {
		for (Confere confere : this.conferidos) {
			if (confere.getBemPermanente().equals(bem)) {
				this.conferidos.remove(confere);
				return true;
			}
		}
		return false;
	}

	private void setResponsavel(Responsavel responsavel) {
		if (responsavel == null) {
			throw new IllegalArgumentException("Responsável não pode ser nulo.");
		}
		this.responsavel = responsavel;
	}

	/*
	 *  @param protocolo
	 */
	private void setProtocolo(NumeroProtocolar protocolo) {
		if (protocolo == null) {
			throw new IllegalArgumentException("Protocolo não pode ser nulo.");
		}
		this.protocolo = protocolo;
	}


	public void setDataEmissao(Date dataEmissao) throws IllegalArgumentException {
		if (dataEmissao == null) throw new IllegalArgumentException("A data de emissão não pode ser nula");
		this.dataEmissao = dataEmissao;
	}

	public void setDataFechamento(Date dataFechamento) throws IllegalArgumentException {
		if (dataFechamento != null && dataFechamento.before(dataEmissao))
			throw new IllegalArgumentException("A data fechamento não pode ser nula ou anterior à data inicial");
		this.dataFechamento = dataFechamento;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Número gerado sequencialmente para identificar o inventário
	 */
	public NumeroProtocolar getProtocolo() {
		return protocolo;
	}

	/**
	 * Data de emissão de um inventário. Início do período de inventariação.
	 *
	 * @return
	 */
	public Date getDataEmissao() {
		return (Date) this.dataEmissao.clone();
	}

	/**
	 * Data em que o inventário é encerrado. Requer que todos os bens estejamverificados.
	 *
	 * @return
	 */
	public Date getDataFechamento() {
		if (this.dataFechamento != null)
			return (Date) this.dataFechamento.clone();
		return this.dataFechamento;
	}

	/**
	 * Relatório impresso do inventário. É gerado contendo a lista dos bens do responsável.
	 * Ao final é digitalizado e anexado ao inventário.
	 * @return
	 */
	public Set<DocumentoDigital> getRelatorioAssinado() {
		return Collections.unmodifiableSet(relatorioAssinado);
	}

	public Responsavel getResponsavel() {
		return responsavel.clone();
	}

	public Set<Confere> getConferidos() {
		return Collections.unmodifiableSet(conferidos);
	}

	public boolean podeSerEncerrado() {
		return todosConferesFeitos() && temRelatorioAssinado() && !getEncerrado();
	}

	public boolean temRelatorioAssinado() {
		return !relatorioAssinado.isEmpty();
	}

	public boolean todosConferesFeitos() {
		for (Confere c : conferidos) {
			if (c.getSituacao() == null) {
				return false;
			}
		}
		return true;
	}

	public boolean getEncerrado() {
		return dataFechamento != null;
	}

	public void adicionaRelatorioAssinado(DocumentoDigital relatorioAssinado) {
		this.relatorioAssinado.add(relatorioAssinado);
	}


	public void removeRelatorioAssinado(DocumentoDigital relatorioAssinado) {
		this.relatorioAssinado.remove(relatorioAssinado);
	}
}
