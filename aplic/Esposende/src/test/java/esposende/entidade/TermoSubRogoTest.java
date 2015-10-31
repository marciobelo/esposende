package esposende.entidade;

import org.apache.commons.lang3.time.DateUtils;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static junit.framework.Assert.*;
import static org.mockito.Mockito.mock;

/**
 * Testes unitários da respectiva classe de domínio
 */
public class TermoSubRogoTest {

    private Responsavel mockResponsavel;
    private Set<BemPermanente> mockArrolados;

    @Before
    public void setUp() {
        DocumentoDigital foto = mock(DocumentoDigital.class);
        mockResponsavel = new Responsavel("Jamile","1234567",foto,"jamile@ist-rio.net");
        mockArrolados = new HashSet<BemPermanente>(1);
        BemPermanente bemPermanente = mock(BemPermanente.class);
        mockArrolados.add(bemPermanente);
    }

    @Test
    public void testarTermoSubRogoValido() {
        Set<BemPermanente> bensArrolados = new HashSet<BemPermanente>(1);
        bensArrolados.add( new BemPermanente("Computador",mock(Origem.class),new LocalPermanencia("Sala 1")));
        TermoSubRogo termo = new TermoSubRogo(new NumeroProtocolar(1,2012),
                new Date(),mockResponsavel, null, bensArrolados);
        assertEquals(new NumeroProtocolar(1, 2012), termo.getProtocolo());
        assertTrue(DateUtils.isSameDay(termo.getDataEmissao(),new Date()));
        assertNull(termo.getProposito());
        assertNull(termo.getDataSubRogo());
        assertNull(termo.getDataEncerramento());
        assertNull(termo.getDataPrevistaEncerramento());
        assertNotNull(termo.getSubrogado());
        assertEquals(mockResponsavel,termo.getSubrogado());
        assertEquals(1,termo.getArrolados().size());
        assertEquals(0,termo.getTermosAssinados().size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testarTermoSubRogoDataSubRogoAnteriorDataEmissao() throws ParseException {
        Date dataEmissao = DateUtils.parseDate("31/01/2012","dd/MM/yyyy");
        Date dataSubRogo = DateUtils.parseDate("30/01/2012","dd/MM/yyyy");
        TermoSubRogo termo = new TermoSubRogo(new NumeroProtocolar(1,2012),dataEmissao,mockResponsavel, null, mockArrolados);
        termo.setDataSubRogo(dataSubRogo);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testarTermoSubRogoDataEncerramentoSemDataSubRogo() throws ParseException {
        Date dataEmissao = DateUtils.parseDate("01/01/2012","dd/MM/yyyy");
        Date dataEncerramento = DateUtils.parseDate("05/01/2012","dd/MM/yyyy");
        TermoSubRogo termo = new TermoSubRogo(new NumeroProtocolar(1,2012),dataEmissao,mockResponsavel, null, mockArrolados);
        termo.setDataEncerramento(dataEncerramento);

    }

    @Test(expected = IllegalArgumentException.class)
    public void testarTermoSubRogoDataEncerramentoAnteriorDataSubRogo() throws ParseException {
        Date dataEmissao = DateUtils.parseDate("01/01/2012","dd/MM/yyyy");
        Date dataSubRogo = DateUtils.parseDate("30/01/2012","dd/MM/yyyy");
        Date dataEncerramento = DateUtils.parseDate("05/01/2012","dd/MM/yyyy");
        TermoSubRogo termo = new TermoSubRogo(new NumeroProtocolar(1,2012),dataEmissao,mockResponsavel, null, mockArrolados);
        termo.setDataSubRogo(dataSubRogo);
        termo.setDataEncerramento(dataEncerramento);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testarTermoSubRogoSemResponsavel() throws ParseException {
        Date dataEmissao = DateUtils.parseDate("01/01/2012","dd/MM/yyyy");
        new TermoSubRogo(new NumeroProtocolar(1,2012),dataEmissao,null, null, mockArrolados);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testarTermoSubRogoBensArroladosNulo() throws ParseException {
        Date dataEmissao = DateUtils.parseDate("01/01/2012","dd/MM/yyyy");
        new TermoSubRogo(new NumeroProtocolar(1,2012),dataEmissao,mockResponsavel, null, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testarTermoSubRogoBensArroladosVazio() throws ParseException {
        Date dataEmissao = DateUtils.parseDate("01/01/2012","dd/MM/yyyy");
        new TermoSubRogo(new NumeroProtocolar(1,2012),dataEmissao,mockResponsavel,
                null, new HashSet<BemPermanente>(0));
    }

    @Test
    public void testarTermoSubRogoCompletoEValido() throws ParseException {
        Date dataEmissao = DateUtils.parseDate("01/01/2012","dd/MM/yyyy");
        Date dataSubRogo = DateUtils.parseDate("15/01/2012","dd/MM/yyyy");
        Date dataEncerramento = DateUtils.parseDate("30/01/2012","dd/MM/yyyy");
        Date dataPrevistaEncerramento = DateUtils.parseDate("05/02/2012","dd/MM/yyyy");
        final String propositoEsperado = "Participação em evento de interesse da instituição";
        DocumentoDigital documentoDigital = mock(DocumentoDigital.class);
        TermoSubRogo termo = new TermoSubRogo(new NumeroProtocolar(13,2013),dataEmissao,mockResponsavel, null, mockArrolados);
        termo.setProposito(propositoEsperado);
        termo.setDataPrevistaEncerramento(dataPrevistaEncerramento);
        termo.setDataSubRogo(dataSubRogo);
        termo.setDataEncerramento(dataEncerramento);
        termo.adicionarTermoAssinado(documentoDigital);
        assertEquals(new NumeroProtocolar(13, 2013), termo.getProtocolo());
        assertEquals(dataEmissao,termo.getDataEmissao());
        assertEquals(propositoEsperado,termo.getProposito());
        assertTrue(dataSubRogo!=termo.getDataSubRogo());
        assertEquals(dataSubRogo,termo.getDataSubRogo());
        assertTrue(dataEncerramento!=termo.getDataEncerramento());
        assertEquals(dataEncerramento,termo.getDataEncerramento());
        assertTrue(dataPrevistaEncerramento != termo.getDataPrevistaEncerramento());
        assertEquals(dataPrevistaEncerramento,termo.getDataPrevistaEncerramento());
        assertEquals(1,termo.getTermosAssinados().size());
        assertTrue(termo.getTermosAssinados().contains(documentoDigital));
    }

    @Test
    public void testarTermoSubRogoCloneSubRogado() {
        DocumentoDigital foto = mock(DocumentoDigital.class);
        Responsavel respOriginal = new Responsavel("Marcio","123",foto,"ist-rio@faetec.rj.gov.br");
        TermoSubRogo termo = new TermoSubRogo(new NumeroProtocolar(1,2012),
                new Date(),respOriginal, null, mockArrolados);
        assertTrue(termo.getSubrogado() != respOriginal);
        assertEquals(termo.getSubrogado(),respOriginal);
    }

    @Test
    public void testarTermoSubRogoCloneBensArrolados() {
        Set<BemPermanente> bensArrolados = new HashSet<BemPermanente>(1);
        LocalPermanencia local = new LocalPermanencia("Sala 1");
        bensArrolados.add( new BemPermanente("Computador",mock(Origem.class),local));
        TermoSubRogo termo = new TermoSubRogo(new NumeroProtocolar(1,2012),
                new Date(),mockResponsavel, null, bensArrolados);
        assertTrue(termo.getArrolados() != bensArrolados);

        assertTrue(termo.getArrolados().iterator().next() != bensArrolados.iterator().next());
        assertEquals(termo.getArrolados().iterator().next(), bensArrolados.iterator().next());
    }

}
