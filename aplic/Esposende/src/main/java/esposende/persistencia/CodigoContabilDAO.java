package esposende.persistencia;

import esposende.entidade.CodigoContabil;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

public interface CodigoContabilDAO {

    CodigoContabil findById(Serializable id);

	List<CodigoContabil> listAll();

    CodigoContabil findByCodigo(String codigo);

	List<Object[]> listaTotalPorCodigoContabil();

    void persist(CodigoContabil codigoContabil);

    void merge(CodigoContabil codigoContabil);

	BigDecimal saldoInicialNaData(Calendar data, CodigoContabil codigoContabil);

	BigDecimal entradasNoPeriodo(Calendar inicio, Calendar fim, CodigoContabil codigoContabil);

	BigDecimal saidasNoPeriodo(Calendar inicio, Calendar fim, CodigoContabil codigoContabil);

    void delete(CodigoContabil codigoContabil);
}
