package esposende.persistencia;

import esposende.entidade.Tombamento;

import java.io.Serializable;

public interface TombamentoDAO{
	void persist(Tombamento tombamento);

	void merge(Tombamento tombamento);

	Tombamento findById(Serializable id);

	Tombamento findByCodigo(String codigo);

}
