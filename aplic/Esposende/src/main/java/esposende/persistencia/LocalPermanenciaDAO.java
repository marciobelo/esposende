package esposende.persistencia;

import esposende.entidade.LocalPermanencia;

import java.util.List;

public interface LocalPermanenciaDAO {

    void persist(LocalPermanencia localPermanencia);

    void merge(LocalPermanencia localPermanencia);

    LocalPermanencia findById(Long id);

    List<LocalPermanencia> listAll();

    void excluir(Long id);
}
