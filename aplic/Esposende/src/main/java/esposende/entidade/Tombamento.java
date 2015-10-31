package esposende.entidade;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

/**
 * Informações de tombamento de um bem patrimonial.
 * Imutável.
 */
@Entity
public class Tombamento implements Serializable, Cloneable {

	private static final long serialVersionUID = 2967724032422394187L;

	@Id
	@GeneratedValue
	private Long id;

    @Column(nullable = false)
	private String codTombamento;

    @Column(nullable = false)
	@Temporal(TemporalType.DATE)
	private Date dataTombamento;

	@ManyToOne
	private CodigoContabil codigoContabil;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private TipoOperacao tipoOperacao;

    private String documentoHabil;

    private String historicoOperacao;

    @Column(nullable = false)
    private BigDecimal valorOperacao;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<DocumentoDigital> comprovantesOperacao;

    public Tombamento() {
	}

    public Tombamento(String codTombamento, Date dataTombamento, CodigoContabil codigoContabil,
                      TipoOperacao tipoOperacao, String documentoHabil, String historicoOperacao,
                      BigDecimal valorOperacao) throws IllegalArgumentException {
        setCodTombamento(codTombamento);
        setDataTombamento(dataTombamento);
        setCodigoContabil(codigoContabil);
        setTipoOperacao(tipoOperacao);
        setDocumentoHabil(documentoHabil);
        setHistoricoOperacao(historicoOperacao);
        setValorOperacao(valorOperacao);
    }

	private void setCodigoContabil(CodigoContabil codigoContabil) {
		if (codigoContabil == null) {
			throw new IllegalArgumentException("Código contábil não pode ser nulo.");
		}
		this.codigoContabil = codigoContabil;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Número que identifica o tombamento. Equivale ao Número de Inventariação.
	 * Esse código fica afixado ao bem seja por uma plaqueta metálica ou por
	 * uma etiqueta comum.
	 *
	 * @return String
	 */
	public String getCodTombamento() {
		return codTombamento;
	}

	public void setCodTombamento(String codTombamento) {
		if (codTombamento == null || codTombamento.trim().isEmpty())
			throw new IllegalArgumentException("O código de tombamento deve ser informado");
		this.codTombamento = codTombamento;
	}

	/**
	 * Data em que a operação de tombamento foi realizada. É a data que figura na tabela da FIBP.
	 *
	 * @return String
	 */
	public Date getDataTombamento() {
		return dataTombamento;
	}

	public void setDataTombamento(Date dataTombamento) {
		if (dataTombamento == null) throw new IllegalArgumentException("A data de tombamento deve ser informada");
		this.dataTombamento = dataTombamento;
	}

	/**
	 * Informa a categoria à qual um bem pertence para fins contábeis.
	 */
	public CodigoContabil getCodigoContabil() {
		return codigoContabil;
	}

	@Override
	public Tombamento clone() throws CloneNotSupportedException {
		try {
			Tombamento clone = (Tombamento) super.clone();
			clone.setDataTombamento((Date) this.dataTombamento.clone());
			return clone;
		} catch (CloneNotSupportedException e) {
			throw new Error("Assertion failure");
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;
		if (obj == this) return true;
		if (!(obj instanceof Tombamento)) return false;
		Tombamento outro = (Tombamento) obj;

        return this.codTombamento.equals(outro.codTombamento) &&
                this.dataTombamento.equals(outro.dataTombamento) &&
                this.codigoContabil.equals(outro.codigoContabil);
    }

	@Override
	public String toString() {
		return String.format("%s/%s", codTombamento, codigoContabil.getCodigo());
	}

    /**
     * Tipo de operação que gerou o tombamento do bem.
     * <p>Exemplos: TRANSFERENCIA, AQUISICAO, etc.</p>
     * @return TipoOperacao
     */
    public TipoOperacao getTipoOperacao() {
        return tipoOperacao;
    }

    private void setTipoOperacao(TipoOperacao tipoOperacao) {
        if( tipoOperacao == null ) throw new IllegalArgumentException("Tipo operação não pode ser nulo.");
        this.tipoOperacao = tipoOperacao;
    }

    /**
     * Identifica o documento, o qual possui fé em nível institucional.
     * <p>Exemplos: Termo de Transferência nº 12/2008, Nota Fiscal 1234</p>
     * @return String texto de identificação do documento hábil
     */
    public String getDocumentoHabil() {
        return documentoHabil;
    }
    private void setDocumentoHabil(String documentoHabil) {
        this.documentoHabil = documentoHabil;
    }

    /**
     * Detalhes complementares sobre a operação, como por exemplo, empresa emissora
     * da nota fiscal de aquisição e fonte dos recursos para aquisição do bem.
     * @return String texto do histórico da operação
     */
    public String getHistoricoOperacao() {
        return historicoOperacao;
    }
    private void setHistoricoOperacao(String historicoOperacao) {
        this.historicoOperacao = historicoOperacao;
    }

    /**
     * Valor de aquisição do bem ou valor atribuído por estimativa, quando for doação.
     *
     * @return BigDecimal valor do bem
     */
    public BigDecimal getValorOperacao() {
        return valorOperacao;
    }
    public void setValorOperacao(BigDecimal valorOperacao) {
        if(valorOperacao == null) throw new IllegalArgumentException("Valor operação não pode ser nulo.");
        this.valorOperacao = valorOperacao;
    }

    /**
     * Anexos com o comprovante da operação. Por ser cópia digitalizada da nota fiscal ou
     * do termo de transferência.
     *
     * @return Set de DocumentoDigital
     */
    public Set<DocumentoDigital> getComprovantesOperacao() {
        return comprovantesOperacao;
    }
    public void setComprovantesOperacao(Set<DocumentoDigital> comprovantesOperacao) {
        this.comprovantesOperacao = comprovantesOperacao;
    }
}
