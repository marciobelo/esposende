package esposende.visao.controle.formbean;

import esposende.entidade.CodigoContabil;

import java.math.BigDecimal;

/**
 *
 */
public class Modelo12 {

	private CodigoContabil codigoContabil;
	private BigDecimal saldoInicial = BigDecimal.ZERO;
	private BigDecimal entradas = BigDecimal.ZERO;
	private BigDecimal saidas = BigDecimal.ZERO;

	public Modelo12(CodigoContabil codigoContabil, BigDecimal saldoInicial) {
		this.codigoContabil = codigoContabil;
		this.saldoInicial = saldoInicial != null ? saldoInicial : BigDecimal.ZERO;
	}

	public CodigoContabil getCodigoContabil() {
		return codigoContabil;
	}

	public void setCodigoContabil(CodigoContabil codigoContabil) {
		this.codigoContabil = codigoContabil;
	}

	public BigDecimal getSaldoInicial() {
		return saldoInicial;
	}

	public void setSaldoInicial(BigDecimal saldoInicial) {
		this.saldoInicial = saldoInicial;
	}

	public BigDecimal getEntradas() {
		return entradas;
	}

	public void setEntradas(BigDecimal entradas) {
		this.entradas = entradas != null ? entradas : BigDecimal.ZERO;
	}

	public BigDecimal getSaidas() {
		return saidas;
	}

	public void setSaidas(BigDecimal saidas) {
		this.saidas = saidas != null ? saidas : BigDecimal.ZERO;
	}

	public BigDecimal getSaldoFinal() {
		return saldoInicial.add(entradas).subtract(saidas);
	}
}
