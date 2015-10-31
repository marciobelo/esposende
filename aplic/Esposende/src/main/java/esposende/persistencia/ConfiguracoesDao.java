package esposende.persistencia;

import esposende.entidade.Configuracoes;

public interface ConfiguracoesDao {

	Configuracoes load();

	void merge(Configuracoes configuracoes);

	void persist(Configuracoes configuracoes);
}
