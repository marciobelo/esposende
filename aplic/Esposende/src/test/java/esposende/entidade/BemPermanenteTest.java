package esposende.entidade;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.*;
import static org.mockito.Mockito.mock;

/**
 * Testes unitários da respectiva classe de domínio
 */
public class BemPermanenteTest {

    public static final Origem ORIGEM = mock(Origem.class);
    public static final LocalPermanencia LOCAL_PERMANENCIA = mock(LocalPermanencia.class);

    @Test
    public void testarBemPermanenteValido()  {
        Origem origem = new Origem("SIDES","Fundo de Descentralização");
        LocalPermanencia local = new LocalPermanencia("Sala 1");
        BemPermanente bem = new BemPermanente("Computador DELL OptiFlex",
                origem, local);
        assertEquals("Computador DELL OptiFlex", bem.getDescricao());
        assertNotNull( bem.getOrigem() );
        assertNotNull( bem.getLocalPermanencia() );
        assertNull( bem.getTombamento() );
        assertNull( bem.getInformacaoAdicional() );
        assertEquals(0, bem.getRegistrosOcorrencia().size());
        assertEquals(0, bem.getFotos().size() );
    }

    @Test(expected = IllegalArgumentException.class)
    public void criarBemPermanenteSemDescricao() {
        new BemPermanente(null,ORIGEM, LOCAL_PERMANENCIA);
    }

    @Test(expected = IllegalArgumentException.class)
    public void criarBemPermanenteDescricaoVazia() {
        new BemPermanente("   ",ORIGEM, LOCAL_PERMANENCIA);
    }

    @Test(expected = IllegalArgumentException.class)
    public void criarBemPermanenteSemOrigem() {
        new BemPermanente("Computador",null, LOCAL_PERMANENCIA);
    }

    @Test(expected = IllegalArgumentException.class)
    public void criarBemPermanenteSemLocal() {
        new BemPermanente("Computador",ORIGEM, null);
    }

    @Test
    public void testarBemPermanenteComTombamento() {
        BemPermanente bem = new BemPermanente("Computador", ORIGEM, LOCAL_PERMANENCIA);
        Tombamento tombamento = mock(Tombamento.class);
        bem.setTombamento(tombamento);
        assertEquals(tombamento,bem.getTombamento());
    }

    @Test
    public void testarBemPermanenteComRegistroOcorrencia() {
        BemPermanente bem = new BemPermanente("Computador",ORIGEM, LOCAL_PERMANENCIA);
        RegistroOcorrencia registroOcorrencia =  new RegistroOcorrencia(TipoRegistroOcorrencia.TEXTO_LIVRE,"teste");
        bem.adicionarRegistroOcorrencia(registroOcorrencia);
        assertEquals(1, bem.getRegistrosOcorrencia().size());
        assertEquals(registroOcorrencia,bem.getRegistrosOcorrencia().get(0));
    }

    @Test
    public void testarBemPermanenteComInformacaoAdicional() {
        BemPermanente bem = new BemPermanente("Computador",ORIGEM, LOCAL_PERMANENCIA);
        bem.setInformacaoAdicional("teste");
        assertEquals("teste", bem.getInformacaoAdicional() );
    }

    @Test
    public void testarBemPermanenteComFoto() {
        BemPermanente bem = new BemPermanente("Computador",ORIGEM, LOCAL_PERMANENCIA);
        DocumentoDigital documentoDigital = mock(DocumentoDigital.class);
        bem.adicionarFoto(documentoDigital);
        assertEquals(1,bem.getFotos().size());
        assertEquals(documentoDigital,bem.getFotos().get(0));
    }

    @Test
    public void testarBemPermanenteCloneResposanvel()  {
        Responsavel resp = new Responsavel("Horácio","123456",mock(DocumentoDigital.class),"horacio@ist-rio.net");
        BemPermanente bem = new BemPermanente("Computador",ORIGEM, LOCAL_PERMANENCIA);
        bem.setResponsavel(resp);
        assertTrue(resp != bem.getResponsavel());
        assertEquals(resp,bem.getResponsavel());
    }

    @Test
    public void testarBemPermanenteCloneRegistrosOcorrencia() {
        List<RegistroOcorrencia> registrosOcorrencia = new ArrayList<RegistroOcorrencia>(2);
        RegistroOcorrencia r1 = new RegistroOcorrencia(TipoRegistroOcorrencia.CRIACAO,"teste1");
        RegistroOcorrencia r2 = new RegistroOcorrencia(TipoRegistroOcorrencia.TEXTO_LIVRE,"teste2");
        BemPermanente bem = new BemPermanente("Computador",ORIGEM, LOCAL_PERMANENCIA);
        bem.adicionarRegistroOcorrencia(r1);
        registrosOcorrencia.add(r1);
        registrosOcorrencia.add(r2);
        bem.adicionarRegistroOcorrencia(r2);
        assertEquals(2, bem.getRegistrosOcorrencia().size());
        assertTrue(registrosOcorrencia != bem.getRegistrosOcorrencia());
        assertTrue(r1 != bem.getRegistrosOcorrencia().get(0));
        assertEquals(registrosOcorrencia.get(0), bem.getRegistrosOcorrencia().get(0));
        assertTrue(r2 != bem.getRegistrosOcorrencia().get(1));
        assertEquals(registrosOcorrencia.get(1),bem.getRegistrosOcorrencia().get(1));
    }

    @Test
    public void testarBemPermanenteCloneOrigem() {
        Origem origem = new Origem("SIDES","Fundo de Descentralização");
        BemPermanente bem = new BemPermanente("Computador",origem, LOCAL_PERMANENCIA);
        assertTrue(origem != bem.getOrigem());
        assertEquals(origem,bem.getOrigem());
    }

    @Test
    public void testarBemPermanenteCloneLocalPermanencia() {
        LocalPermanencia local = new LocalPermanencia("Sala 1");
        BemPermanente bem = new BemPermanente("Computador DELL OptiFlex",
                ORIGEM, local);
        assertTrue(local != bem.getLocalPermanencia());
        assertEquals(local, bem.getLocalPermanencia());
    }

}