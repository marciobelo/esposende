package esposende.entidade.util;

import java.util.Calendar;

public class CalendarUtil {

	public Calendar primeiroDiaMes(Integer ano, Integer mes) {
		Calendar data = Calendar.getInstance();
		data.set(Calendar.YEAR, ano);
		data.set(Calendar.MONTH, --mes);
		data.set(Calendar.DAY_OF_MONTH, 1);
		return data;
	}

	public Calendar ultimoDiaMes(Integer ano, Integer mes) {
		Calendar data = Calendar.getInstance();
		data.set(Calendar.YEAR, ano);
		data.set(Calendar.MONTH, --mes);
		data.set(Calendar.DAY_OF_MONTH, data.getActualMaximum(Calendar.DAY_OF_MONTH));
		return data;
	}

	public Calendar ultimoDiaMesAnterior(Integer ano, Integer mes) {
		Calendar data = Calendar.getInstance();
		data.set(Calendar.YEAR, ano);
		data.set(Calendar.MONTH, --mes);
		data.add(Calendar.MONTH, -1);
		data.set(Calendar.DAY_OF_MONTH, data.getActualMaximum(Calendar.DAY_OF_MONTH));
		return data;
	}

}
