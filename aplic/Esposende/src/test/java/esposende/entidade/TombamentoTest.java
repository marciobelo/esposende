package esposende.entidade;

import org.junit.Test;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static junit.framework.Assert.assertEquals;

public class TombamentoTest {

    public static final CodigoContabil CODIGO_CONTABIL = new CodigoContabil("1.4.2.1.2.1.0", "Máq.Mot.Aparelhos");
    private static final String DOC_HABIL_TRANSF = "Termo de Transferência nº 12/2008";
    private static final String HIST_OP_TRANSF = "Transf. Conforme Proc. Nº E26/33.838/2008 - Termo nº 12/2008 de FAETEC P/ o IST-RIO";
    private static final BigDecimal VALOR_199_90 = new BigDecimal("199.90");

    @Test
    public void criarTombamentoValido() throws ParseException {

        Date dataTombamento = new SimpleDateFormat("dd/MM/yyyy").parse("31/01/2012");
        Tombamento tombamento = new Tombamento("107",dataTombamento, CODIGO_CONTABIL, TipoOperacao.AQUISICAO,
                DOC_HABIL_TRANSF, HIST_OP_TRANSF, VALOR_199_90);
        assertEquals("107", tombamento.getCodTombamento());
        Calendar cal = Calendar.getInstance();
        cal.setTime(tombamento.getDataTombamento());
        assertEquals(31, cal.get(Calendar.DAY_OF_MONTH));
        assertEquals(Calendar.JANUARY,cal.get(Calendar.MONTH));
        assertEquals(2012,cal.get(Calendar.YEAR));
        assertEquals(CODIGO_CONTABIL,tombamento.getCodigoContabil());
        assertEquals(TipoOperacao.AQUISICAO, tombamento.getTipoOperacao());
        assertEquals(DOC_HABIL_TRANSF, tombamento.getDocumentoHabil());
        assertEquals(HIST_OP_TRANSF, tombamento.getHistoricoOperacao());
        assertEquals(VALOR_199_90, tombamento.getValorOperacao());
    }

    @Test(expected = IllegalArgumentException.class)
    public void criarTombamentoCodigoInvalido1() throws ParseException {
        new Tombamento(null,
                new SimpleDateFormat("dd/MM/yyyy").parse("31/01/2012"), CODIGO_CONTABIL, TipoOperacao.AQUISICAO,
                DOC_HABIL_TRANSF, HIST_OP_TRANSF, VALOR_199_90);
    }

    @Test(expected = IllegalArgumentException.class)
    public void criarTombamentoCodigoInvalido2() throws ParseException {
        new Tombamento("",
                new SimpleDateFormat("dd/MM/yyyy").parse("31/01/2012"), CODIGO_CONTABIL, TipoOperacao.AQUISICAO,
                DOC_HABIL_TRANSF, HIST_OP_TRANSF, VALOR_199_90);
    }

    @Test(expected = IllegalArgumentException.class)
    public void criarTombamentoCodigoInvalido3() throws ParseException {
        new Tombamento("   ",
                new SimpleDateFormat("dd/MM/yyyy").parse("31/01/2012"), CODIGO_CONTABIL, TipoOperacao.AQUISICAO,
                DOC_HABIL_TRANSF, HIST_OP_TRANSF, VALOR_199_90);
    }

    @Test(expected = IllegalArgumentException.class)
    public void criarTombamentoDataInvalida1() {
        new Tombamento("123",null, CODIGO_CONTABIL, TipoOperacao.AQUISICAO,
                DOC_HABIL_TRANSF, HIST_OP_TRANSF, VALOR_199_90);
    }

    @Test(expected = IllegalArgumentException.class)
    public void criarTombamentoDataCodigoContabilNulo() throws ParseException {
        new Tombamento("123",new SimpleDateFormat("dd/MM/yyyy").parse("31/01/2012"), null, TipoOperacao.TRANSFERENCIA,
                DOC_HABIL_TRANSF, HIST_OP_TRANSF, VALOR_199_90);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testarTombamentoInvalidoTipoOperacaoNulo() throws ParseException {
        new Tombamento("123",new SimpleDateFormat("dd/MM/yyyy").parse("31/01/2012"), null, null,
                DOC_HABIL_TRANSF, HIST_OP_TRANSF, VALOR_199_90);

    }

    @Test(expected = IllegalArgumentException.class)
    public void testarTombamentoInvalidoValorOperacaoNulo() throws ParseException {
        new Tombamento("123",new SimpleDateFormat("dd/MM/yyyy").parse("31/01/2012"), null, TipoOperacao.AQUISICAO,
                DOC_HABIL_TRANSF, HIST_OP_TRANSF, null);

    }

}
