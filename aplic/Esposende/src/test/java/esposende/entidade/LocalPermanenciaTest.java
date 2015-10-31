package esposende.entidade;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * Testes unitários para a respectiva classe de domínio
 */
public class LocalPermanenciaTest {

    @Test
    public void testarLocalValido() {
        LocalPermanencia local = new LocalPermanencia("Direção");
        assertEquals("Direção", local.getNome());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testarLocalInvalido1() {
        new LocalPermanencia(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testarLocalInvalido2() {
        new LocalPermanencia("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testarLocalInvalido3() {
        new LocalPermanencia(" ");
    }

}
