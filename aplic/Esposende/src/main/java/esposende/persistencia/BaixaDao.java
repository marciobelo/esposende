package esposende.persistencia;

import esposende.entidade.Baixa;
import esposende.entidade.BemPermanente;

import java.util.List;

public interface BaixaDao {

	void persist(Baixa baixa);

	void merge(Baixa baixa);

	Baixa findById(Long id);

	List<Baixa> listarTodos();

	List<BemPermanente> listaEmBaixa();

	List<BemPermanente> listaBaixados();
}
