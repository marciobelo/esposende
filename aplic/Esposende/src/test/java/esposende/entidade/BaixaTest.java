package esposende.entidade;

import org.apache.commons.lang3.time.DateUtils;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.mock;

/**
 * Testes unitários da respectiva classe de domínio
 */
public class BaixaTest {

    private static final String JUSTIFICATIVA = "Bens inservíveis e danificados. Custo não justifica o conserto.";
    private Set<BemPermanente> mockUmBem;

    @Before
    public void setUp() {
        mockUmBem = new HashSet<BemPermanente>();
        mockUmBem.add( new BemPermanente("Computador",mock(Origem.class),new LocalPermanencia("Sala 1")));
    }

    @Test
    public void testarBaixaValida() {
        Baixa baixa = new Baixa(new NumeroProtocolar(1, 2013), JUSTIFICATIVA, mockUmBem);
        assertEquals(2013, baixa.getProtocolo().getAno() );
        assertEquals(1, baixa.getProtocolo().getSeq() );
        assertEquals(JUSTIFICATIVA, baixa.getJustificativa());
        assertEquals(1, baixa.getBens().size() );
    }

    @Test(expected = IllegalArgumentException.class)
    public void testarBaixaInvalidaProtocoloNulo() {
        new Baixa(null, JUSTIFICATIVA, mockUmBem);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testarBaixaInvalidaJustificativaNula() {
        new Baixa(new NumeroProtocolar(1, 2013), null, mockUmBem);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testarBaixaInvalidaBensNulo() {
        new Baixa(new NumeroProtocolar(1, 2013), JUSTIFICATIVA, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testarBaixaInvalidaBensVazio() {
        new Baixa(new NumeroProtocolar(1, 2013), JUSTIFICATIVA, new HashSet<BemPermanente>());
    }

    @Test(expected = IllegalStateException.class)
    public void testarBaixaBaixaContabilExigeDataTermoBaixa() throws ParseException {
        Baixa baixa = new Baixa(new NumeroProtocolar(1, 2013), JUSTIFICATIVA, mockUmBem);
        Date dataBaixaContabil = DateUtils.parseDate("31/01/2012", "dd/MM/yyyy");
        baixa.setDataBaixaContabil( dataBaixaContabil );
    }

    @Test(expected = IllegalStateException.class)
    public void testarBaixaContabilAnteriorTermoBaixa() throws ParseException {
        Baixa baixa = new Baixa(new NumeroProtocolar(1, 2013), JUSTIFICATIVA, mockUmBem);
        Date dataTermoBaixa = DateUtils.parseDate("28/02/2012", "dd/MM/yyyy");
        Date dataBaixaContabil = DateUtils.parseDate("31/01/2012", "dd/MM/yyyy");
        baixa.setDataTermoBaixa( dataTermoBaixa );
        baixa.setDataBaixaContabil( dataBaixaContabil );
    }

}
