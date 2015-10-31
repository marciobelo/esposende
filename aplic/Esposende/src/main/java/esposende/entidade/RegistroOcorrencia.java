package esposende.entidade;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Ocorrência temporal que agrega informação sobre um bem
 * permanente,
 */
@Entity
public class RegistroOcorrencia implements Serializable,Cloneable {

	@Id
	@GeneratedValue
	private Long id;

	@Temporal(TemporalType.DATE)
	private Date data;

	private String descricao;

	@Enumerated(value = EnumType.STRING)
	private TipoRegistroOcorrencia tipoRegistroOcorrencia;


	public RegistroOcorrencia(TipoRegistroOcorrencia tipoRegistroOcorrencia, String descricao){
		setData(new Date());
		setTipoRegistroOcorrencia(tipoRegistroOcorrencia);
		setDescricao(descricao);
	}

	public RegistroOcorrencia() {
	}

	public Long getId(){
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	private void setData(Date data){
		this.data = data;

	}

	private void setTipoRegistroOcorrencia(TipoRegistroOcorrencia tipoRegistroOcorrencia){
		if(tipoRegistroOcorrencia == null){
			throw new IllegalArgumentException("Campo tipo de registro de ocorrência não pode ser nulo");
		}
		this.tipoRegistroOcorrencia = tipoRegistroOcorrencia;
	}

	private void setDescricao(String descricao){
		if(descricao == null || descricao.trim().equals("")){
			throw new IllegalArgumentException("Campo descrição não pode ser nulo nem vazio.");
		}
		this.descricao = descricao;
	}

	public Date getData(){
		return (Date) this.data.clone();
	}

	public String getDescricao(){
		return descricao;
	}

	public TipoRegistroOcorrencia getTipoRegistroOcorrencia(){
		return tipoRegistroOcorrencia;
	}

    @Override
    public RegistroOcorrencia clone() {
        try {
            return (RegistroOcorrencia) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new Error("CloneNotSupported");
        }
    }

    @Override
    public boolean equals(Object obj) {
        if(obj==null) return false;
        if(obj==this) return true;
        if(! (obj instanceof RegistroOcorrencia) ) return false;
        RegistroOcorrencia outro = (RegistroOcorrencia)obj;
        return this.data.equals(outro.data) &&
                this.descricao.equals(outro.descricao) &&
                this.tipoRegistroOcorrencia.equals(outro.tipoRegistroOcorrencia);
    }

}
