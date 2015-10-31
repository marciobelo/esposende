package esposende.service;

import esposende.entidade.LocalPermanencia;
import esposende.persistencia.LocalPermanenciaDAO;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

@Component
public class LocalPermanenciaService {
	@Inject
	private LocalPermanenciaDAO dao;

	public void persist(LocalPermanencia localPermanencia) {
        dao.persist(localPermanencia);
	}

	public void merge(LocalPermanencia localPermanencia) {
        dao.merge(localPermanencia);
	}

	public LocalPermanencia findById(Long id) {
        return dao.findById(id);
	}

	public List<LocalPermanencia> listar() {
        return dao.listAll();
	}

    @Transactional
    public void excluir(Long id) {
        dao.excluir( id );
    }
}
