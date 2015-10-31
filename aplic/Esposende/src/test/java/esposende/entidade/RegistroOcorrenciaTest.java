package esposende.entidade;

import junit.framework.Assert;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;

import java.util.Date;

import static junit.framework.Assert.*;

/**
 * Testes unitários da respectiva classe de domínio
 */
public class RegistroOcorrenciaTest {

    public static final String DESCRICAO = "Criado o Bem Permanente.\n" +
            "Descrição: Computador Dell OptiFlex\n" +
            "Responsável: <nenhum>\n" +
            "Data Aquisição: 01/01/2012\n" +
            "Valor Aquisição: R$ 1.200,00\n" +
            "Origem: Doação da FAETEC\n" +
            "Local: Sala 1\n" +
            "Tombamento: cod. 123 em 05/01/2012\n";

    @Test
    public void criaRegistroOcorrenciaValido() {
        Date data = new Date();
        RegistroOcorrencia ro = new RegistroOcorrencia(TipoRegistroOcorrencia.CRIACAO, DESCRICAO);
        assertEquals(ro.getDescricao(), DESCRICAO);
        assertEquals(ro.getTipoRegistroOcorrencia(), TipoRegistroOcorrencia.CRIACAO);
        assertTrue(DateUtils.isSameDay(data,ro.getData()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void criaRegistroOcorrenciaInvalido1() {
        Date data = new Date();
        RegistroOcorrencia ro = new RegistroOcorrencia(null, DESCRICAO);
    }

    @Test(expected = IllegalArgumentException.class)
    public void criaRegistroOcorrenciaInvalido2() {
        Date data = new Date();
        RegistroOcorrencia ro = new RegistroOcorrencia(TipoRegistroOcorrencia.ALTERACAO_DESCRICAO, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void criaRegistroOcorrenciaInvalido3() {
        Date data = new Date();
        RegistroOcorrencia ro = new RegistroOcorrencia(TipoRegistroOcorrencia.ALTERACAO_DESCRICAO, "   ");
    }

}



