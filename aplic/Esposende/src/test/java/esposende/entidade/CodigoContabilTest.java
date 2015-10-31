package esposende.entidade;

import junit.framework.Assert;
import org.junit.Test;

import static junit.framework.Assert.*;

/**
 * Testes unitários da respectiva classe de domínio
 */
public class CodigoContabilTest {

    public static final String CODIGO = "1.4.2.1.2.1.0";
    public static final String DESCRICAO = "Máq.Mot.Aparelhos";

    @Test
    public void testarCodigoContabilValido() {
        CodigoContabil contabil = new CodigoContabil(CODIGO,DESCRICAO);
        assertEquals(CODIGO, contabil.getCodigo());
        assertEquals(DESCRICAO, contabil.getDescricao());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testarCodigoContabilInvalidoCodigoNulo() {
        new CodigoContabil(null, DESCRICAO);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testarCodigoContabilInvalidoCodigoVazio() {
        new CodigoContabil("   ", DESCRICAO);
    }

    @Test(expected = NumberFormatException.class)
    public void testarCodigoContabilInvalidoCodigoIncorreto() {
        new CodigoContabil("123.ABC.456", DESCRICAO);
    }

    @Test(expected = NumberFormatException.class)
    public void testarCodigoContabilInvalidoCodigoIncorreto2() {
        new CodigoContabil("123 . 456", DESCRICAO);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testarCodigoContabilDescricaoNula() {
        CodigoContabil contabil = new CodigoContabil(CODIGO,null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testarCodigoContabilDescricaoVazia() {
        CodigoContabil contabil = new CodigoContabil(CODIGO,"   ");
    }

}
