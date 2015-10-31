package esposende.entidade;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Código que classifica um Bem Permanente patrimoniado,
 * conforme tabela do plano de contas da entidade mantenedora
 * do patrimônio.
 * Exemplo: 1.4.2.1.2.1.0 Máq.Mot.Aparelhos
 */
@Entity
public class CodigoContabil implements Serializable {

	@Id
	@GeneratedValue
	private Long id;

	private String codigo;
	private String descricao;

	public CodigoContabil(String codigo, String descricao) {
		setCodigo(codigo);
		setDescricao(descricao);
	}

	public CodigoContabil() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setDescricao(String descricao) {
		if(descricao == null || descricao.trim().isEmpty()){
			throw new IllegalArgumentException("descrição do código contábil não pode ser nulo ou vazio.");
		}
		this.descricao = descricao;
	}

	public void setCodigo(String codigo) {
		if(codigo == null || codigo.trim().isEmpty()) {
			throw new IllegalArgumentException("código do código contábil não pode ser nulo ou vazio.");
		}
		String[] numeros = codigo.split("\\.");
		for(String numero : numeros){
			Integer.parseInt(numero);
		}
		this.codigo = codigo;
	}

	public String getCodigo() {
		return codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public String getDescricaoCompleta() {
		return String.format("%s %s", this.codigo, this.descricao);
	}

    @Override
    public boolean equals(Object obj) {
        if(obj == null) return false;
        if(obj == this) return true;
        if( !(obj instanceof CodigoContabil) ) return false;
        CodigoContabil outro = (CodigoContabil) obj;
        return this.codigo.equals(outro.codigo) &&
                this.descricao.equals(outro.descricao);
    }
}
