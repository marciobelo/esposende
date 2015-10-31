package esposende.visao.controle;

import esposende.entidade.*;
import esposende.service.BemPermanenteService;
import esposende.service.NumeroProtocolarService;
import esposende.service.ResponsavelService;
import esposende.service.TermoSubRogoService;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.mockito.Mockito.*;

public class TermoSubRogoControllerTest {

    private ResponsavelService responsavelService = null;
    private BemPermanenteService bemPermanenteService = null;
    private TermoSubRogoService termoSubRogoService = null;
    private NumeroProtocolarService numeroProtocolarService = null;

    private TermoSubRogoController controller = null;
    private Map<String,Object> model = null;
    private Set<BemPermanente> bensArrolados;
    private Responsavel responsavel;
    private TermoSubRogo termo;
    private BemPermanente bemPermanente;

    @Before
    public void setUp() {
        responsavelService = mock(ResponsavelService.class);
        bemPermanenteService = mock(BemPermanenteService.class);
        termoSubRogoService = mock(TermoSubRogoService.class);
        numeroProtocolarService = mock(NumeroProtocolarService.class);
        controller = new TermoSubRogoController(responsavelService,
                bemPermanenteService, termoSubRogoService,numeroProtocolarService);
        model = new HashMap<String,Object>();
        bensArrolados = new HashSet<BemPermanente>(1);
        bemPermanente = new BemPermanente("Computador", mock(Origem.class), new LocalPermanencia("Sala 1"));
        bemPermanente.setId(1L);
        bensArrolados.add(bemPermanente);
        responsavel = new Responsavel("Rodrigo","123", mock(DocumentoDigital.class), "rodrigo@ist-rio.net");
        termo = new TermoSubRogo(new NumeroProtocolar(1,2012),
                new Date(), responsavel, null, bensArrolados);
    }

    @Test
    public void criaNovoTermoSubRogo() {
        String ret = controller.editar(null, model);
        assertEquals("termoSubRogo/form", ret);
        assertEquals(model.get("termoSubRogoModel"), TermoSubRogoController.TermoSubRogoModel.criarVazio());
    }

    @Test
    public void testarListarTermos() {
        String ret = controller.listar(null,model);
        assertEquals("termoSubRogo/listar", ret);
    }

    @Test
    public void testarExibir() {
        String ret = controller.exibir(termo, model);
        assertEquals("termoSubRogo/exibir", ret);
        TermoSubRogoController.TermoSubRogoModel modelRet =
                (TermoSubRogoController.TermoSubRogoModel) model.get("termoSubRogoModel");
        assertEquals("00001/2012",modelRet.getProtocolo());
    }

    @Test
    public void testarEditar() {
        String ret = controller.editar(termo,model);
        assertEquals("termoSubRogo/form", ret);
        TermoSubRogoController.TermoSubRogoModel modelRet =
                (TermoSubRogoController.TermoSubRogoModel) model.get("termoSubRogoModel");
        assertEquals("00001/2012",modelRet.getProtocolo());
        assertNotNull(modelRet.getBens());
        assertNotNull(modelRet.getResponsaveis());
    }

    @Test
    public void testarPrepararFormRegistrar() {
        when(termoSubRogoService.obterPorId(1L)).thenReturn(termo);
        String view = controller.prepararFormRegistrar(termo, model);
        assertEquals("termoSubRogo/formRegistrar", view);
        TermoSubRogoController.TermoSubRogoModel modelRet =
                (TermoSubRogoController.TermoSubRogoModel) model.get("termoSubRogoModel");
        assertEquals("00001/2012",modelRet.getProtocolo());
        assertEquals(null, modelRet.getDataSubRogo());
    }
}
