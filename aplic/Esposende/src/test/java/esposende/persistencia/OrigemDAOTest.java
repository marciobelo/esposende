package esposende.persistencia;

import esposende.entidade.Origem;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.unitils.UnitilsJUnit4;
import org.unitils.orm.jpa.JpaUnitils;
import org.unitils.spring.annotation.SpringApplicationContext;

import java.util.List;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

/**
 * Testes unitários da respectiva classe
 */
public class OrigemDAOTest extends UnitilsJUnit4 {

    private OrigemDAO dao;

    @SpringApplicationContext({"file:src/main/webapp/WEB-INF/applicationContext.xml","test-config.xml"})
    private ApplicationContext applicationContext;

    @Before
    public void setUp() {

        JpaUnitils.assertMappingWithDatabaseConsistent();

        dao = (OrigemDAO) applicationContext.getBean( OrigemDAO.class );

    }

    @Test
    public void testarInjecaoDao() {
        assertNotNull(dao);
    }

    @Test
    public void testarNovaOrigem() {
        Origem origemOriginal = new Origem("teste persistência","teste de persistência");
        dao.persist(origemOriginal);
        List<Origem> origens = dao.findAll();
        boolean existe = false;
        for(Origem origem:origens) {
            if(origemOriginal.equals(origem)) {
                existe=true;
                break;
            }
        }
        assertTrue(existe);
    }

}