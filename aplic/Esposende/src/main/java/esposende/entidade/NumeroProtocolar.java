package esposende.entidade;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Codigo de identificação de um documento composto de um número
 * sequencial mais o número do ano em que foi emitido.
 * Exemplo: 1/2012 (primeiro documento de 2012)
 * Essa classe é imutável.
 */
@Entity
public class NumeroProtocolar implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    private int seq;
    private int ano;

    public NumeroProtocolar() {
    }

    public NumeroProtocolar(int seq, int ano) {
        setSeq(seq);
        setAno(ano);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setAno(int ano) {
        if (ano <= 0) {
            throw new IllegalArgumentException("Campo ano do número protocolar deve ser maior que zero");
        }
        this.ano = ano;
    }

    public void setSeq(int seq) {
        if (seq <= 0) {
            throw new IllegalArgumentException("Campo seq do número protocolar deve ser maior que zero");
        }
        this.seq = seq;
    }

    public int getSeq() {
        return seq;
    }

    public int getAno() {
        return ano;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof NumeroProtocolar)) return false;
        if (obj == this) return true;
        NumeroProtocolar outroProtocolo = (NumeroProtocolar) obj;
        if (outroProtocolo.ano == this.ano && outroProtocolo.seq == this.seq)
            return true;
        return false;
    }

    @Override
    public String toString() {
        return String.format("%05d",seq ) + "/" + String.format("%04d",ano);
    }
}