package esposende.entidade;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class NumeroProtocolarTest {

    @Test
    public void criaNumeroProtocolarValido() {
        NumeroProtocolar numeroProtocolar = new NumeroProtocolar(1,2012);
        assertEquals(1, numeroProtocolar.getSeq());
        assertEquals(2012,numeroProtocolar.getAno());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testarNumeroProtocolarInvalido1() {
        new NumeroProtocolar(-1,2012);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testarNumeroProtocolarInvalido2() {
        new NumeroProtocolar(1,-2012);
    }

    @Test
    public void testarToString() {
        NumeroProtocolar numeroProtocolar = new NumeroProtocolar(1,2012);
        assertEquals("00001/2012",numeroProtocolar.toString());
    }

}
