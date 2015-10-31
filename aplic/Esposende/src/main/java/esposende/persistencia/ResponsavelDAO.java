package esposende.persistencia;

import java.util.List;

import esposende.entidade.Responsavel;

public interface ResponsavelDAO {
	public abstract void persist(Responsavel responsavel);
	public abstract List<Responsavel> findAll();
	public abstract Responsavel findById(Long idResponsavel);
	public abstract void update(Responsavel responsavel);
	public abstract void delete(Responsavel responsavel);
}
