package esposende.entidade;

import junit.framework.Assert;
import org.junit.Test;
import static junit.framework.Assert.assertEquals;

public class DocumentoDigitalTest {

    @Test
    public void criaDocumentoDigitalValido() {
        byte[] arquivoFicticio = { 0, 0, 0, 0, 0 };
        DocumentoDigital documentoDigital = new DocumentoDigital("Nota Fiscal",
                arquivoFicticio,DocumentoDigital.TipoMime.JPEG);
        assertEquals("Nota Fiscal", documentoDigital.getTitulo());
        assertEquals(5,documentoDigital.getDocumento().length);
        assertEquals("image/jpeg",documentoDigital.getTipoMime().toString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void criaDocumentoSemTitulo() {
        new DocumentoDigital(null,new byte[]{ 0, 0, 0, 0, 0 },DocumentoDigital.TipoMime.JPEG);
    }

    @Test(expected = IllegalArgumentException.class)
    public void criaDocumentoSemTitulo2() {
        new DocumentoDigital("",new byte[]{ 0, 0, 0, 0, 0 },DocumentoDigital.TipoMime.JPEG);
    }

    @Test(expected = IllegalArgumentException.class)
    public void criaDocumentoSemTitulo3() {
        new DocumentoDigital(" ",new byte[]{ 0, 0, 0, 0, 0 },DocumentoDigital.TipoMime.JPEG);
    }

    @Test(expected = IllegalArgumentException.class)
    public void criaDocumentoSemDocumentoSerializado() {
        new DocumentoDigital("Nota Fiscal",null,DocumentoDigital.TipoMime.JPEG);
    }

    @Test(expected = IllegalArgumentException.class)
    public void criaDocumentoSemTipoMime() {
        new DocumentoDigital("Nota Fiscal",new byte[]{ 0, 0, 0, 0, 0 },null);
    }

}
