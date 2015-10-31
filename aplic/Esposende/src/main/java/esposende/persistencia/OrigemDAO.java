package esposende.persistencia;

import java.util.List;

import esposende.entidade.Origem;

public interface OrigemDAO {
	public abstract void persist(Origem origem);
	public abstract List<Origem> findAll();
	public abstract Origem findById(Long idOrigem);
	public abstract void update(Origem origem);
	public abstract void delete(Origem origem);
}
