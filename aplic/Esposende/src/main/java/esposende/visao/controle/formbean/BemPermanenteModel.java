package esposende.visao.controle.formbean;

import esposende.entidade.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

public class BemPermanenteModel {
	private Long id;

    @Size(min = 1, message = "Informe a descrição")
	private String descricao;

    private String informacaoAdicional;

	@NotNull(message = "Informe a origem")
	private Origem origem;

	@NotNull(message = "Informe o responsável")
	private Responsavel responsavel;

	private Tombamento tombamento;
	private Set<DocumentoDigital> fotosBemPermanente;
	private Long idDocumentoAquisicao;
	private String nomeEmpresa;
	private String codigoDocumentoAquisicao;
	private Long idTombamento;

    @Pattern(regexp = "[0-9 ]*", message = "O código de tombamento deve ter somente números")
	private String codigoTombamento;

	@Past(message = "A data de tombamento deve ser anterior ao presente momento")
	private Date dataTombamento;

	@NotNull(message = "Infome o código contábil")
	private CodigoContabil codigoContabil;

	@NotNull(message = "Informe o local de permenência")
	private LocalPermanencia localPermanencia;

	private List<RegistroOcorrencia> historico;

	// Listas auxiliares de preenchimento
	private List<Responsavel> responsaveis;
	private List<LocalPermanencia> locaisPermanencia;
	private List<Origem> origens;
	private List<CodigoContabil> codigosContabeis;
	private TipoOperacao tipoOperacao;
	private String documentoHabil;
	private String historicoOperacao;
	private BigDecimal valorOperacao;
	private Set<DocumentoDigital> comprovantes;

	public BemPermanenteModel() {
	}

	public BemPermanenteModel(BemPermanente bem) {
		this.id = bem.getId();
		this.descricao = bem.getDescricao();
        this.informacaoAdicional = bem.getInformacaoAdicional();
		this.origem = bem.getOrigem();
		this.responsavel = bem.getResponsavel();
		this.tombamento = bem.getTombamento();
		this.fotosBemPermanente = new HashSet<DocumentoDigital>(bem.getFotos());
		this.localPermanencia = bem.getLocalPermanencia();
		historico = bem.getRegistrosOcorrencia();
		this.comprovantes = bem.getTombamento() == null ? null : bem.getTombamento().getComprovantesOperacao();


		this.idTombamento = (bem.getTombamento() != null) ? bem.getTombamento().getId() : null;
		this.codigoTombamento = (bem.getTombamento() != null) ? bem.getTombamento().getCodTombamento() : null;
		this.dataTombamento = (bem.getTombamento() != null) ? bem.getTombamento().getDataTombamento() : null;
        this.codigoContabil = ( bem.getTombamento() != null ) ? bem.getTombamento().getCodigoContabil() : null;
		this.valorOperacao = (bem.getTombamento() != null) ? bem.getTombamento().getValorOperacao() : null;
		this.tipoOperacao = (bem.getTombamento() != null) ? bem.getTombamento().getTipoOperacao() : null;
		this.documentoHabil = (bem.getTombamento() != null) ? bem.getTombamento().getDocumentoHabil() : null;
		this.historicoOperacao = (bem.getTombamento() != null) ? bem.getTombamento().getHistoricoOperacao() : null;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Origem getOrigem() {
		return origem;
	}

	public void setOrigem(Origem origem) {
		this.origem = origem;
	}

	public Responsavel getResponsavel() {
		return responsavel;
	}

	public void setResponsavel(Responsavel responsavel) {
		this.responsavel = responsavel;
	}

	public Tombamento getTombamento() {
		return tombamento;
	}

	public void setTombamento(Tombamento tombamento) {
		this.tombamento = tombamento;
	}

	public String getNomeEmpresa() {
		return nomeEmpresa;
	}

	public void setNomeEmpresa(String nomeEmpresa) {
		this.nomeEmpresa = nomeEmpresa;
	}

	public String getCodigoDocumentoAquisicao() {
		return codigoDocumentoAquisicao;
	}

	public void setCodigoDocumentoAquisicao(String codigoDocumentoAquisicao) {
		this.codigoDocumentoAquisicao = codigoDocumentoAquisicao;
	}

	public Long getIdDocumentoAquisicao() {
		return idDocumentoAquisicao;
	}

	public void setIdDocumentoAquisicao(Long idDocumentoAquisicao) {
		this.idDocumentoAquisicao = idDocumentoAquisicao;
	}

	public Long getIdTombamento() {
		return idTombamento;
	}

	public void setIdTombamento(Long idTombamento) {
		this.idTombamento = idTombamento;
	}

	public String getCodigoTombamento() {
		return codigoTombamento;
	}

	public void setCodigoTombamento(String codigoTombamento) {
		this.codigoTombamento = codigoTombamento;
	}

	public Date getDataTombamento() {
		return (dataTombamento != null) ? dataTombamento : new Date();
	}

	public void setDataTombamento(Date dataTombamento) {
		this.dataTombamento = dataTombamento;
	}

	public CodigoContabil getCodigoContabil() {
		return codigoContabil;
	}

	public void setCodigoContabil(CodigoContabil codigoContabil) {
		this.codigoContabil = codigoContabil;
	}

	public LocalPermanencia getLocalPermanencia() {
		return localPermanencia;
	}

	public void setLocalPermanencia(LocalPermanencia localPermanencia) {
		this.localPermanencia = localPermanencia;
	}

	public BemPermanente getBemPermanenteModel() throws IOException {
		BemPermanente bem = new BemPermanente();
		bem.setDescricao(this.descricao);
        bem.setInformacaoAdicional( this.informacaoAdicional );
		bem.setOrigem(this.getOrigem());
		bem.setResponsavel(this.responsavel);
		bem.setTombamento(this.tombamento);
		bem.setLocalPermanencia(this.localPermanencia);
		bem.setId(this.id);

		if (!this.codigoTombamento.isEmpty() && this.dataTombamento != null) {
			bem.setTombamento(this.getTombamentoModel());
		}

		return bem;
	}

	public Tombamento getTombamentoModel() {
		Tombamento tombamento = new Tombamento(this.codigoTombamento, this.dataTombamento, this.codigoContabil,
				this.tipoOperacao, this.documentoHabil, this.historicoOperacao, this.valorOperacao);
		tombamento.setId(this.idTombamento);
		return tombamento;
	}

	public Set<DocumentoDigital> getFotosBemPermanente() {
		return fotosBemPermanente;
	}

	public void setFotosBemPermanente(Set<DocumentoDigital> fotosBemPermanente) {
		this.fotosBemPermanente = fotosBemPermanente;
	}

	public List<RegistroOcorrencia> getHistorico() {
		return historico;
	}

	public void setResponsaveis(List<Responsavel> responsaveis) {
		this.responsaveis = responsaveis;
	}

	public List<Responsavel> getResponsaveis() {
		return responsaveis;
	}

	public List<LocalPermanencia> getLocaisPermanencia() {
		return locaisPermanencia;
	}

	public void setLocaisPermanencia(List<LocalPermanencia> locaisPermanencia) {
		this.locaisPermanencia = locaisPermanencia;
	}

	public List<Origem> getOrigens() {
		return origens;
	}

	public void setOrigens(List<Origem> origens) {
		this.origens = origens;
	}

	public List<CodigoContabil> getCodigosContabeis() {
		return codigosContabeis;
	}

	public void setCodigosContabeis(List<CodigoContabil> codigosContabeis) {
		this.codigosContabeis = codigosContabeis;
	}

	public TipoOperacao getTipoOperacao() {
		return tipoOperacao;
	}

	public void setTipoOperacao(TipoOperacao tipoOperacao) {
		this.tipoOperacao = tipoOperacao;
	}

	public String getDocumentoHabil() {
		return documentoHabil;
	}

	public void setDocumentoHabil(String documentoHabil) {
		this.documentoHabil = documentoHabil;
	}

	public String getHistoricoOperacao() {
		return historicoOperacao;
	}

	public void setHistoricoOperacao(String historicoOperacao) {
		this.historicoOperacao = historicoOperacao;
	}

	public BigDecimal getValorOperacao() {
		return valorOperacao;
	}

	public void setValorOperacao(BigDecimal valorOperacao) {
		this.valorOperacao = valorOperacao;
	}

	public Set<DocumentoDigital> getComprovantes() {
		return comprovantes;
	}

	public List<TipoOperacao> getOperacoes() {
		return Arrays.asList(TipoOperacao.values());
	}

    public String getInformacaoAdicional() {
        return informacaoAdicional;
    }

    public void setInformacaoAdicional(String informacaoAdicional) {
        this.informacaoAdicional = informacaoAdicional;
    }
}