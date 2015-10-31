package esposende.visao.controle.converter;

import org.springframework.stereotype.Component;

import java.beans.PropertyEditorSupport;
import java.math.BigDecimal;

@Component
public class StringBigDecimalConverter extends PropertyEditorSupport {

	@Override
	public void setAsText(String text) {
		if (text.contains(".")) {
			String exception = "O valor não pode conter pontos";
			throw new IllegalArgumentException(exception);
		}

		super.setValue(new BigDecimal(text.replace(",", ".")));
	}

	@Override
	public String getAsText() {
		return super.getValue() != null ? super.getValue().toString().replace(".", ",") : "0";
	}
}
