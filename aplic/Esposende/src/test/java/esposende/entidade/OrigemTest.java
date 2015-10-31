package esposende.entidade;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class OrigemTest {

	@Test(expected = IllegalArgumentException.class)
	public void testResumoDeOrigemNaoPodeSerNulo() {
		new Origem(null, null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testResumoDeOrigemNaoPodeVazio() {
		new Origem("   ", null);
	}

    @Test(expected = IllegalArgumentException.class)
    public void testResumoDeOrigemMaiorQue40caracteres() {
        new Origem("12345678901234567890123456789012345678901", null);
    }

    @Test
	public void testDetalheResumoPodeSerNulo() {
		Origem obj = new Origem("TESTE", null);
		assertNotNull(obj);
        assertEquals(null,obj.getDetalhe());
	}
	
}
