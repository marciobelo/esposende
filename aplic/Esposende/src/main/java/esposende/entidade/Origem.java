package esposende.entidade;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Fonte financeira ou beneficente de aquisição de um bem permanente.
 * Um bem pode ser adquirido com recursos próprio, doado
 * ou transferido.
 * Exemplos: Fundo de Descentralização, Mantenedora,
 * Doação Unidade XPTO.
 */
@Entity
public class Origem implements Serializable, Cloneable {
	private static final long serialVersionUID = 7859647779471307005L;

	@Id
	@GeneratedValue
	@Column(nullable = false)
	private Long id;

	@Column(nullable = false, length = 40)
	private String resumo;

	@Column(nullable = true)
	private String detalhe;

	public Origem() {
	}

	public Origem(String resumo, String detalhe) {
		setResumo(resumo);
		setDetalhe(detalhe);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long idOrigem) {
		this.id = idOrigem;
	}

	/**
	 * Texto resumido que caracteriza a origem de um bem permanente.
	 */
	public String getResumo() {
		return resumo;
	}

	public void setResumo(String resumo) {
		if (resumo == null || resumo.trim().length() == 0 || resumo.length() > 40)
			throw new IllegalArgumentException(String.format("Valor inválido para o atributo resumo: '%s'.", resumo));
		this.resumo = resumo;
	}

	/**
	 * Texto detalhado que relata detalhes sobre a origem de um bem permanente.
	 *
	 * @return
	 */
	public String getDetalhe() {
		return detalhe;
	}

	public void setDetalhe(String detalhe) {
		this.detalhe = detalhe;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;
		if (!(obj instanceof Origem)) return false;
		if (obj == this) return true;
		Origem outro = (Origem) obj;
		return this.resumo.equals(outro.resumo);
	}

	public int hashCode() {
		int hash = 1;
		hash = hash * 31 + (resumo.isEmpty() ? 0 : resumo.hashCode());
		hash = hash * 31 + (detalhe.isEmpty() ? 0 : detalhe.hashCode());
		return hash;
	}

	@Override
	public Origem clone() {
		try {
			return (Origem) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new Error("CloneNotSupported");
		}
	}

	@Override
	public String toString(){
		return resumo;
	}

}
