package esposende.service;

import esposende.entidade.CodigoContabil;
import esposende.entidade.util.CalendarUtil;
import esposende.persistencia.CodigoContabilDAO;
import esposende.visao.controle.formbean.Modelo12;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Service
public class CodigoContabilService {
	@Inject
	private CodigoContabilDAO dao;

	/**
	 * Busca código contábil no banco de dados de acordo com o id
	 *
	 * @param id
	 * @return
	 */
	public CodigoContabil findById(Serializable id) {
        return dao.findById(id);
	}

	/**
	 * Lista todos os códigos contábeis
	 *
	 * @return
	 */
	public List<CodigoContabil> listAll() {
		return dao.listAll();
	}

	/**
	 * Lista os códigos contábeis e a respectiva quantidade de bens de cada código
	 *
	 * @return
	 */
	public List<Object[]> listaTotalPorCodigoContabil() {
		return dao.listaTotalPorCodigoContabil();
	}

	/**
	 * Grava um código contábil no banco de dados
	 *
	 * @param codigoContabil
	 */
	@Transactional
	public void persist(CodigoContabil codigoContabil) {
		dao.persist(codigoContabil);
	}

	/**
	 * Atualiza um código contábil no banco de dados
	 *
	 * @param codigoContabil
	 */
	@Transactional
	public void merge(CodigoContabil codigoContabil) {
		dao.merge(codigoContabil);
	}

	/**
	 * Gera registros de relatório modelo 12 para um mês e ano informados
	 *
	 * @param ano
	 * @param mes
	 * @return
	 */
	public List<Modelo12> listaParaModelo12(Integer ano, Integer mes) {
		CalendarUtil calendarUtil = new CalendarUtil();
		Calendar data = calendarUtil.ultimoDiaMesAnterior(ano, mes);
		Calendar inicio = calendarUtil.primeiroDiaMes(ano, mes);
		Calendar fim = calendarUtil.ultimoDiaMes(ano, mes);

		List<Modelo12> modelo12 = new ArrayList<Modelo12>();

		for (CodigoContabil c : listAll()) {
			Modelo12 modelo121 = new Modelo12(c, dao.saldoInicialNaData(data, c));
			modelo121.setEntradas(dao.entradasNoPeriodo(inicio, fim, c));
			modelo121.setSaidas(dao.saidasNoPeriodo(inicio, fim, c));
			modelo12.add(modelo121);
		}

		return modelo12;
	}

    @Transactional
    public void excluir(Long idCodigoContabil) {
        CodigoContabil codigoContabil = dao.findById( idCodigoContabil );
        dao.delete( codigoContabil );
    }
}
