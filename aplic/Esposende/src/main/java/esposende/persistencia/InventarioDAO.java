package esposende.persistencia;

import esposende.entidade.BemPermanente;
import esposende.entidade.Confere;
import esposende.entidade.Inventario;
import esposende.entidade.Responsavel;

import java.util.List;

public interface InventarioDAO {
    public Inventario findById(Long id);
    public Inventario buscarPorResponsavel(Responsavel responsavel);
    public List<Inventario> listarPorResponsavel(Responsavel responsavel);
    public void persist(Inventario inventario);
    public void update(Inventario inventario);
	public List<Inventario> listaEmAberto();
	public List<BemPermanente> bensInventarioAtraso();

	List<BemPermanente> listaPorSituacao(Confere.TipoSituacaoConfere naoLocalizado);

    Inventario buscaUltimoInventario(BemPermanente bemPermanente);
}
