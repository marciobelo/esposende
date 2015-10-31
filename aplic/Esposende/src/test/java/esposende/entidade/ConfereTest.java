package esposende.entidade;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.mock;

/**
 * Testes unitários da respectiva classe de domínio
 */
public class ConfereTest {

    @Test
    public void criaConfereValido() {
        Inventario inventario = mock(Inventario.class);
        BemPermanente bemPermanente = mock(BemPermanente.class);
        Confere confere = new Confere(bemPermanente,inventario, null);
        assertEquals(inventario, confere.getInventario());
        assertEquals(bemPermanente,confere.getBemPermanente());
        assertEquals(null,confere.getSituacao());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testarBemPermanenteNulo() {
        Inventario inventario = mock(Inventario.class);
        new Confere(null,inventario,
                Confere.TipoSituacaoConfere.CONFERIDO);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testarInventarioNulo() {
        BemPermanente bemPermanente = mock(BemPermanente.class);
        new Confere(bemPermanente,null,
                Confere.TipoSituacaoConfere.CONFERIDO);
    }
}
