package esposende.entidade.util;

import esposende.entidade.BemPermanente;
import esposende.entidade.TipoRegistroOcorrencia;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class BemPermanenteDiff {

	public Map<TipoRegistroOcorrencia, String> diff(BemPermanente bem1, BemPermanente bem2, Map<String, TipoRegistroOcorrencia> campos) throws NoSuchFieldException, IllegalAccessException {
		Map<TipoRegistroOcorrencia, String> diff = new HashMap<TipoRegistroOcorrencia, String>();

		for (String campo : campos.keySet()) {
			Object valor1 = extraiValorAtributo(bem1, campo);
			Object valor2 = extraiValorAtributo(bem2, campo);

			if (!valor1.equals(valor2)) {
				diff.put(campos.get(campo), String.format("%s > %s", valor1, valor2));
			}
		}

		return diff;
	}

	private Object extraiValorAtributo(BemPermanente bem1, String campo) throws NoSuchFieldException, IllegalAccessException {
		Field campo1 = bem1.getClass().getDeclaredField(campo);
		campo1.setAccessible(true);
		return campo1.get(bem1);
	}

}
