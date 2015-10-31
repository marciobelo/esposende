package esposende.entidade;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Espaço físico onde um ou mais bens permanentes são usualmente
 * dispostos, seja para uso ou para estocagem.
 * Exemplo: Direção, Sala Múltipla, etc.
 */
@Entity
public class LocalPermanencia implements Serializable, Cloneable {

	@Id
	@GeneratedValue
	private Long id;

	private String nome;

	public LocalPermanencia() {
	}

	public LocalPermanencia(String nome) {
		setNome(nome);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	private void setNome(String nome) {
		if (nome == null || nome.trim().equals("")) {
			throw new IllegalArgumentException("Campo nome não pode ser nulo ou vazio.");
		}
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}

	@Override
	public LocalPermanencia clone() {
		try {
			return (LocalPermanencia) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new Error("CloneNotSupported");
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;
		if (obj == this) return true;
		if (!(obj instanceof LocalPermanencia)) return false;
		LocalPermanencia outro = (LocalPermanencia) obj;
		return this.nome.equals(outro.nome);
	}

	@Override
	public String toString() {
		return nome;
	}
}
