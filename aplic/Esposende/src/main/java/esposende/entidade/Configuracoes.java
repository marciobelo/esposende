package esposende.entidade;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Entity
public class Configuracoes implements Serializable {

	@Id
	private Long id;
	@ManyToOne
	@JoinColumn(name = "responsavelinstitucional")
	private Responsavel responsavelInstitucional;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Responsavel getResponsavelInstitucional() {
		return responsavelInstitucional;
	}

	public void setResponsavelInstitucional(Responsavel responsavelInstitucional) {
		this.responsavelInstitucional = responsavelInstitucional;
	}
}
