package esposende.persistencia;

import esposende.entidade.CodigoContabil;
import esposende.entidade.TipoOperacao;
import esposende.entidade.Tombamento;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.unitils.UnitilsJUnit4;
import org.unitils.dbunit.annotation.DataSet;
import org.unitils.spring.annotation.SpringApplicationContext;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.Date;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

@DataSet
public class TombamentoDAOTest extends UnitilsJUnit4 {

    private TombamentoDAO daoTombamento;

    @Inject
    private CodigoContabilDAO daoCodigoContabil;

    @SpringApplicationContext({"file:src/main/webapp/WEB-INF/applicationContext.xml","test-config.xml"})
    private ApplicationContext applicationContext;

    @Before
    public void setUp() {
        daoTombamento = (TombamentoDAO) applicationContext.getBean( TombamentoDAO.class );
        daoCodigoContabil = (CodigoContabilDAO) applicationContext.getBean( CodigoContabilDAO.class );
    }

    @Test
    public void testarInjecaoDao() {
        assertNotNull(daoTombamento);
        assertNotNull(daoCodigoContabil);
    }

    @Test
    public void testarNovoTombamento() {
        CodigoContabil codigoContabil = daoCodigoContabil.findByCodigo("1.2.3.4.5.6.0");
        Tombamento tombamentoOriginal =
                new Tombamento("2001",new Date(),codigoContabil, TipoOperacao.AQUISICAO, "Termo de Transferência nº 12/2008",
                        "Transf. Conforme Proc. Nº E26/33.838/2008 - Termo nº 12/2008 de FAETEC P/ o IST-RIO",
                        new BigDecimal("199.90") );
        daoTombamento.persist(tombamentoOriginal);
        Tombamento tombamentoPersistido = daoTombamento.findByCodigo("2001");
        assertEquals(tombamentoOriginal, tombamentoPersistido);
    }
}