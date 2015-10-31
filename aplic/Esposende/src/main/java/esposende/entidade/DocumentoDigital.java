package esposende.entidade;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Arrays;

/**
 * Representação digital de um documento físico.
 * Pode ser uma foto de um bem, a foto de uma nota fiscal,
 * um documento PDF da nota fiscal eletrônica, a foto de uma placa
 * de identificação de um bem permanente, etc.
 */
@Entity
public class DocumentoDigital implements Serializable {


    private static final int DEZ_MEGABYTE = 10 ^ 20 * 10;

    /**
	 * Tipos de documento suportados pelo sistema.
	 */
	public enum TipoMime {
		/**
		 * http://www.w3.org/Graphics/JPEG/
		 */
		JPEG("image/jpeg");

		TipoMime(String tipoMime) {
			stringTipoMime = tipoMime;

		}

		private String stringTipoMime;

		@Override
		public String toString() {
			return stringTipoMime;
		}
	}

	@Id
	@GeneratedValue
	private Long id;

    @Column(nullable = false)
	private String titulo;

    @Column(nullable = false, length = DEZ_MEGABYTE) @Lob
    private byte[] documento;

    @Column(nullable = false)
    private TipoMime tipoMime;

	public DocumentoDigital() {
	}

	public DocumentoDigital(String titulo, byte[] documento, TipoMime tipoMime) {
		setTitulo(titulo);
		setDocumento(documento);
		setTipoMime(tipoMime);
	}

	private void setTipoMime(TipoMime tipoMime) {
		if (tipoMime == null) {
			throw new IllegalArgumentException("Campo tipoMime não pode ser nulo.");
		}
		this.tipoMime = tipoMime;
	}

	private void setTitulo(String titulo) {
		if (titulo == null || titulo.trim().equals("")) {
			throw new IllegalArgumentException("Campo titulo não pode ser nulo ou vazio.");
		}
		this.titulo = titulo;
	}

	public void setDocumento(byte[] documento) {
		if (documento == null || documento.length == 0) {
			throw new IllegalArgumentException("Campo documento (documento digitalizado) não pode ser nulo ou vazio.");
		}
		this.documento = documento;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Titulo de um documento digitalizado.
	 * Exemplo Nota fiscal, foto da plaqueta, etc.
	 *
	 * @return String
	 */
	public String getTitulo() {
		return titulo;
	}

	/**
	 * documento serializado em bytes
	 *
	 * @return byte[]
	 */
	public byte[] getDocumento() {
		return documento;
	}

	/**
	 * Tipo Mime do documento serializado
	 *
	 * @return TipoMime
	 */
	public TipoMime getTipoMime() {
		return tipoMime;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;
		if (obj == this) return true;
		if (!(obj instanceof DocumentoDigital)) return false;
		DocumentoDigital outro = (DocumentoDigital) obj;
		return this.titulo.equals(outro.titulo) &&
				this.tipoMime.equals(outro.tipoMime) &&
				Arrays.equals(this.documento, outro.documento);
	}

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (titulo != null ? titulo.hashCode() : 0);
        result = 31 * result + (documento != null ? Arrays.hashCode(documento) : 0);
        return result;
    }

}
