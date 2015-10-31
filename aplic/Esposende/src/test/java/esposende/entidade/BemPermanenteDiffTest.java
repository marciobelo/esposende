package esposende.entidade;

import esposende.entidade.util.BemPermanenteDiff;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class BemPermanenteDiffTest {

	private BemPermanente bem1, bem2;
	private Map<String, TipoRegistroOcorrencia> campos;

	@Before
	public void init() {
		bem1 = new BemPermanente("descricao1", new byte[]{23, 44}, new Origem(), new Responsavel(), null);
		bem2 = new BemPermanente("descricao2", new byte[]{23, 44}, new Origem(), new Responsavel(), null);
		campos = new HashMap<String, TipoRegistroOcorrencia>();
		campos.put("descricao", TipoRegistroOcorrencia.ALTERACAO_DESCRICAO);
	}

	@Test
	public void diffTest() throws NoSuchFieldException, IllegalAccessException {
		Map<TipoRegistroOcorrencia, String> diff = new BemPermanenteDiff().diff(bem1, bem2, campos);
		Assert.assertEquals("descricao1 > descricao2", diff.get(TipoRegistroOcorrencia.ALTERACAO_DESCRICAO));
	}

}
