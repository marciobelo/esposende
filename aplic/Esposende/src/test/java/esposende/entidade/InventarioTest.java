 package esposende.entidade;

 import org.apache.commons.lang3.time.DateUtils;
 import org.junit.Before;
 import org.junit.Test;

 import java.util.ArrayList;
 import java.util.Date;
 import java.util.List;
 import java.util.Set;

 import static junit.framework.Assert.*;
 import static org.mockito.Mockito.mock;

/**
 * Testes unitários da respectiva classe de domínio
 */
public class InventarioTest {

    private static final Date EMISSAO = new Date();
    private static final NumeroProtocolar PROTOCOLO = new NumeroProtocolar(1, 2012);
    private Responsavel mockResponsavel;
    private BemPermanente b1, b2;
    private List<BemPermanente> listaBensSoUmItem = new ArrayList<BemPermanente>();

    @Before
    public void setUp() {
        mockResponsavel = new Responsavel("Jamile","1234567",mock(DocumentoDigital.class),"jamile@ist-rio.net");
        b1 = new BemPermanente("Computador",mock(Origem.class),mock(LocalPermanencia.class));
        b2 = new BemPermanente("Grampeador",mock(Origem.class),mock(LocalPermanencia.class));
        listaBensSoUmItem.add(b1);
    }

    @Test
    public void testarInventarioValido() {
        Inventario inventario = new Inventario(PROTOCOLO, EMISSAO, mockResponsavel, listaBensSoUmItem);
        assertNull(inventario.getDataFechamento());
        Date dataFechamento = new Date();
        inventario.setDataFechamento(dataFechamento);
        NumeroProtocolar numProcoloEsperado = new NumeroProtocolar(1,2012);
        assertEquals(0,inventario.getRelatorioAssinado().size());
        assertEquals(numProcoloEsperado, inventario.getProtocolo());
        assertEquals(EMISSAO, inventario.getDataEmissao());
        DateUtils.isSameDay(EMISSAO, inventario.getDataFechamento());
        assertTrue(inventario.getDataFechamento() != dataFechamento);
        assertEquals(mockResponsavel, inventario.getResponsavel());
        assertTrue(inventario.getConferidos().contains(new Confere(b1,inventario,null)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testarInventarioSemProtocolo() {
        new Inventario(null,EMISSAO,mockResponsavel, listaBensSoUmItem );
    }

    @Test(expected = IllegalArgumentException.class)
    public void testarInventarioSemDataEmissao() {
        new Inventario(PROTOCOLO,null,mockResponsavel, listaBensSoUmItem);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testarInventarioSemResponsavel() {
        new Inventario(PROTOCOLO,EMISSAO,null, listaBensSoUmItem);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testarInventarioSemBensConferidos() {
        new Inventario(PROTOCOLO,EMISSAO,mockResponsavel,null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testarInventarioConferidosVazio() {
        new Inventario(PROTOCOLO,EMISSAO,mockResponsavel, new ArrayList<BemPermanente>());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testarDocumentoDigitalSomenteLeitura() {
        Inventario inventario = new Inventario(PROTOCOLO, EMISSAO,mockResponsavel, listaBensSoUmItem);
        Set<DocumentoDigital> lista = inventario.getRelatorioAssinado();
        lista.add(mock(DocumentoDigital.class));
    }

    @Test
    public void testarInventarioCloneResponsavel() {
        Responsavel resp = new Responsavel("Horacio","123456",mock(DocumentoDigital.class),"horacio@ist-rio.net");
        Inventario inventario = new Inventario(PROTOCOLO, EMISSAO,resp,  listaBensSoUmItem);
        assertTrue(resp != inventario.getResponsavel());
        assertEquals(resp,inventario.getResponsavel());
    }

    @Test
    public void testarRemoverBemConferido() {
        Inventario inventario = new Inventario(PROTOCOLO, EMISSAO,mockResponsavel, listaBensSoUmItem);
        inventario.adicionarConferido(b2);
        assertEquals(2, inventario.getConferidos().size());
        inventario.removerConferido(b1);
        assertFalse(inventario.getConferidos().contains(new Confere(b1, inventario, null)));
        assertTrue(inventario.getConferidos().contains(new Confere(b2, inventario, null)));
    }
}