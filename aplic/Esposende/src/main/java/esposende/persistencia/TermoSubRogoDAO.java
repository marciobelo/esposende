package esposende.persistencia;

import esposende.entidade.BemPermanente;
import esposende.entidade.Responsavel;
import esposende.entidade.TermoSubRogo;

import java.util.List;

/**
 * Persistência do Termo de SubRogo
 */
public interface TermoSubRogoDAO {

    public void salvar(TermoSubRogo termoSubRogo);

    List<TermoSubRogo> obterPorResponsavel(Responsavel responsavel);

    List<TermoSubRogo> obterTodos();

    TermoSubRogo obterPorId(Long idTermoSubRogo);

	Long numeroSubRogados();

	List<BemPermanente> listaSubRogados();

	List<TermoSubRogo> listaTermosAbertos();

    TermoSubRogo buscaSubRogoVigente(BemPermanente bemPermanente);

	TermoSubRogo subRogoVigenteBem(BemPermanente bemPermanente);
}
