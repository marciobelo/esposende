package esposende.entidade;

import javax.persistence.*;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Grupo de bens que se pretende ou foram desincorporados sob uma mesma justificativa. Ao criar um registro
 * de Baixa e relacionar um bem, o mesmo passa a constar no estado EM_BAIXA. Após o atributo dataBaixaContabil ser
 * informado, o bem passa ao estado BAIXADO.
 */
@Entity
public class Baixa {

	@Id
	@GeneratedValue
	private Long id;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private NumeroProtocolar protocolo = new NumeroProtocolar();

	@Column(nullable = false)
	private Date dataCriacao;

	private String numeroProcesso;

	private Date dataProcesso;

	@Column(nullable = false)
	private String justificativa;

	private Date dataTermoBaixa;

	private Date dataBaixaContabil;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<DocumentoDigital> comprovantes = new HashSet<DocumentoDigital>();

	@OneToMany(fetch = FetchType.EAGER)
	@JoinColumn(name = "baixa_id")
	private Set<BemPermanente> bens = new HashSet<BemPermanente>();

	public Baixa() {
	}

	public Baixa(NumeroProtocolar numeroProtocolar, String justificativa, Set<BemPermanente> bens) {
		setProtocolo(numeroProtocolar);
		setJustificativa(justificativa);
		setBens(bens);
	}

	public void setProtocolo(NumeroProtocolar protocolo) {
		if (protocolo == null) throw new IllegalArgumentException("Protocolo é obrigatório.");
		this.protocolo = protocolo;
	}

	public void setJustificativa(String justificativa) {
		if (justificativa == null || justificativa.length() == 0)
			throw new IllegalArgumentException("Justificativa deve estar preenchida.");
		this.justificativa = justificativa;
	}

	public void setBens(Set<BemPermanente> bens) {
		if (bens == null || bens.size() == 0) throw new IllegalArgumentException("Deve ser informado ao menos um bem.");
		this.bens = bens;
	}

	public String getJustificativa() {
		return justificativa;
	}

	public NumeroProtocolar getProtocolo() {
		return protocolo;
	}

	public Set<BemPermanente> getBens() {
		return bens;
	}

	public void setDataBaixaContabil(Date dataBaixaContabil) {
		if (this.dataTermoBaixa == null && dataBaixaContabil != null)
			throw new IllegalStateException("Data do Termo de Baixa não pode ser nula ao se registrar Data de Baixa Contábil.");
		if (dataBaixaContabil != null && dataBaixaContabil.before(this.dataTermoBaixa))
			throw new IllegalStateException("Data Baixa Contábil não pode ser anterior à Data do Termo de Baixa.");
		this.dataBaixaContabil = dataBaixaContabil;
	}

	public void setDataTermoBaixa(Date dataTermoBaixa) {
		this.dataTermoBaixa = dataTermoBaixa;
	}

	public Long getId() {
		return id;
	}

	public Date getDataCriacao() {
		return dataCriacao;
	}

	public String getNumeroProcesso() {
		return numeroProcesso;
	}

	public Date getDataProcesso() {
		return dataProcesso;
	}

	public Date getDataTermoBaixa() {
		return dataTermoBaixa;
	}

	public Date getDataBaixaContabil() {
		return dataBaixaContabil;
	}

	public void adicionaComprovante(DocumentoDigital documento){
		comprovantes.add(documento);
	}


	public void removeComprovante(DocumentoDigital documento){
		comprovantes.remove(documento);
	}

	public Set<DocumentoDigital> getComprovantes() {
		return Collections.unmodifiableSet(comprovantes);
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public void setNumeroProcesso(String numeroProcesso) {
		this.numeroProcesso = numeroProcesso;
	}

	public void setDataProcesso(Date dataProcesso) {
		this.dataProcesso = dataProcesso;
	}

	public void setComprovantes(Set<DocumentoDigital> comprovantes) {
		this.comprovantes = comprovantes;
	}

	public Integer getNumeroBens() {
		return bens.size();
	}
}
