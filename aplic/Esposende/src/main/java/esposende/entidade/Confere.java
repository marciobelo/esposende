package esposende.entidade;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Serializable;

/**
 * Resultado da conferência de um determinado bem patrimonial
 * realizado dentro de um inventário
 */
@Entity
public class Confere implements Serializable {
	private static final long serialVersionUID = -8538872437513272042L;

	public enum TipoSituacaoConfere {
		CONFERIDO("Conferido"),
		DANIFICADO("Danificado"),
		NAO_LOCALIZADO("Não Localizado"),
        EM_DESUSO("Em Desuso");

		private String descricao;

		TipoSituacaoConfere(String descricao) {
			this.descricao = descricao;
		}

		public String getDescricao() {
			return descricao;
		}
	}

	@Id
	@GeneratedValue
	private Long id;

	@ManyToOne(optional = false)
	private BemPermanente bemPermanente;

	@ManyToOne(optional = false)
	private Inventario inventario;

	@Column(nullable = true)
	@Enumerated(EnumType.STRING)
	private TipoSituacaoConfere situacao;

	public Confere() {
	}

	public Confere(BemPermanente bemPermanente, Inventario inventario, TipoSituacaoConfere situacao) {
		setBemPermanente(bemPermanente);
		setInventario(inventario);
		setSituacao(situacao);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BemPermanente getBemPermanente() {
		return bemPermanente;
	}

	public void setBemPermanente(BemPermanente bemPermanente) throws IllegalArgumentException {
		if (bemPermanente == null) throw new IllegalArgumentException("O bem não pode ser nulo");
		this.bemPermanente = bemPermanente;
	}

	public Inventario getInventario() {
		return inventario;
	}

	public void setInventario(Inventario inventario) throws IllegalArgumentException {
		if (inventario == null) throw new IllegalArgumentException("O inventário não pode ser nulo");
		this.inventario = inventario;
	}

	public TipoSituacaoConfere getSituacao() {
		return situacao;
	}

	public void setSituacao(TipoSituacaoConfere situacao) throws IllegalArgumentException {
		this.situacao = situacao;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Confere confere = (Confere) o;
		if (!bemPermanente.equals(confere.bemPermanente)) return false;
		if (!inventario.equals(confere.inventario)) return false;
		return true;
	}

	@Override
	public int hashCode() {
		int result = bemPermanente != null ? bemPermanente.hashCode() : 0;
		result = 31 * result + (inventario != null ? inventario.hashCode() : 0);
		return result;
	}

	public InputStream getFotoBem() {
		return bemPermanente.getFotos().size() != 0 ? new ByteArrayInputStream(bemPermanente.getFotos().get(0).getDocumento()) : null;
	}
}
