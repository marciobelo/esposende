package esposende.entidade;


import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Ativo fixo sobre o qual se deseja ou se necessita manter controle patrimonial
 */
@Entity
public class BemPermanente implements Serializable, Cloneable {

	private static final long serialVersionUID = -4633199957240608028L;

	@Id
	@GeneratedValue
	private Long id;

	private String descricao;

    private String informacaoAdicional;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	private List<DocumentoDigital> fotos = new ArrayList<DocumentoDigital>(0);

	@ManyToOne
	private Origem origem;

	@ManyToOne
	private Responsavel responsavel;

	@OneToOne(cascade = CascadeType.ALL)
	private Tombamento tombamento;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@Fetch(value = FetchMode.SUBSELECT)
	private List<RegistroOcorrencia> registrosOcorrencia = new ArrayList<RegistroOcorrencia>(0);

	@ManyToOne
	private LocalPermanencia localPermanencia;

	@ManyToOne(fetch = FetchType.LAZY)
	private Baixa baixa;

	public BemPermanente() {
	}

	/**
	 * @param descricao
	 * @param foto
	 * @param origem
	 * @param responsavel
	 * @param tombamento
	 * @deprecated
	 */
	public BemPermanente(String descricao, byte[] foto, Origem origem, Responsavel responsavel, Tombamento tombamento) {
		setDescricao(descricao);
		setFoto(foto);
		setOrigem(origem);
		setResponsavel(responsavel);
		setTombamento(tombamento);
	}

	public BemPermanente(String descricao, Origem origem, LocalPermanencia localPermanencia) {
		setDescricao(descricao);
		setOrigem(origem);
		setLocalPermanencia(localPermanencia);
	}

	public void setLocalPermanencia(LocalPermanencia localPermanencia) {
		if (localPermanencia == null) {
			throw new IllegalArgumentException("O local de permanência não pode ser nulo.");
		}
		this.localPermanencia = localPermanencia;
	}

	/**
	 * Informações de tombamento do bem
	 *
	 * @see esposende.entidade.Tombamento
	 */
	public Tombamento getTombamento() {
		return tombamento;
	}

	public void setTombamento(Tombamento tombamento) {
		this.tombamento = tombamento;
	}

	/**
	 * Código númerico sequuencial usado internamento para identificar o bem.
	 */
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Descrição sucinta e suficiente para identificar o bem entre outros
	 */
	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricaoBreve) throws IllegalArgumentException {
		if (descricaoBreve == null || descricaoBreve.trim().isEmpty())
			throw new IllegalArgumentException("A descrição não pode ser nula ou vazia");
		this.descricao = descricaoBreve;
	}

	/**
	 * @deprecated
	 */
	public byte[] getFoto() {
		if (fotos.size() == 0) return null;
		return fotos.get(0).getDocumento();
	}

	/**
	 * @param foto
	 * @deprecated
	 */
	public void setFoto(byte[] foto) {
		fotos.add(new DocumentoDigital("Foto", foto, DocumentoDigital.TipoMime.JPEG));
	}

	/**
	 * Origem de aquisição do bem
	 *
	 * @see esposende.entidade.Origem
	 */
	public Origem getOrigem() {
		return origem.clone();
	}

	public void setOrigem(Origem origem) throws IllegalArgumentException {
		if (origem == null) throw new IllegalArgumentException("A origem não pode ser nula");
		this.origem = origem;
	}

	/**
	 * Responsável interno pelo bem
	 *
	 * @see esposende.entidade.Responsavel
	 */
	public Responsavel getResponsavel() {
		return responsavel.clone();
	}

	public void setResponsavel(Responsavel responsavel) {
		this.responsavel = responsavel;
	}

	/**
	 * Local mais comum de permanência do bem
	 *
	 * @see esposende.entidade.LocalPermanencia
	 */
	public LocalPermanencia getLocalPermanencia() {
		return localPermanencia.clone();
	}

	/**
	 * Registros de ocorrência do bem. Informam histórico do bem
	 *
	 * @see esposende.entidade.RegistroOcorrencia
	 */
	public List<RegistroOcorrencia> getRegistrosOcorrencia() {
		List<RegistroOcorrencia> clone = new ArrayList<RegistroOcorrencia>(registrosOcorrencia.size());
		for (RegistroOcorrencia ro : registrosOcorrencia) {
			clone.add(ro.clone());
		}
		return clone;
	}

	/**
	 * Fotos do bem
	 *
	 * @see esposende.entidade.DocumentoDigital
	 */
	public List<DocumentoDigital> getFotos() {
		return fotos;
	}

	public Baixa getBaixa() {
		return baixa;
	}

	public void setBaixa(Baixa baixa) {
		this.baixa = baixa;
	}

	public void adicionarRegistroOcorrencia(RegistroOcorrencia registroOcorrencia) {
		registrosOcorrencia.add(registroOcorrencia);
	}

	public void adicionarFoto(DocumentoDigital documentoDigital) {
		fotos.add(documentoDigital);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof BemPermanente)) return false;

		BemPermanente that = (BemPermanente) o;

		if (!descricao.equals(that.descricao)) return false;
		if (id != null ? !id.equals(that.id) : that.id != null) return false;
		if (!localPermanencia.equals(that.localPermanencia)) return false;
		if (!origem.equals(that.origem)) return false;
		if (responsavel != null ? !responsavel.equals(that.responsavel) : that.responsavel != null) return false;
		if (tombamento != null ? !tombamento.equals(that.tombamento) : that.tombamento != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + descricao.hashCode();
		result = 31 * result + origem.hashCode();
		result = 31 * result + (responsavel != null ? responsavel.hashCode() : 0);
		result = 31 * result + (tombamento != null ? tombamento.hashCode() : 0);
		result = 31 * result + localPermanencia.hashCode();
		return result;
	}

	@Override
	public final BemPermanente clone() {
		try {
			BemPermanente clone = (BemPermanente) super.clone();
			clone.setResponsavel(this.responsavel == null ? null : this.responsavel.clone());
			clone.setTombamento(this.tombamento == null ? null : this.tombamento.clone());
			clone.setLocalPermanencia(this.localPermanencia == null ? null : this.localPermanencia.clone());
			return clone;
		} catch (CloneNotSupportedException e) {
			throw new Error("CloneNotSupported");
		}
	}

	public void excluirFoto(DocumentoDigital documentoDigital) {
		this.fotos.remove(documentoDigital);
	}

    public String getInformacaoAdicional() {
        return informacaoAdicional;
    }

    public void setInformacaoAdicional(String informacaoAdicional) {
        this.informacaoAdicional = informacaoAdicional;
    }
}
