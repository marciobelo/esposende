package esposende.entidade;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Identifica o responsável pela gestão de um bem patrimonial.
 * É um funcionário estatutário.
 */
@Entity
public class Responsavel implements Serializable, Cloneable {

	private static final long serialVersionUID = -3415217048887883532L;

	@Id
	@GeneratedValue
	@Column(nullable = false)
	private Long id;

	@Column(length = 60, nullable = false)
	private String nome;

	@Column(nullable = false)
	private String matricula;

	@OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	private DocumentoDigital foto;

	private String email;

	public Responsavel() {
	}

	public Responsavel(String nome, String matricula, DocumentoDigital foto, String email) {
		setNome(nome);
		setMatricula(matricula);
		setFoto(foto);
		setEmail(email);
	}

	public void setEmail(String email) {
		if (email == null) {
			throw new IllegalArgumentException("Campo email deve ser preenchido");
		}
		this.email = email;
	}

	public void setFoto(DocumentoDigital foto) {
		if (foto == null) {
			throw new IllegalArgumentException("Campo foto deve ser preenchido");
		}
		this.foto = foto;
	}

	public String getEmail() {
		return email;
	}

	public DocumentoDigital getFoto() {
		return foto;
	}

	/**
	 * Identificador interno para a aplicação
	 *
	 * @return
	 */
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Nome do responsável por bens patrimoniais
	 *
	 * @return
	 */
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		if (nome == null || nome.trim().length() == 0 || nome.length() > 60)
			throw new IllegalArgumentException(String.format("Valor invalido para o atributo nome: '%s'", nome));
		this.nome = nome;
	}

	/**
	 * Identificação funcional do responsável; número de registro junto a um
	 * sistema externo de recursos humanos
	 *
	 * @return
	 */
	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		if (matricula == null || matricula.trim().equals(""))
			throw new IllegalArgumentException("Matricula nao pode ser nula ou vazia");
		Long.parseLong(matricula);
		this.matricula = matricula;
	}

	@Override
	public boolean equals(Object outro) {
		if (outro == null) return false;
		if (!(outro instanceof Responsavel)) return false;
		if (this == outro) return true;
		Responsavel outroResponsavel = (Responsavel) outro;
		return !((this.matricula.compareTo(outroResponsavel.matricula) != 0)
				|| (this.nome.compareTo(outroResponsavel.nome) != 0));
	}

	public int hashCode() {
		int hash = 1;
		hash = hash * 31 + (nome.isEmpty() ? 0 : nome.hashCode());
		hash = hash * 31 + (matricula.isEmpty() ? 0 : matricula.hashCode());
		return hash;
	}

	public Responsavel clone() {
		try {
			Responsavel clone = (Responsavel) super.clone();
			return clone;
		} catch (CloneNotSupportedException e) {
			throw new Error("Assertion failure");
		}
	}

	@Override
	public String toString(){
		return String.format("%s (%s)", nome,matricula);
	}
}
