package esposende.visao.controle.formbean;

import esposende.entidade.Baixa;
import esposende.entidade.BemPermanente;
import esposende.entidade.DocumentoDigital;

import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Set;

public class BaixaModel {

	private Long id;

	private Date dataCriacao;

	private String numeroProcesso;

	private Date dataProcesso;

	@Size(message = "Justificativa deve ser informada")
	private String justificativa;

	private Date dataTermoBaixa;

	private Date dataBaixaContabil;

	private Set<DocumentoDigital> comprovantes;

	private Set<BemPermanente> bens;

	private Long idProtocolo;
	private int seqProtocolo;
	private int anoProtocolo;

	public BaixaModel() {

	}

	public BaixaModel(Baixa baixa) {
		setBens(baixa.getBens());
		setComprovantes(baixa.getComprovantes());
		setDataBaixaContabil(baixa.getDataBaixaContabil());
		setDataCriacao(baixa.getDataCriacao());
		setDataProcesso(baixa.getDataProcesso());
		setDataTermoBaixa(baixa.getDataTermoBaixa());
		setId(baixa.getId());
		setJustificativa(baixa.getJustificativa());
		setNumeroProcesso(baixa.getNumeroProcesso());
		setIdProtocolo(baixa.getProtocolo().getId());
		setSeqProtocolo(baixa.getProtocolo().getSeq());
		setAnoProtocolo(baixa.getProtocolo().getAno());
	}

	public Baixa getBaixa() {
		if (dataBaixaContabil != null && (dataTermoBaixa == null || numeroProcesso.isEmpty() || dataProcesso == null))
			throw new IllegalStateException("Todos os dados de baixa devem ser preenchidos ao definir data de baixa contábil.");
		if (dataBaixaContabil != null && dataBaixaContabil.before(dataTermoBaixa))
			throw new IllegalStateException("Data Baixa Contábil não pode ser anterior à Data do Termo de Baixa.");

		Baixa baixa = new Baixa();
		baixa.setDataTermoBaixa(getDataTermoBaixa());
		baixa.setDataCriacao(getDataCriacao());
		baixa.setDataProcesso(getDataProcesso());
		baixa.setDataBaixaContabil(getDataBaixaContabil());
		baixa.setId(getId());
		baixa.setJustificativa(getJustificativa());
		baixa.setNumeroProcesso(getNumeroProcesso());
		baixa.getProtocolo().setId(idProtocolo);
		baixa.getProtocolo().setSeq(seqProtocolo);
		baixa.getProtocolo().setAno(anoProtocolo);
		return baixa;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdProtocolo() {
		return idProtocolo;
	}

	public void setIdProtocolo(Long idProtocolo) {
		this.idProtocolo = idProtocolo;
	}

	public int getSeqProtocolo() {
		return seqProtocolo;
	}

	public void setSeqProtocolo(int seqProtocolo) {
		this.seqProtocolo = seqProtocolo;
	}

	public int getAnoProtocolo() {
		return anoProtocolo;
	}

	public void setAnoProtocolo(int anoProtocolo) {
		this.anoProtocolo = anoProtocolo;
	}

	public Date getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public String getNumeroProcesso() {
		return numeroProcesso;
	}

	public void setNumeroProcesso(String numeroProcesso) {
		this.numeroProcesso = numeroProcesso;
	}

	public Date getDataProcesso() {
		return dataProcesso;
	}

	public void setDataProcesso(Date dataProcesso) {
		this.dataProcesso = dataProcesso;
	}

	public String getJustificativa() {
		return justificativa;
	}

	public void setJustificativa(String justificativa) {
		this.justificativa = justificativa;
	}

	public Date getDataTermoBaixa() {
		return dataTermoBaixa;
	}

	public void setDataTermoBaixa(Date dataTermoBaixa) {
		this.dataTermoBaixa = dataTermoBaixa;
	}

	public Date getDataBaixaContabil() {
		return dataBaixaContabil;
	}

	public void setDataBaixaContabil(Date dataBaixaContabil) {
		this.dataBaixaContabil = dataBaixaContabil;
	}

	public Set<DocumentoDigital> getComprovantes() {
		return comprovantes;
	}

	public void setComprovantes(Set<DocumentoDigital> comprovantes) {
		this.comprovantes = comprovantes;
	}

	public Set<BemPermanente> getBens() {
		return bens;
	}

	public void setBens(Set<BemPermanente> bens) {
		this.bens = bens;
	}
}
